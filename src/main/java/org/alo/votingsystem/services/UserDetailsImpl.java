package org.alo.votingsystem.services;

import org.alo.votingsystem.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Integer id;
    private String username;
    private String passHash;
    private String email;
    private String createdAt;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String passHash, String email, String createdAt, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.passHash = passHash;
        this.email = email;
        this.createdAt = createdAt;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        // TODO: Implement roles and authorities
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority[]{new SimpleGrantedAuthority("USER")});

        return new UserDetailsImpl(user.getId(),
                                   user.getUsername(),
                                   user.getPassHash(),
                                   user.getEmail(),
                                   user.getCreatedAt(),
                                   authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassHash() {
        return passHash;
    }

    @Override
    public String getPassword() {
        return passHash;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserDetailsImpl user = (UserDetailsImpl) o;
        return id.equals(user.id);
    }
}
