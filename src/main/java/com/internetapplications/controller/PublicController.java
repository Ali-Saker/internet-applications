package com.internetapplications.controller;

import com.internetapplications.entity.User;
import com.internetapplications.mail.EmailRecipient;
import com.internetapplications.mail.MailService;
import com.internetapplications.mail.TextEmail;
import com.internetapplications.repository.UserRepository;
import com.internetapplications.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public")
public class PublicController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


    public PublicController(JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Welcom to the Internet Applications Project!");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody User user) {
        user = userRepository.save(user);
        user.setToken(jwtService.generateToken(user));
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            user = (User) userDetailsService.loadUserByUsername(user.getUsername());
            user.setToken(jwtService.generateToken(user));
        } catch (Exception e) {
            throw new RuntimeException("Invalid Email or Password", e);
        }
        return ResponseEntity.ok(user);
    }
}
