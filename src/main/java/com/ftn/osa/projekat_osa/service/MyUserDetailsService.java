package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.service.serviceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOptional = userServiceInterface.getUserByUsername(s);
        if(!userOptional.isPresent()) throw new UsernameNotFoundException("Not found");
        User user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }


}
