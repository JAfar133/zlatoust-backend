package com.zlatoust.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Неверный формат email")
    private String email;
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;
    @NotBlank(message = "Подтверждение пароля не должен быть пустым")
    private String confirmPassword;
    private String username;
    private String surname;
    private String patronymic;
}
