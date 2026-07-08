package com.travellog.auth;

import com.travellog.auth.dto.AuthResponse;
import com.travellog.auth.dto.LoginRequest;
import com.travellog.auth.dto.SignupRequest;
import com.travellog.common.exception.EmailAlreadyInUseException;
import com.travellog.common.exception.InvalidCredentialsException;
import com.travellog.user.User;
import com.travellog.user.UserRepository;
import com.travellog.user.dto.UserDto;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyInUseException("An account with this email already exists");
        }

        Instant now = Instant.now();
        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .displayName(request.displayName())
                .roles(List.of("USER"))
                .createdAt(now)
                .updatedAt(now)
                .build();
        user = userRepository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token, UserDto.from(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, UserDto.from(user)));
    }
}
