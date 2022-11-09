package com.example.brainutrain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeMemory {
    MEMORY("memory"),
    MNEMONICS("mnemonics");
    private final String typeMemory;
}
