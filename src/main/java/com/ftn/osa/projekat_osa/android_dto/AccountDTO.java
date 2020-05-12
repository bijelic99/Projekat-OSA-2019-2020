package com.ftn.osa.projekat_osa.android_dto;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.InServerType;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class AccountDTO extends DtoObject<Account> {
    private Long id;
    private String smtp;
    private String inServerType;
    private String inServer;
    private String username;
    private String password;
    private Set<FolderDTO> accountFolders;

    public AccountDTO(){
        this.accountFolders = new HashSet<>();
    }

    public AccountDTO(Long id, String smtp, String inServerType, String inServer, String username, String password, Set<FolderDTO> folders) {
        this.id = id;
        this.smtp = smtp;
        this.inServerType = inServerType;
        this.inServer = inServer;
        this.username = username;
        this.password = password;
        this.accountFolders = folders;
    }

    public AccountDTO(Account entity) {
        this.id = entity.getId();
        StringJoiner sj = new StringJoiner(":");
        sj.add(entity.getSmtpAddress()).add(entity.getSmtpPort());
        this.smtp = sj.toString();
        this.inServerType = entity.getInServerType().name();
        sj = new StringJoiner(":");
        sj.add(entity.getInServerAddress()).add(entity.getInServerPort().toString());
        this.inServer = sj.toString();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.accountFolders = entity.getAccountFolders().stream().map(folder -> new FolderDTO(folder)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getInServerType() {
        return inServerType;
    }

    public void setInServerType(String inServerType) {
        this.inServerType = inServerType;
    }

    public String getInServer() {
        return inServer;
    }

    public void setInServer(String inServer) {
        this.inServer = inServer;
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

    public Set<FolderDTO> getAccountFolders() {
        return accountFolders;
    }

    public void setAccountFolders(Set<FolderDTO> accountFolders) {
        this.accountFolders = accountFolders;
    }

    @Override
    public Account getJpaEntity() {
        String[] smtpFullAddress = this.getSmtp().split(":");
        String[] inFullAddress = this.getInServer().split(":");
        return new Account(this.getId(), smtpFullAddress[0], smtpFullAddress[1],
                InServerType.valueOf(this.getInServerType()), inFullAddress[0], Integer.parseInt(inFullAddress[1]),
                this.getUsername(), this.getPassword(), "",
                this.getAccountFolders().stream().map(folder-> folder.getJpaEntity()).collect(Collectors.toSet())
                );
    }
}
