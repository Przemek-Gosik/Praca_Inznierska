package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastWritingLessonDto;
import com.example.brainutrain.model.FastWritingLesson;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.Collection;
import java.util.List;

@Mapper
public interface FastWritingLessonMapper {

    FastWritingLessonMapper INSTANCE = Mappers.getMapper(FastWritingLessonMapper.class);

    FastWritingLessonDto toDto(FastWritingLesson fastWritingLesson);

    List<FastWritingLessonDto> toDto(Collection<FastWritingLesson> fastWritingLessons);

    FastWritingLesson fromDto(FastWritingLessonDto fastWritingLessonDto);


}
