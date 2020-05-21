package com.ftn.osa.projekat_osa.mail_utill;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Message;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.*;

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
            ByteArrayDataSource dataSource = new ByteArrayDataSource(Base64.getDecoder().decode(attachment.getData()), attachment.getName());
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
}
