package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongPasswordException;
import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.repository.UserRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.UserServiceInterface;
import javassist.NotFoundException;
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
            if (!userRepository.findUserByUsername(user.getUsername()).isPresent()) {
                return Optional.of(userRepository.save(user));
            } else {
                return Optional.empty();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }

    }

    @Override
    public Optional<User> changePassword(Long userId, String currentPassword, String newPassword) throws ResourceNotFoundException, WrongPasswordException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getPassword().equals(currentPassword)){
                user.setPassword(newPassword);

                return Optional.of(userRepository.save(user));
            }
            else throw new WrongPasswordException();
        }
        else throw new ResourceNotFoundException("User not found");
    }
}
