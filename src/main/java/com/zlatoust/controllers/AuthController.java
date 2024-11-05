package com.zlatoust.controllers;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.exceptions.UserAlreadyExistException;
import com.zlatoust.models.User;
import com.zlatoust.security.auth.AuthRequest;
import com.zlatoust.security.auth.AuthResponse;
import com.zlatoust.security.auth.AuthService;
import com.zlatoust.security.auth.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(ApiConstants.API_PATH + "/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().body(authService.authenticate(authRequest));
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) throws UserAlreadyExistException {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        return ResponseEntity.ok(authService.register(request));

    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }

    @GetMapping("/check")
    public ResponseEntity<User> check(HttpServletRequest request) {
        User user = authService.checkToken(request);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
