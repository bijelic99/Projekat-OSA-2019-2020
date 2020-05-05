package com.ftn.osa.projekat_osa.model;

import java.util.HashSet;
import java.util.Set;

public class User extends Identifiable {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Contact> userContacts;
    private Set<Tag> userTags;
    private Set<Account> userAccounts;

    public User(Long id, String username, String password, String firstName, String lastName, Set<Contact> userContacts, Set<Tag> userTags, Set<Account> userAccounts) {
        super(id);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userContacts = userContacts;
        this.userTags = userTags;
        this.userAccounts = userAccounts;
    }

    public User(){
        this.userContacts = new HashSet<>();
        this.userTags = new HashSet<>();
        this.userAccounts = new HashSet<>();
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

    public Set<Contact> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(Set<Contact> userContacts) {
        this.userContacts = userContacts;
    }

    public Set<Tag> getUserTags() {
        return userTags;
    }

    public void setUserTags(Set<Tag> userTags) {
        this.userTags = userTags;
    }

    public Set<Account> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<Account> userAccounts) {
        this.userAccounts = userAccounts;
    }
}
