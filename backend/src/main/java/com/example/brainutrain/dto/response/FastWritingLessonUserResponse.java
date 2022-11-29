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
public class FastWritingLessonUserResponse {

    private Long idFastWritingLesson;

    private int number;

    private String name;

    private String generatedCharacters;

    private Long idFastWritingCourse;

    private Double Score;
}
