package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastWritingTestDto;
import com.example.brainutrain.model.FastWritingTest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface FastWritingTestMapper {

    FastWritingTestMapper INSTANCE = Mappers.getMapper(FastWritingTestMapper.class);

    FastWritingTestDto toDto(FastWritingTest fastWritingTest);

    List<FastWritingTestDto> toDto(Collection<FastWritingTest> fastWritingTests);

    FastWritingTest fromDto(FastWritingTestDto fastWritingTestDto);

    default Timestamp fromLocalDateTime(LocalDateTime localDateTime){
        return  localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
