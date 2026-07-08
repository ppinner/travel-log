package com.travellog.user.dto;

import com.travellog.user.User;

public record UserDto(String id, String email, String displayName) {

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName());
    }
}
