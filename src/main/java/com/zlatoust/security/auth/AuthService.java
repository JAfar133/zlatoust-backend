package com.zlatoust.security.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlatoust.exceptions.UserAlreadyExistException;
import com.zlatoust.models.PersonRole;
import com.zlatoust.models.User;
import com.zlatoust.services.dto.mapper.UserDTOMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.zlatoust.exceptions.BadCredentialsException;
import com.zlatoust.mapper.UserMapper;
import com.zlatoust.security.ClientDetails;
import com.zlatoust.security.JwtService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    public AuthResponse authenticate(AuthRequest authRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Can't login user: " + e.getMessage());
        }

        User user = userMapper.findUserByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User with email = " + authRequest.getEmail() + " not found"));
        ClientDetails clientDetails = new ClientDetails(user);
        String jwtToken = jwtService.generateToken(clientDetails);
        String refreshToken = jwtService.generateRefreshToken(clientDetails);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userDTOMapper.toDto(user))
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if(userMapper.findUserByEmail(registerRequest.getEmail()).isPresent())
            throw new UserAlreadyExistException("Пользователь с такой почтой уже зарегистрирован");

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .username(registerRequest.getUsername())
                .surname(registerRequest.getSurname())
                .patronymic(registerRequest.getPatronymic())
                .role(PersonRole.USER)
                .build();
        userMapper.saveUser(user);
        ClientDetails userDetails = new ClientDetails(user);
        String jwtToken = jwtService.generateToken(userDetails);

        var refreshToken = jwtService.generateRefreshToken(userDetails);

        AuthResponse response = new AuthResponse();
        response.setAccessToken(jwtToken);
        response.setRefreshToken(refreshToken);
        response.setUser(userDTOMapper.toDto(user));
        return response;
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = userMapper.findUserByEmail(userEmail)
                    .orElseThrow();
            ClientDetails clientDetails = new ClientDetails(user);
            if (jwtService.isTokenValid(refreshToken, clientDetails)) {
                var accessToken = jwtService.generateToken(clientDetails);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .user(userDTOMapper.toDto(user))
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public User checkToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(token);

        if (userEmail != null && jwtService.isTokenValid(token, new ClientDetails(userMapper.findUserByEmail(userEmail).orElse(null)))) {
            return userMapper.findUserByEmail(userEmail).orElse(null);
        }

        return null;
    }
}
