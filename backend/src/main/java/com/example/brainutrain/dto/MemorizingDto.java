package com.example.brainutrain.dto;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeMemory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MemorizingDto {

    private Long idMemorizing;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeMemory type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Level level;

    @NotNull
    private Long score;

    private LocalDateTime startTime;
}
