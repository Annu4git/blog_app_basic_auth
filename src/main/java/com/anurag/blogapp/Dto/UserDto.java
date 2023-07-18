package com.anurag.blogapp.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotNull(message = "User name must not be null")
    private String name;

    @Email(message = "Provide proper email address")
    private String email;

    @NotBlank( message = "password must not be blank")
    @Size(min = 4, max = 10, message = "password should be minimum 4 characters")
    private String password;

    @NotBlank
    private String description;

    @NotBlank
    private String about;
}
