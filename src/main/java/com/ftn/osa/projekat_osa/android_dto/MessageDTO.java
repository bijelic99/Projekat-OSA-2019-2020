package com.ftn.osa.projekat_osa.android_dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Message;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageDTO extends DtoObject<Message> {
    private Long id;
    private Long account;
    private ContactDTO from;
    private Set<ContactDTO> to;
    private Set<ContactDTO> cc;
    private Set<ContactDTO> bcc;
    private LocalDateTime dateTime;
    private String subject;
    private String content;
    private Set<AttachmentDTO> attachments;
    private Set<TagDTO> tags;
    private Boolean unread;

    public MessageDTO() {
        this.to = new HashSet<>();
        this.cc = new HashSet<>();
        this.bcc = new HashSet<>();
        this.attachments = new HashSet<>();
        this.tags = new HashSet<>();
    }

    public MessageDTO(Long id, Long account, ContactDTO from, Set<ContactDTO> to, Set<ContactDTO> cc, Set<ContactDTO> bcc, LocalDateTime dateTime, String subject, String content, Set<AttachmentDTO> attachments, Set<TagDTO> tags, Boolean unread) {
        this.id = id;
        this.account = account;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.dateTime = dateTime;
        this.subject = subject;
        this.content = content;
        this.attachments = attachments;
        this.tags = tags;
        this.unread = unread;
    }

    public MessageDTO(Message entity) {
        this();
        this.id = entity.getId();
        this.account = entity.getAccount().getId();
        ContactDTO c = new ContactDTO();
        c.setEmail(entity.getFrom());
        this.from = c;
        if(entity.getTo() != null && !entity.getTo().equals(""))
            this.to = Arrays.stream(entity.getTo().split(" ")).map(email -> {
                ContactDTO contactDTO = new ContactDTO();
                contactDTO.setEmail(email);
                return contactDTO;
            }).collect(Collectors.toSet());
        if(entity.getCc() != null && !entity.getCc().equals(""))
            this.cc = Arrays.stream(entity.getCc().split(" ")).map(email -> {
                ContactDTO contactDTO = new ContactDTO();
                contactDTO.setEmail(email);
                return contactDTO;
            }).collect(Collectors.toSet());
        if(entity.getBcc() != null && !entity.getBcc().equals(""))
            this.bcc = Arrays.stream(entity.getBcc().split(" ")).map(email -> {
                ContactDTO contactDTO = new ContactDTO();
                contactDTO.setEmail(email);
                return contactDTO;
            }).collect(Collectors.toSet());
        this.dateTime = entity.getDateTime();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        this.attachments = entity.getAttachments().stream().map(attachment -> new AttachmentDTO(attachment)).collect(Collectors.toSet());
        this.tags = entity.getTags().stream().map(tag -> new TagDTO(tag)).collect(Collectors.toSet());
        this.unread = entity.isUnread();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public ContactDTO getFrom() {
        return from;
    }

    public void setFrom(ContactDTO from) {
        this.from = from;
    }

    public Set<ContactDTO> getTo() {
        return to;
    }

    public void setTo(Set<ContactDTO> to) {
        this.to = to;
    }

    public Set<ContactDTO> getCc() {
        return cc;
    }

    public void setCc(Set<ContactDTO> cc) {
        this.cc = cc;
    }

    public Set<ContactDTO> getBcc() {
        return bcc;
    }

    public void setBcc(Set<ContactDTO> bcc) {
        this.bcc = bcc;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public Boolean getUnread() {
        return unread;
    }

    public void setUnread(Boolean unread) {
        this.unread = unread;
    }

    @Override
    public Message getJpaEntity() {
        return new Message(getId(), getFrom().getEmail(),
                getTo().stream().map(contactDTO -> contactDTO.getEmail()).collect(Collectors.joining(" ")),
                getCc().stream().map(contactDTO -> contactDTO.getEmail()).collect(Collectors.joining(" ")),
                getBcc().stream().map(contactDTO -> contactDTO.getEmail()).collect(Collectors.joining(" ")),
                getDateTime(),
                getSubject(),
                getContent(),
                getUnread(),
                getTags().stream().map(tagDTO -> tagDTO.getJpaEntity()).collect(Collectors.toSet()),
                getAttachments().stream().map(attachmentDTO -> attachmentDTO.getJpaEntity()).collect(Collectors.toSet()),
                new Account(getAccount(), null, null, null, null, null, null, null, null, null)

        );

    }
}
