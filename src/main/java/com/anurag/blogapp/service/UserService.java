package com.anurag.blogapp.service;

import com.anurag.blogapp.Dto.UserDto;

import java.util.List;

public interface UserService {

    public UserDto createUser(UserDto userDto);

    public UserDto updateUser(UserDto userDto, int userId);

    public UserDto getUserById(int userId);

    public List<UserDto> getAllUsers();

    public void deleteUser(int userId);
}
