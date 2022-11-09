package com.example.brainutrain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {

    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;
}
