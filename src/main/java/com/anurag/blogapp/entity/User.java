package com.anurag.blogapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @Column(name = "user_id")
    private int id;

    @Column(name = "username")
    private String name;

    // if name attribute is not provided in column
    // then it will take java variable name as it is
    @Column
    private String email;

    // even if Column annotation is not provide,
    // still column will be created
    private String password;

    // this will not persist in DB
    @Transient
    private String description;

    // this will persist in DB but will not return back as part of Json
    @JsonIgnore
    private String about;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlogPost> blogPosts;


    // here Join table is a 3rd table, which will save mapping of user and roles
    // name of that 3rd table is user_role
    // here table 1 : user, with col 1 : user_id
    // is joined with
    // table 2 : role, with col 2 : role_id
    // table 2 is called as inverse join columns
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "role_id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.name;
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
