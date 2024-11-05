package com.zlatoust.controllers;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.models.User;
import com.zlatoust.security.auth.AuthContext;
import com.zlatoust.services.UserService;
import com.zlatoust.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.API_PATH_USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/{id}/update-profile")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = AuthContext.getUserFromSecurityContext();
        if (!user.getId().equals(id)) {
            return ResponseEntity.status(403).build();
        }
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        User user = AuthContext.getUserFromSecurityContext();
        if (!user.getId().equals(id)) {
            return ResponseEntity.status(403).build();
        }
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok("Пароль успешно изменен");
    }
}
