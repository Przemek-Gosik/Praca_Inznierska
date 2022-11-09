package com.example.brainutrain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FontSize {
    SMALL("small"),
    MEDIUM("medium"),
    BIG("big");

    private final String fontSize;
}
