package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.repository.UserRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> registerUser(User user) {
        try {
            return Optional.of(userRepository.save(user));
        }
        catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }

    }
}
