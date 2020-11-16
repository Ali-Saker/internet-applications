package com.internetapplications.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class User extends BaseEntity implements Serializable, UserDetails {

    @Column(
            nullable = false
    )
    private String firstName;

    @Column(
            nullable = false
    )
    private String lastName;

    private String name;

    @Column(
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            nullable = false
    )
    private String password;

    @Column(name = "IS_ADMIN", columnDefinition = "boolean default false")
    private boolean admin = false;

    @Column(name = "IS_ACTIVATED", columnDefinition = "boolean default true")
    private boolean activated = true;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    @Transient
    private String token;

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : this.getRoles()) {
            for(ApiPermission apiPermission : role.getApiPermissions()) {
                authorities.add(new SimpleGrantedAuthority(apiPermission.getCode()));
            }
        }
        return authorities;
    }

    @JsonIgnore
    public boolean hasPermission(String permission) {
        for (Role role : this.getRoles()) {
            for(ApiPermission apiPermission : role.getApiPermissions()) {
                if(permission.equals(apiPermission.getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isActivated();
    }

    @PrePersist
    private void prePersistProcess() {
        this.password = this.password == null ? null : new BCryptPasswordEncoder().encode(this.password);
        this.name = this.firstName + " " + this.lastName;
    }

    @PreUpdate
    public void preUpdateProcess() {
        this.name = this.firstName + " " + this.lastName;
    }
}
