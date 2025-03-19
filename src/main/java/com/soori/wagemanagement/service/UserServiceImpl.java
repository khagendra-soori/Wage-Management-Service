package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.UserDto;
import com.soori.wagemanagement.entity.User;
import com.soori.wagemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List <User> users = findAllUser();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(mapToUserDTO(user));
        }

        return userDtos;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = findUserById(userId);

        return mapToUserDTO(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        boolean userByName = findUserByName(userDto.getUserName()).isPresent();
        boolean userByEmail = findUserByEmail(userDto.getEmail()).isPresent();
        boolean userByPhoneNumber = findUserByPhoneNumber(userDto.getPhoneNumber()).isPresent();

        if (userByName || userByEmail || userByPhoneNumber) {
            return userDto;
        }

        User user = mapToUserEntity(userDto);
        User savedUser = userRepository.save(user);

        return mapToUserDTO(savedUser);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User existingUser = findUserById(userId);
        updateExistingUser(existingUser, userDto);
        User savedUser = mapToUserEntity(userDto);
        return mapToUserDTO(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        deleteUserById(userId);
    }

    private UserDto mapToUserDTO(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .userImage(user.getUserImage())
                .lineManager(user.getLineManager())
                .departmentId(user.getDepartment().getDepartmentId())
                .roleIds(user.getRoles().stream().map(role -> role.getRoleId()).collect(Collectors.toList()))
                .build();
    }

    private User mapToUserEntity(UserDto userDTO){
        return User.builder()
                .userName(userDTO.getUserName())
                .email(userDTO.getEmail())
                .userImage(userDTO.getUserImage())
                .lineManager(userDTO.getLineManager())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
    }

    private void updateExistingUser(User existingUser, UserDto userDTO){
        existingUser.setUserName(userDTO.getUserName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setUserImage(userDTO.getUserImage());
        existingUser.setLineManager(userDTO.getLineManager());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
    }

    private List<User> findAllUser() {
        return userRepository.findAll();

    }

    private User findUserById(Long userId){
        return userRepository.findById(userId).get();
    }

    private void deleteUserById(Long userId){
        userRepository.deleteById(userId);
    }

    private Optional<User> findUserByName(String userName){
        return userRepository.findByUserName(userName);
    }

    private Optional<User> findUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    private Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
