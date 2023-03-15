package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.WritingTextResultDto;
import com.example.brainutrain.model.WritingTextResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface WritingTextResultMapper {

    WritingTextResultMapper INSTANCE = Mappers.getMapper(WritingTextResultMapper.class);

    @Mapping(target = "title",source = "text.title")
    WritingTextResultDto toDto(WritingTextResult writingTextResult);

    List<WritingTextResultDto> toDto(Collection<WritingTextResult> writingTextResults);

    WritingTextResult fromDto(WritingTextResultDto writingTextResultDto);

    default Timestamp fromLocalDateTime(LocalDateTime localDateTime){
        return  localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
