package com.example.brainutrain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserDto {
    private Long idUser;
    private String login;
    private String email;
    private List<String> roles;
    private boolean isEmailConfirmed;
}
