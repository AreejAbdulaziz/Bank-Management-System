package com.example.banksecurity.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "username cannot be null")
    @Size(min = 4,max = 10,message = "username between 4 and 10 characters")
    @Column(columnDefinition = "varchar 10 not null") //unique
    private String username;
    @NotNull(message = "password cannot be null")
    @Size(min = 6,message = "password must be more than 6 characters")
    @Column(columnDefinition = "varchar (20)")
    private String password;
    @NotNull(message = "name cannot be null")
    @Size(min = 2, max = 20,message = "name must be between 2 and 20 characters")
    @Column(columnDefinition = "varchar 20 not null")
    private String name;
    @Email(message = "enter correct email")
    @Column(columnDefinition = "varchar 20 ")
    private String email;
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$")
    @Column(columnDefinition = "check (role=CUSTOMER or role=EMPLOYEE or role=ADMIN)")
    private String role;


    @OneToOne(cascade = CascadeType.ALL , mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
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
