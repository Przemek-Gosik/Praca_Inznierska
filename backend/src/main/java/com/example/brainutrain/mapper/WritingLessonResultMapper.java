package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.WritingLessonResultDto;
import com.example.brainutrain.model.WritingLessonResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface WritingLessonResultMapper {

    WritingLessonResultMapper INSTANCE = Mappers.getMapper(WritingLessonResultMapper.class);

    WritingLessonResultDto toDto(WritingLessonResult writingLessonResult);

    List<WritingLessonResultDto> toDto(Collection<WritingLessonResult> writingLessonResults);

    WritingLessonResult fromDto(WritingLessonResultDto writingLessonResultDto);

    default Timestamp fromLocalDateTime(LocalDateTime localDateTime){
        return  localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
