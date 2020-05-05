package com.ftn.osa.projekat_osa.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Message extends Identifiable{
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private LocalDateTime dateTime;
    private Set<Tag> tags;
    private String subject;
    private String content;
    private Set<Attachment> attachments;
    private boolean unread;

    private Account account;

    public Message(Long id, String from, String to, String cc, String bcc, LocalDateTime dateTime, String subject, String content, boolean unread, Set<Tag> tags, Set<Attachment> attachments, Account account) {
        super(id);
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.dateTime = dateTime;
        this.subject = subject;
        this.content = content;
        this.unread = unread;
        this.tags = tags;
        this.attachments = attachments;
        this.account = account;
    }

    public Message(){
        this.tags = new HashSet<>();
        this.attachments = new HashSet<>();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
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

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
