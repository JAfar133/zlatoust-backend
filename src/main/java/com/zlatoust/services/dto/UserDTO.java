package com.zlatoust.services.dto;

import com.zlatoust.models.PersonRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Неверный формат email")
    private String email;
    private String username;
    private String surname;
    private String patronymic;
    private PersonRole role;
}
