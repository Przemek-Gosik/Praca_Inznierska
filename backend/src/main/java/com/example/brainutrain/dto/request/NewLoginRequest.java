package com.example.brainutrain.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class NewLoginRequest {

    @NotBlank
    @Size(max=45)
    private String newLogin;
}
