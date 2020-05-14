package com.ftn.osa.projekat_osa.android_dto;

import com.ftn.osa.projekat_osa.model.User;

import java.util.HashSet;

public class UserDTO extends DtoObject<User>{

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public UserDTO(Long id, String username, String password, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(User entity) throws Exception {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public User getJpaEntity() {
        return new User(getId(), getUsername(), getPassword(), getFirstName(), getLastName(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }
}
