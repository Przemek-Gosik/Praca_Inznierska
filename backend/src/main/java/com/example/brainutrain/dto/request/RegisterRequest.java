package com.example.brainutrain.dto.request;

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
public class RegisterRequest {

    @NotBlank(message = "Login nie może być pusty")
    @Size(min = 5,message = "Login nie może być krótszy niż 5 znaków")
    @Size(max=45,message = "Login nie może być dłuższy niż 45 znaków")
    private  String login;

    @NotBlank(message = "Email nie może być pusty")
    @Email(message = "Podana wartość nie jest adresem email")
    @Size(min=13,message = "Email nie może być krótszy niż 45 znaków")
    @Size(max = 45,message = "Email nie może być dłuższy niż 45 znaków")
    private String email;

    @NotBlank(message = "Hasło nie może być puste")
    @Size(min = 5,message = "Hasło nie może być krótsze niż 5 znaków")
    @Size(max = 45,message = "Hasło nie może być dłuższe niż 45 znaków")
    private String password;
}
