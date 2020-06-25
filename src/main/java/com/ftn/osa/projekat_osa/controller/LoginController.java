package com.ftn.osa.projekat_osa.controller;

import com.ftn.osa.projekat_osa.android_dto.UserDTO;
import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.service.MyUserDetailsService;
import com.ftn.osa.projekat_osa.service.UserService;
import com.ftn.osa.projekat_osa.utillity.JwtUtil;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userCred) throws Exception {
            String username = userCred.get("username");
            String password = userCred.get("password");
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );
            }
            catch (BadCredentialsException e) {
                throw new Exception("Incorrect uname or pwd");
            }

            final UserDetails userDetails =  userDetailsService.loadUserByUsername(username);

            final String jwt = jwtUtil.generateToken(userDetails);
            Map<String, String> token = new HashMap<>();
            token.put("token", jwt);
        return ResponseEntity.ok(token);
    }


}
