package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "messages")
public class Message extends JpaEntity {
    @Column(name = "_from", nullable = false)
    private String from;
    @Column(name = "_to", nullable = false)
    private String to;
    @Column
    private String cc;
    @Column
    private String bcc;
    @Column(name = "date_time", columnDefinition = "datetime default now()")
    private LocalDateTime dateTime;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags;
    @Column
    private String subject;
    @Column
    private String content;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Attachment> attachments;
    private boolean unread;
    @ManyToOne(fetch = FetchType.EAGER)
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

    public Message() {
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
