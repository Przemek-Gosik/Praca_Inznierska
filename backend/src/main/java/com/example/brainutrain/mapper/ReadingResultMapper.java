package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.ReadingResultDto;
import com.example.brainutrain.model.ReadingResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface ReadingResultMapper {

    ReadingResultMapper INSTANCE = Mappers.getMapper(ReadingResultMapper.class);


    ReadingResultDto toDto(ReadingResult readingResult);

    List<ReadingResultDto> toDto(Collection<ReadingResult> readingResults);

    ReadingResult fromDto(ReadingResultDto readingResultDto);

    @Mapping(target = "idReadingResult",ignore = true)
    @Mapping(target = "user",ignore = true)
    ReadingResult create(ReadingResultDto readingResultDto);

    default Timestamp fromLocalDateTime(LocalDateTime localDateTime){
        return  localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
