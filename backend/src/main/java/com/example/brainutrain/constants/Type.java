package com.example.brainutrain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Type {
        SCHUBERT("schubert"),
        FINDING_NUMBERS("finding_numbers"),
        READING_WITH_QUIZ("reading_with_quiz");
       private final String type;
}
