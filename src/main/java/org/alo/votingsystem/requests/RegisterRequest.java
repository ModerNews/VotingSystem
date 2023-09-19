package org.alo.votingsystem.requests;

import org.alo.votingsystem.validators.PasswordMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

@PasswordMatches
public class RegisterRequest {
    @NotBlank
    @Size(min=3, max=32)
    private String username;

    @NotBlank
    @Email
    private String email;

    private Set<String> role = Collections.singleton("USER");

    @NotBlank
    @Size(min=6)
    private String password;

    @NotBlank
    @Size(min=6)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
