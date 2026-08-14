package com.pos.user.addflywaymigrationusertable.service;

import com.pos.user.addflywaymigrationusertable.dto.CreateUserRequest;
import com.pos.user.addflywaymigrationusertable.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
}