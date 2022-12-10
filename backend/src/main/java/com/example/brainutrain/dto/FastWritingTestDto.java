package com.example.brainutrain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FastWritingTestDto {

    private Long idFastWritingTest;

    @Lob
    @NotNull
    private String typedText;

    @NotNull
    private Double score;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private float time;

    @NotNull
    private Long idText;
}
