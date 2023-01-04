package com.example.brainutrain.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class WritingLessonUserResponse {

    private Long idWritingLesson;

    private int number;

    private String name;

    private String generatedCharacters;

    private Long idWritingLessonResult;

    private Double Score;
}
