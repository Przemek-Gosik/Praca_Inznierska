package com.example.brainutrain.dto.response;

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
public class WritingModuleUserResponse {

    private Long idWritingModule;

    @NotNull
    private int number;

    @NotNull
    private String name;

    private List<WritingLessonUserResponse> writingLessons;
}
