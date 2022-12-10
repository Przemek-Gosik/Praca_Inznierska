package com.example.brainutrain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum Level {
    EASY("easy"),
    MEDIUM("medium"),
    ADVANCED("advanced");
    private final String level;
}
