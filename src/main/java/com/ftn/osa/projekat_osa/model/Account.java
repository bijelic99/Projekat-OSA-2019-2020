package com.ftn.osa.projekat_osa.model;

import java.util.HashSet;
import java.util.Set;

public class Account extends Identifiable {
    private String smtpAddress;
    private String smtpPort;
    private InServerType inServerType;
    private String inServerAddress;
    private Integer inServerPort;
    private String username;
    private String password;
    private String displayName;
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

    public Account(){
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
