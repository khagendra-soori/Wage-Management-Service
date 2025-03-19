package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> getAllUsers();
    public UserDto getUserById(Long userId);
    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(Long userId, UserDto userDto);
    public void deleteUser(Long userId);
}
