package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastWritingLessonDto;
import com.example.brainutrain.model.FastWritingLesson;
import com.sun.tools.javac.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface FastWritingLessonMapper {

    FastWritingLessonMapper INSTANCE = Mappers.getMapper(FastWritingLessonMapper.class);

    FastWritingLessonDto toDto(FastWritingLesson fastWritingLesson);

    List<FastWritingLessonDto> toDto(Collection<FastWritingLesson> fastWritingLessons);

}
