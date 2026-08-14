package com.pos.user.addflywaymigrationusertable.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username length must be between 3 and 50 characters")
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email max length is 100 characters")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password length must be between 6 and 255 characters")
    String password,

    @Size(max = 100, message = "Full name max length is 100 characters")
    String fullName
) {}