package com.fmahadybd.backend.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fmahadybd.backend.enmus.GENDER;
import com.fmahadybd.backend.enmus.ROLE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)

    private ROLE role;

    private Date dob;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    @Enumerated(value = EnumType.STRING)
    private GENDER gender;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens;


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval=true)
    private List<Art> arts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {

        return email;
    }

    @Override
    public boolean isAccountNonExpired() {

        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {

        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {

        return isEnabled;
    }
}
