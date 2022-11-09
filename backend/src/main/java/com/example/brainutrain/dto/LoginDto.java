package com.example.brainutrain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LoginDto {
    @NotNull(message = "Username should not be null")
    String userName;

    @NotNull(message = "Password should not be null")
    String password;
}
