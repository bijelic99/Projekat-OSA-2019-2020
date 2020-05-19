package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongPasswordException;
import com.ftn.osa.projekat_osa.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserServiceInterface {
    public Optional<User> registerUser(User user);
    public Optional<User> changePassword(Long userId, String currentPassword, String newPassword) throws ResourceNotFoundException, WrongPasswordException;
    public Optional<User> updateUser(Long userId, Map<String, String> newValues) throws ResourceNotFoundException;
}
