package ru.edu.ui.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {
    private final String ROLE_PREFIX = "ROLE_";
    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private String email;
    private String hashPassword;
    private Role role;
    LocalDate dataOfCreation;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role.equals(Role.ADMIN)) {
            return List.of(new SimpleGrantedAuthority(ROLE_PREFIX + "ADMIN"));
        } else {
            return List.of(new SimpleGrantedAuthority(ROLE_PREFIX + "USER"));
        }
    }

    @Override
    public String getPassword() {
        return hashPassword;
    }

    @Override
    public String getUsername() {
        return nickname;
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
}
