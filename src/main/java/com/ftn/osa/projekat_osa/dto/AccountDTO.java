package com.ftn.osa.projekat_osa.dto;

import java.io.Serializable;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Identifiable;
import com.ftn.osa.projekat_osa.model.InServerType;

public class AccountDTO extends Identifiable implements Serializable {

	private String smtpAddress;
	private String smtpPort;
	private InServerType inServerType;
	private String inServerAddress;
	private Integer inServerPort;
	private String username;
	private String password;
	private String displayName;
	
	/**
	 * 
	 */
	public AccountDTO() {
		super();
	}
	/**
	 * @param smtpAddress
	 * @param smtpPort
	 * @param inServerType
	 * @param inServerAddress
	 * @param inServerPort
	 * @param username
	 * @param password
	 * @param displayName
	 */
	public AccountDTO(String smtpAddress, String smtpPort, InServerType inServerType, String inServerAddress, Integer inServerPort, String username, String password, String displayName) {
		super();
		this.smtpAddress = smtpAddress;
		this.smtpPort = smtpPort;
		this.inServerType = inServerType;
		this.inServerAddress = inServerAddress;
		this.inServerPort = inServerPort;
		this.username = username;
		this.password = password;
		this.displayName = displayName;
	}
	
	public AccountDTO(Account account) {
		this(account.getSmtpAddress(), account.getSmtpPort(), account.getInServerType(), account.getInServerAddress(), account.getInServerPort(), account.getUsername(), account.getPassword(), account.getDisplayName());
	}
	/**
	 * 
	 * @return the smtpAddress
	 */
	public String getSmtpAddress() {
        return smtpAddress;
    }

	/**
	 * 
	 * @param smtpAddress the smtpAddress to set
	 */
    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    /**
     * 
     * @return the smtpPort
     */
    public String getSmtpPort() {
        return smtpPort;
    }

    /**
     * 
     * @param smtpPort the smtpPort to set
     */
    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    /**
     * 
     * @return the inServerType
     */
    public InServerType getInServerType() {
        return inServerType;
    }

    /**
     * 
     * @param inServerType the inServerType to set
     */
    public void setInServerType(InServerType inServerType) {
        this.inServerType = inServerType;
    }

    /**
     * 
     * @return the inServerAddress
     */
    public String getInServerAddress() {
        return inServerAddress;
    }

    /**
     * 
     * @param inServerAddress the inServerAddress to set
     */
    public void setInServerAddress(String inServerAddress) {
        this.inServerAddress = inServerAddress;
    }

    /**
     * 
     * @return the inServerPort
     */
    public Integer getInServerPort() {
        return inServerPort;
    }

    /**
     * 
     * @param inServerPort the inServerPort to set
     */
    public void setInServerPort(Integer inServerPort) {
        this.inServerPort = inServerPort;
    }

    /**
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
