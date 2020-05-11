package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.model.User;

import java.util.Optional;

public interface UserServiceInterface {
    public Optional<User> registerUser(User user);
}
