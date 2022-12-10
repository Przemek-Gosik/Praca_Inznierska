package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastReadingResultDto;
import com.example.brainutrain.model.FastReadingResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface FastReadingResultMapper {

    FastReadingResultMapper INSTANCE = Mappers.getMapper(FastReadingResultMapper.class);


    FastReadingResultDto toDto(FastReadingResult fastReadingResult);

    List<FastReadingResultDto> toDto(Collection<FastReadingResult> fastReadingResults);

    FastReadingResult fromDto(FastReadingResultDto fastReadingResultDto);

    @Mapping(target = "idFastReadingResult",ignore = true)
    @Mapping(target = "user",ignore = true)
    FastReadingResult create(FastReadingResultDto fastReadingResultDto);

    default Timestamp fromLocalDateTime(LocalDateTime localDateTime){
        return  localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
