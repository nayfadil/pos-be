package com.pos.user.addflywaymigrationusertable.dto;

import com.pos.user.addflywaymigrationusertable.entity.User;

import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String username,
    String email,
    String fullName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}