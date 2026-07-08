package com.travellog.auth.dto;

import com.travellog.user.dto.UserDto;

public record AuthResponse(String token, UserDto user) {
}
