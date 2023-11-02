package org.alo.votingsystem.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.alo.votingsystem.models.Token;
import org.alo.votingsystem.models.User;
import org.alo.votingsystem.repository.UserRepository;
import org.alo.votingsystem.requests.AuthorizationRequest;
import org.alo.votingsystem.requests.RegisterRequest;
//import org.alo.votingsystem.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

//    @Autowired
//    private JwtUtils jwtUtils;

    @PostMapping(value = "/register",
                 consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    // For request body validation, reroute submit to application/json via ajax/JQuery
    public ResponseEntity<?> registerUser(@Valid RegisterRequest request) {
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

    @GetMapping(value="/code")
    public void loginUser(HttpServletResponse response, @Valid AuthorizationRequest request) throws IOException {
        // TODO store code and serialized AuthorizationRequest
        Token token = new Token();
        // Assert Redirect_uri is not null nor empty
        if (request.getRedirect_uri() == null) {
            request.setRedirect_uri("http://localhost:8080");
        }else if (request.getRedirect_uri().trim().length() == 0) {
            request.setRedirect_uri("http://localhost:8080");
        }
        response.sendRedirect(request.getRedirect_uri().trim() + "/code?code=def" + token.generateAccessToken(125));
    }
}