package com.zlatoust.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String username;
    private String surname;
    private String patronymic;
    private String password;
    private PersonRole role;
}
