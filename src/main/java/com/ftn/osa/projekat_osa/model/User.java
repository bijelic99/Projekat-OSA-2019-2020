package com.ftn.osa.projekat_osa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User extends Identifiable {
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @OneToMany
    private Set<Account> accounts;
    @OneToMany
    private Set<Contact> contacts;
    @OneToMany
    private Set<Tag> userTags;

    public User() {
        this(null, null, null, null, null, null, null, null);
    }

    public User(Integer id, String username, String password, String firstName, String lastName, Set<Account> accounts, Set<Contact> contacts, Set<Tag> userTags) {
        super(id);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = accounts;
        this.contacts = contacts;
        this.userTags = userTags;
    }

    public Set<Tag> getUserTags() {
        return userTags;
    }

    public void setUserTags(Set<Tag> userTags) {
        this.userTags = userTags;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
