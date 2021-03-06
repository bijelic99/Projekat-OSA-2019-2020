package com.ftn.osa.projekat_osa.mail_utill;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.InServerType;
import com.ftn.osa.projekat_osa.model.Message;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Store;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MailUtility{
    Account account;
    Session session;

    private MailUtility() {

    }

    public MailUtility(Account account) throws NullPointerException {
        if (account == null) throw new NullPointerException("Account can't be null");
        this.account = account;
    }

    private Session startSession() throws NullPointerException {
        if (account == null) throw new NullPointerException("Account can't be null");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", account.getSmtpAddress());
        properties.put("mail.smtp.port", account.getSmtpPort());

        if(account.getInServerType() == InServerType.POP3){
            properties.put("mail.pop3.host", account.getInServerAddress());
            properties.put("mail.pop3.port", account.getInServerPort());
            properties.put("mail.pop3.ssl.enable", "true");
        }
        else if (account.getInServerType() == InServerType.IMAP){
            properties.put("mail.imap.host", account.getInServerAddress());
            properties.put("mail.imap.port", account.getInServerPort());
            properties.put("mail.imap.ssl.enable", "true");
        }

        Session newSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account.getUsername(), account.getPassword());
            }
        });

        setSession(newSession);
        return newSession;
    }

    public void sendMessage(Message message) throws MessagingException {
        if (getSession() == null) startSession();
        javax.mail.Message mimeMessage = new MimeMessage(getSession());
        mimeMessage.setFrom(new InternetAddress(message.getFrom()));
        mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO,
                MailUtilityHelper.stringToInternetAddress(message.getTo())
        );
        mimeMessage.setRecipients(javax.mail.Message.RecipientType.CC,
                MailUtilityHelper.stringToInternetAddress(message.getCc())
        );
        mimeMessage.setRecipients(javax.mail.Message.RecipientType.BCC,
                MailUtilityHelper.stringToInternetAddress(message.getBcc())
        );
        Multipart multipart = new MimeMultipart();

        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setContent(message.getContent(), "text/plain");
        multipart.addBodyPart(textBodyPart);

        //TODO Moguce da ce ovo praviti problema
        message.getAttachments().stream().map(attachment -> {
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            try {
                attachmentBodyPart.setFileName(attachment.getName());

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            ByteArrayDataSource dataSource = new ByteArrayDataSource(Base64.getDecoder().decode(attachment.getData()), attachment.getMime_type());
            try {
                attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return attachmentBodyPart;
        }).forEach(o -> {
            try {
                multipart.addBodyPart(o);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        mimeMessage.setSubject(message.getSubject());
        mimeMessage.setContent(multipart);

        Transport.send(mimeMessage);

        setSession(null);
    }

    public Set<com.ftn.osa.projekat_osa.model.Folder> getWholeFolderTree() throws MessagingException {
        if (getSession() == null) startSession();
        Store store = getSession().getStore(account.getInServerType() == InServerType.POP3 ? "pop3" : account.getInServerType() == InServerType.IMAP ? "imap" : null);
        store.connect(getAccount().getUsername(), getAccount().getPassword());
        Folder rootFolder = store.getDefaultFolder();
        Set<com.ftn.osa.projekat_osa.model.Folder> folders = Arrays.stream(rootFolder.list())
                .map(folder -> {
                    try {
                        return MailUtilityHelper.mailClientFolderToJpaEntityFolder(folder, null, getAccount());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toSet());
        
        setSession(null);

        return folders;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private Session getSession() {
        return session;
    }

    private void setSession(Session session) {
        this.session = session;
    }

    public Map<String, Object> syncFolder(Stack<String> folderPath, LocalDateTime latestMessageTimestamp, List<String> existingFoldersNameList, com.ftn.osa.projekat_osa.model.Folder folder) throws MessagingException, ResourceNotFoundException {
        if (getSession() == null) startSession();
        Store store = getSession().getStore(account.getInServerType() == InServerType.POP3 ? "pop3" : account.getInServerType() == InServerType.IMAP ? "imap" : null);
        store.connect(getAccount().getUsername(), getAccount().getPassword());


            Folder f = store.getDefaultFolder();
            while (!folderPath.empty()){
                f = f.getFolder(folderPath.pop());
            }
            List<Message> messages = new ArrayList<>();
            if (f.getType() == Folder.HOLDS_MESSAGES || f.getType() == 3) {
                f.open(Folder.READ_ONLY);
                messages = Arrays.stream(f.getMessages())
                        .map(message -> {
                            try {
                                return MailUtilityHelper.mailClientMessageToJpaEntityMessage(message, getAccount());
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .filter(message -> message != null)
                        .filter(message -> latestMessageTimestamp == null ||  message.getDateTime().isAfter(latestMessageTimestamp))
                        .collect(Collectors.toList());
                if(f.isOpen()) f.close();
            }
            List<com.ftn.osa.projekat_osa.model.Folder> folders = Arrays.stream(f.list())
                    .filter(folder1 -> !existingFoldersNameList.contains(folder1.getName()))
                    .map(folder1 -> {
                        try {
                            return MailUtilityHelper.mailClientFolderToJpaEntityFolder(folder1, folder, getAccount());
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .filter(folder1 -> folder1 != null)
                    .collect(Collectors.toList());

            Map<String, Object> map = new HashMap<>();
            map.put("messages", messages);
            map.put("folders", folders);

            return map;

    }

    public Set<Message> getMessages() throws WrongProtocolException, MessagingException {
        Set<Message> messages = new HashSet<>();
        switch (getAccount().getInServerType()){
            case IMAP: messages = getImapMessages(); break;
            case POP3: messages = getPop3Messages(); break;
        }
        return messages;
    }

    private Set<Message> getImapMessages() throws WrongProtocolException, MessagingException {
        if(getAccount().getInServerType() != InServerType.IMAP) throw new WrongProtocolException("Wrong protocol used to get messages");
        if (getSession() == null) startSession();
        IMAPStore store = (IMAPStore) getSession().getStore(account.getInServerType() == InServerType.POP3 ? "pop3" : account.getInServerType() == InServerType.IMAP ? "imap" : null);
        store.connect(getAccount().getUsername(), getAccount().getPassword());
        Folder folder = Arrays.stream(store.getDefaultFolder().list())
                .filter(folder1 -> folder1.getName().toLowerCase().contains("inbox"))
                .findFirst()
                .orElse(null);
        folder.open(Folder.READ_ONLY);
        Set<Message> messages = Arrays.stream(folder.getMessages())
                .map(message -> {
                    try {
                        return MailUtilityHelper.mailClientMessageToJpaEntityMessage(message, getAccount());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(message -> message != null)
                .collect(Collectors.toSet());
        folder.close();
        setSession(null);
        return messages;
    }

    private Set<Message> getPop3Messages() throws WrongProtocolException, MessagingException {
        if(getAccount().getInServerType() != InServerType.POP3) throw new WrongProtocolException("Wrong protocol used to get messages");
        if (getSession() == null) startSession();
        POP3Store store = (POP3Store) getSession().getStore(account.getInServerType() == InServerType.POP3 ? "pop3" : account.getInServerType() == InServerType.IMAP ? "imap" : null);
        store.connect(getAccount().getUsername(), getAccount().getPassword());
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Set<Message> messages = Arrays.stream(folder.getMessages())
                .map(message -> {
                    try {
                        return MailUtilityHelper.mailClientMessageToJpaEntityMessage(message, getAccount());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(message -> message != null)
                .collect(Collectors.toSet());
        folder.close();
        setSession(null);
        return messages;
    }

    public Set<Message> getNewMessages(LocalDateTime time) throws WrongProtocolException, MessagingException {
        if(time != null) return getMessages().stream()
                .filter(message -> message.getDateTime().isAfter(time))
                .collect(Collectors.toSet());

        else return getMessages();
    }
}
