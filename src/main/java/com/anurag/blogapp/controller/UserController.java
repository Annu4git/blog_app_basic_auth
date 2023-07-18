package com.anurag.blogapp.controller;

import com.anurag.blogapp.Dto.ApiResponse;
import com.anurag.blogapp.Dto.UserDto;
import com.anurag.blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@EnableMethodSecurity
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String home() {
        return "This is home page updated 12";
    }

    @PostMapping(path = "/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        System.out.println("Multiplying");
        UserDto createdUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{userId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<UserDto> updateUser(@PathVariable int userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    @PreAuthorize("hasAuthority('ROLE_NORMAL')")
    public UserDto getUser(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping(path = "/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
