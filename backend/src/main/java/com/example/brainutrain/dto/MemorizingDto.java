package com.example.brainutrain.dto;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeMemory;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    @NotNull(message = "Typ testu na zapamiętywanie nie może być pusty!")
    private TypeMemory type;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Poziom testu na zapamiętywanie nie może być pusty!")
    private Level level;

    @NotNull(message = "Wynik testu na zapamiętywanie nie może być pusty!")
    private Long score;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;
}
