package com.example.brainutrain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FastWritingModuleDto {

    private Long idFastWritingModule;

    @NotNull
    private int number;

    @NotNull
    private String name;

    private List<FastWritingLessonDto> fastWritingLessons;

}
