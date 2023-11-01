package org.alo.votingsystem.controllers;

import org.alo.votingsystem.models.Token;
import org.alo.votingsystem.models.User;
import org.alo.votingsystem.repository.UserRepository;
import org.alo.votingsystem.requests.LoginRequest;
import org.alo.votingsystem.requests.RegisterRequest;
import org.alo.votingsystem.responses.CodeResponse;
import org.alo.votingsystem.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/auth",
                consumes = {"application/x-www-form-urlencoded", "application/x-www-form-urlencoded;charset=UTF-8", "application/json"})
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/register",
                 consumes = {MediaType.APPLICATION_JSON_VALUE})
    // For request body validation, reroute submit to application/json via ajax/JQuery
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(String.format("Username %s is already taken", request.getUsername()));
        } else if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("There already exists an account with this email");
        }

        User user = new User(request.getUsername(), request.getEmail(), encoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping(value = "/code",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        if (request.getUsername().equals("")) {
            return ResponseEntity.badRequest().body("Username cannot be empty");
        } else if (request.getPassword().equals("")) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new CodeResponse(jwt));
    }
}