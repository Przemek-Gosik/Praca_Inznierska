package com.example.brainutrain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Level {
    EASY("easy"),
    MEDIUM("medium"),
    ADVANCED("advanced");
    private final String level;
}
