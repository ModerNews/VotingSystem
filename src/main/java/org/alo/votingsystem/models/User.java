package org.alo.votingsystem.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table( name = "users"
        // uniqueConstraints = {
        //   @UniqueConstraint(columnNames = "username"),
        //   @UniqueConstraint(columnNames = "email")
        // }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 32)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String passHash;

    @NotBlank
    @DateTimeFormat
    private String createdAt;

    public User() {
    }

    public User(String username, String email, String passHash) {
        this.username = username;
        this.email = email;
        this.passHash = passHash;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
