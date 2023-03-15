package com.example.brainutrain.dto;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeReading;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadingResultDto {

    private Long idReadingResult;

    @NotNull
    private TypeReading type;

    @NotNull
    private Level level;

    @NotNull
    private Double score;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private float time;

    private Long idFastReadingText;
}
