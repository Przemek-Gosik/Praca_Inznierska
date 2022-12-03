package com.example.brainutrain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FastWritingLessonDto {

    private Long idFastWritingLesson;

    @NotNull
    private int number;

    @NotNull
    private String name;

    @NotNull
    @Size(max=10,message = "Może być maksymalnie 10 znaków")
    private String generatedCharacters;

    private String text;
}
