package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "MESSAGES")
public class Message extends Identifiable {

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    private Contact from;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Contact> to = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Contact> cc = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Contact> bcc = new HashSet<>();

    @Column
    private LocalDateTime dateTime;

    @Column(name = "_subject")
    private String subject;

    @Column
    private String content;

    @OneToMany
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @Column(name = "_read")
    private Boolean read;

    public Message(){
        this(null, null, null, null, null, null, null, null, null, null, false);
    }

    public Message(Integer id, Account account, Contact from, Set<Contact> to, Set<Contact> cc, Set<Contact> bcc, LocalDateTime dateTime, String subject, String content, Set<Tag> tags, Boolean read) {
        super(id);
        this.account = account;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.dateTime = dateTime;
        this.subject = subject;
        this.content = content;
        this.tags = tags;
        this.read = read;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Contact getFrom() {
        return from;
    }

    public void setFrom(Contact from) {
        this.from = from;
    }

    public Set<Contact> getTo() {
        return to;
    }

    public void setTo(Set<Contact> to) {
        this.to = to;
    }

    public Set<Contact> getCc() {
        return cc;
    }

    public void setCc(Set<Contact> cc) {
        this.cc = cc;
    }

    public Set<Contact> getBcc() {
        return bcc;
    }

    public void setBcc(Set<Contact> bcc) {
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

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
