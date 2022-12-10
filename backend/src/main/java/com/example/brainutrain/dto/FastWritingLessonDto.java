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

    @NotNull(message = "Numer lekcji nie może być pusty")
    private int number;

    @NotNull(message = "Nazwa Lekcji nie może być pusta")
    private String name;

    @NotNull(message = "Wartość generowanych znaków nie może być pusta")
    @Size(max=10,message = "Nie można podać więcej niż 10 znaków")
    private String generatedCharacters;

    @Size(max = 10)
    private String[] text;
}
