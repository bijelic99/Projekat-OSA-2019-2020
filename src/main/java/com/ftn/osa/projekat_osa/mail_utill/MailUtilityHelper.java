package com.ftn.osa.projekat_osa.mail_utill;

import com.ftn.osa.projekat_osa.model.*;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface MailUtilityHelper {
    static InternetAddress[] stringToInternetAddress(String recipients) {
        return Arrays.stream(recipients.split(","))
                .map(s -> s.replaceAll(" ", ""))
                .map(s -> {
                    try {
                        return new InternetAddress(s);
                    } catch (AddressException e) {
                        return null;
                    }
                })
                .filter(internetAddress -> internetAddress != null)
                .toArray(InternetAddress[]::new);
    }

    static Folder mailClientFolderToJpaEntityFolder(javax.mail.Folder folder, Folder parentFolder, Account account) throws MessagingException {

        Folder jpaFolder = new Folder();
        jpaFolder.setName(folder.getName());
        jpaFolder.setParentFolder(parentFolder);
        //rekurzivno poziva f-ju

        if(folder.getType() == javax.mail.Folder.HOLDS_FOLDERS)
            jpaFolder.setFolders(Arrays.stream(folder.list())
                    .map(folder1 -> {
                        try {
                            return MailUtilityHelper.mailClientFolderToJpaEntityFolder(folder1, jpaFolder, account);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toSet()));


        if(folder.getType() == javax.mail.Folder.HOLDS_MESSAGES || folder.getType() == 3) {
            folder.open(javax.mail.Folder.READ_ONLY);
            jpaFolder.setMessages(Arrays.stream(folder.getMessages())
                    .map(message -> {
                        try {
                            return mailClientMessageToJpaEntityMessage(message, account);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toSet()));
        }

        return jpaFolder;
    }

    static Message mailClientMessageToJpaEntityMessage(javax.mail.Message message, Account account) throws MessagingException, IOException {
        Message jpaMessage = new Message();
        jpaMessage.setFrom(message.getFrom()[0].toString());
        if(message.getRecipients(javax.mail.Message.RecipientType.BCC) != null)
            jpaMessage.setBcc(
                    Arrays.stream(message.getRecipients(javax.mail.Message.RecipientType.BCC))
                            .map(address -> address.toString())
                            .collect(Collectors.joining(", ")));
        if(message.getRecipients(javax.mail.Message.RecipientType.CC) != null)
            jpaMessage.setCc(
                    Arrays.stream(message.getRecipients(javax.mail.Message.RecipientType.CC))
                            .map(address -> address.toString())
                            .collect(Collectors.joining(", ")));
        if(message.getRecipients(javax.mail.Message.RecipientType.TO) != null)
            jpaMessage.setTo(
                    Arrays.stream(message.getRecipients(javax.mail.Message.RecipientType.TO))
                            .map(address -> address.toString())
                            .collect(Collectors.joining(", ")));

        jpaMessage.setSubject(message.getSubject());
        jpaMessage.setUnread(!message.isSet(Flags.Flag.SEEN));
        if(message.getReceivedDate() != null)
            jpaMessage.setDateTime(message.getReceivedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        jpaMessage.setAccount(account);
        //System.out.println("-------" + message.getContentType()+ "   " + message.getSubject());
        if(message.getContentType().toLowerCase().matches("^multipart/[\\s\\S]*$")){
            Multipart multipartContent = (Multipart) message.getContent();
            for(int i = 0; i<multipartContent.getCount(); i++){
                BodyPart bodyPart = multipartContent.getBodyPart(i);
                if(bodyPart.getContentType().toLowerCase().matches("^text/[\\s\\S]*$")){
                    jpaMessage.setContent((jpaMessage.getContent() != null ? jpaMessage.getContent() : "") + bodyPart.getContent().toString());
                }
                else {
                    Attachment attachment = new Attachment();
                    DataHandler dataHandler = bodyPart.getDataHandler();
                    attachment.setMime_type(dataHandler.getContentType());
                    InputStream inputStream = message.getInputStream();
                    byte[] attArray = new byte[inputStream.available()];
                    inputStream.read(attArray);
                    String base64Att = Base64.getEncoder().encodeToString(attArray);
                    attachment.setData(base64Att);
                    attachment.setName(bodyPart.getFileName());
                    jpaMessage.getAttachments().add(attachment);
                }
            }
        }
        else{
            jpaMessage.setContent(message.getContent().toString());
        }
        return jpaMessage;
    }
}
