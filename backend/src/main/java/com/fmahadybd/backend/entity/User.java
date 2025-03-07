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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Email is required")
    @Column(unique = true)
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password is invalid")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Role is required")
    private ROLE role;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth is invalid")
    private Date dob;

    @NotNull(message = "Account expiration status is required")
    private boolean isAccountNonExpired;

    @NotNull(message = "Account lock status is required")
    private boolean isAccountNonLocked;

    @NotNull(message = "Credentials expiration status is required")
    private boolean isCredentialsNonExpired;

    @NotNull(message = "Enabled status is required")
    private boolean isEnabled;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Gender is required")
    private GENDER gender;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens;

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
