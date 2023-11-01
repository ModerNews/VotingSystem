package org.alo.votingsystem;

import org.alo.votingsystem.controllers.AuthController;
import org.alo.votingsystem.controllers.VoterController;
import org.alo.votingsystem.requests.LoginRequest;
import org.alo.votingsystem.responses.CodeResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthTest {
    @Autowired
    AuthController authController;

    @Autowired
    VoterController voterController;

    @Test
    void getJWTToken(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password");
        CodeResponse data = (CodeResponse) authController.loginUser(loginRequest).getBody();
        assertThat(data.getCode()).isNotNull();
        System.setProperty("Authorization", data.getCode());
    }

    // TODO check if you can access authenticated() endpoints with freshly generated token
    @Test
    void checkForLogin(){
        voterController.getClass();
    }
}
