package com.example.brainutrain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Purpose {

    EMAIL_VERIFICATION("email verification"),
    PASSWORD_REMINDER("password reminder");
    private final String purpose;
}
