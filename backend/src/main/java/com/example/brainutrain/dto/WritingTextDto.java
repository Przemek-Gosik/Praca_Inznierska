package com.example.brainutrain.dto;

import com.example.brainutrain.constants.Level;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class WritingTextDto {

    private Long idWritingText;

    @NotNull
    private String title;

    @Lob
    @NotNull
    private String text;

    @NotNull
    private Level level;
}
