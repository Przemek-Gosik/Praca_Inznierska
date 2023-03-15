package com.example.brainutrain.dto;

import com.example.brainutrain.constants.Level;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ReadingTextDto {

    private Long idReadingText;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;

    private List<ReadingQuestionDto> questions;
}
