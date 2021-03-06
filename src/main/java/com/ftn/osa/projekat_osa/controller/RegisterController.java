package com.ftn.osa.projekat_osa.controller;

import com.ftn.osa.projekat_osa.android_dto.UserDTO;
import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        try {
            userDTO.setPassword( passwordEncoder.encode(userDTO.getPassword()));
            User u = userDTO.getJpaEntity();
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            Optional<User> userOptional = userService.registerUser(u);
            ResponseEntity<UserDTO> responseEntity;
            if (userOptional.isPresent())
                responseEntity = new ResponseEntity<UserDTO>(new UserDTO(userOptional.get()), HttpStatus.OK);
            else responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            return responseEntity;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
