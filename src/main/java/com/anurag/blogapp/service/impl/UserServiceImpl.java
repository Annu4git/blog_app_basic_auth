package com.anurag.blogapp.service.impl;

import com.anurag.blogapp.Dto.UserDto;
import com.anurag.blogapp.entity.User;
import com.anurag.blogapp.exception.ResourceNotFoundException;
import com.anurag.blogapp.repository.UserRepository;
import com.anurag.blogapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userToUserDto(userRepository.save(userDtoToUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        /*
        If we directly call save then also it will work
        if user is not present then new user will be created
        if user is present then existing user data will be updated
        return userToUserDto(userRepository.save(userDtoToUser(userDto)));
        But we need to check with user id first, then will take the action accordingly
         */

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        User updatedUser = userRepository.save(userDtoToUser(userDto));

        return userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return userToUserDto(user);

    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(user);
    }

    private User userDtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

    private UserDto userToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() -> new ResourceNotFoundException("", "" , 1));
        return user;
    }
}
