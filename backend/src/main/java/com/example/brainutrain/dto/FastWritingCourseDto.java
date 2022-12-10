package com.example.brainutrain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FastWritingCourseDto {

    private Long idFastWritingCourse;

    @NotNull
    @Min(value = 0,message = "Wynik nie może byc liczbą mniejszą od 0")
    @Max(value=1,message = "Wynik nie może być liczbą większą od 1")
    private Double score;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private float time;

    private int numberOfAttempts;

    private Long idFastWritingLesson;
}
