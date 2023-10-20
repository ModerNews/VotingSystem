package org.alo.votingsystem.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {
    @NotBlank
    @Size(min=3, max=32)
    private String username;

    @NotBlank
    @Size(min=6)
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
