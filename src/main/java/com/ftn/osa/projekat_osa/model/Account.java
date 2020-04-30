package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ACCOUNTS")
public class Account extends Identifiable {

    @Column(name = "smtp_address", nullable = false)
    private String smtpAddress;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "incoming_mail_protocol", nullable = false)
    private IncomingMailProtocol incomingMailProtocol;

    @Column(name = "incoming_mail_address", nullable = false)
    private String incomingMailAddress;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Folder> accountFolders;

    public  Account(){
        this(null,null,null,null,null,null, null, new HashSet<>());
    }

    public Account(Integer id, String smtpAddress, IncomingMailProtocol incomingMailProtocol, String incomingMailAddress, String username, String password, String displayName, Set<Folder> accountFolders) {
        super(id);
        this.smtpAddress = smtpAddress;
        this.incomingMailProtocol = incomingMailProtocol;
        this.incomingMailAddress = incomingMailAddress;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.accountFolders = accountFolders;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public IncomingMailProtocol getIncomingMailProtocol() {
        return incomingMailProtocol;
    }

    public void setIncomingMailProtocol(IncomingMailProtocol incomingMailProtocol) {
        this.incomingMailProtocol = incomingMailProtocol;
    }

    public String getIncomingMailAddress() {
        return incomingMailAddress;
    }

    public void setIncomingMailAddress(String incomingMailAddress) {
        this.incomingMailAddress = incomingMailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
