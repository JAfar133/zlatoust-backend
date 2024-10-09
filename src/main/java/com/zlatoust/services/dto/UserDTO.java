package com.zlatoust.services.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String login;

    @Override
    public String toString() {
        return "{\n\t\"email\": \"" + email + "\", \n" +
                "\t\"login\": \"" + login + "\"\n}";
    }
}
