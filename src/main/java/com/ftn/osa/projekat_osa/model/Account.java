package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account extends JpaEntity {
    @Column(name = "smtp_address", nullable = false)
    private String smtpAddress;
    @Column(name = "smtp_port", nullable = false)
    private String smtpPort;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "in_server_type", nullable = false)
    private InServerType inServerType;
    @Column(name = "in_server_address", nullable = false)
    private String inServerAddress;
    @Column(name = "in_server_port", nullable = false)
    private Integer inServerPort;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "display_name")
    private String displayName;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Folder> accountFolders;

    public Account(Long id, String smtpAddress, String smtpPort, InServerType inServerType, String inServerAddress, Integer inServerPort, String username, String password, String displayName, Set<Folder> accountFolders) {
        super(id);
        this.smtpAddress = smtpAddress;
        this.smtpPort = smtpPort;
        this.inServerType = inServerType;
        this.inServerAddress = inServerAddress;
        this.inServerPort = inServerPort;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.accountFolders = accountFolders;
    }

    public Account() {
        this.accountFolders = new HashSet<>();
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public InServerType getInServerType() {
        return inServerType;
    }

    public void setInServerType(InServerType inServerType) {
        this.inServerType = inServerType;
    }

    public String getInServerAddress() {
        return inServerAddress;
    }

    public void setInServerAddress(String inServerAddress) {
        this.inServerAddress = inServerAddress;
    }

    public Integer getInServerPort() {
        return inServerPort;
    }

    public void setInServerPort(Integer inServerPort) {
        this.inServerPort = inServerPort;
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

    public Set<Folder> getAccountFolders() {
        return accountFolders;
    }

    public void setAccountFolders(Set<Folder> accountFolders) {
        this.accountFolders = accountFolders;
    }
}
