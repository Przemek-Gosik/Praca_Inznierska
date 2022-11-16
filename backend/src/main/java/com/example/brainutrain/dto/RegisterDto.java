package com.example.brainutrain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RegisterDto {

    @NotBlank
    @Size(max=45)
    private  String login;

    @NotBlank
    @Email(message = "Not valid email")
    @Size(max=45)
    private String email;

    @NotBlank
    @Size(min=5,max=45)
    private String password;
}
