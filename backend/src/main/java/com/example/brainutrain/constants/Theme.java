package com.example.brainutrain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum Theme {
    DAY("day"),
    NIGHT("night"),
    CONTRAST("contrast");
    private final String theme;
}
