package com.example.brainutrain.dto;

import com.example.brainutrain.constants.TypeFastReading;
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
public class FastReadingResultDto {

    private Long id;

    @NotNull
    private TypeFastReading type;

    @NotNull
    private Double score;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private float time;
}
