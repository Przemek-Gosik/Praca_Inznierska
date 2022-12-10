package com.example.brainutrain.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NewLoginRequest {

    @NotBlank
    @Size(max=45)
    private String newLogin;
}
