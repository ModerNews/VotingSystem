package org.alo.votingsystem.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.alo.votingsystem.models.AuthGrant;
import org.alo.votingsystem.models.Token;
import org.alo.votingsystem.models.User;
import org.alo.votingsystem.repository.AuthGrantRepository;
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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthGrantRepository authGrantRepository;

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

    @GetMapping(value="/code",
                produces={MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void loginUser(HttpServletResponse response, @Valid AuthorizationRequest request) throws IOException {
        Token token = new Token();
        // Assure Redirect_uri is not null nor empty
        try {
            Boolean requestValid = request.isValid();
        } catch (IllegalArgumentException e ) {
            String[] message = e.getMessage().split(";");
            if (message[0].equals("server")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, message[1]);
            } else {
                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(request.getRedirect_uri().trim());
                if (request.getState() != null && !(request.getState().trim().length() > 0)) {
                    uriBuilder.queryParam("state", URLEncoder.encode(request.getState()));
                }uriBuilder.queryParam("error", message[0]);
                uriBuilder.queryParam("error_description", URLEncoder.encode(message[1], "ASCII"));
                response.sendRedirect(uriBuilder.build().toUriString());
            }
            return;
        }
        String code = "def" + token.generateAccessToken(125);
        AuthGrant grant = new AuthGrant(code, request);
        authGrantRepository.save(grant);
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(request.getRedirect_uri().trim());
        uri.queryParam("code", code);
        uri.queryParam("state", URLEncoder.encode(request.getState()));
        String uriString = uri.build().toUriString();

        response.sendRedirect(uriString);
    }

    @GetMapping(value="/token",
                produces={MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> getToken() {
        return ResponseEntity.ok("Token");
    }
}