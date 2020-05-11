package com.ftn.osa.projekat_osa.controller;

import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = "application/json")
    public Optional<User> register(){

    }

}
