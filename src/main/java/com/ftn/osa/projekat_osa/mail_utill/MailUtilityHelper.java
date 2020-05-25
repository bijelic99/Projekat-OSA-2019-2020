package com.ftn.osa.projekat_osa.mail_utill;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Attachment;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;

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
        jpaFolder.setMessages(Arrays.stream(folder.getMessages())
                .map(message -> {
                    try {
                        return mailClientMessageToJpaEntityMessage(message, account);
                    } catch (Exception e){
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toSet()));

        return jpaFolder;
    }

    static Message mailClientMessageToJpaEntityMessage(javax.mail.Message message, Account account) throws MessagingException, IOException {
        Message jpaMessage = new Message();
        jpaMessage.setFrom(message.getFrom()[0].toString());
        jpaMessage.setBcc(
                Arrays.stream(message.getRecipients(javax.mail.Message.RecipientType.BCC))
                        .map(address -> address.toString())
                        .collect(Collectors.joining(", ")));

        jpaMessage.setCc(
                Arrays.stream(message.getRecipients(javax.mail.Message.RecipientType.CC))
                        .map(address -> address.toString())
                        .collect(Collectors.joining(", ")));

        jpaMessage.setTo(
                Arrays.stream(message.getRecipients(javax.mail.Message.RecipientType.TO))
                        .map(address -> address.toString())
                        .collect(Collectors.joining(", ")));
        jpaMessage.setSubject(message.getSubject());
        jpaMessage.setUnread(!message.isSet(Flags.Flag.SEEN));
        jpaMessage.setDateTime(message.getReceivedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        jpaMessage.setAccount(account);
        if(message.getContentType().equals("multipart")){
            Multipart multipartContent = (Multipart) message.getContent();
            for(int i = 0; i<multipartContent.getCount(); i++){
                BodyPart bodyPart = multipartContent.getBodyPart(i);
                if(bodyPart.getContentType().matches("^text/.*$")){
                    jpaMessage.setContent(bodyPart.getContent().toString());
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

    }
}
