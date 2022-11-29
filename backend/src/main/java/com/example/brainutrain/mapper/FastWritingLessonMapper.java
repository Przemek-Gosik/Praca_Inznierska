package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastWritingLessonDto;
import com.example.brainutrain.dto.response.FastWritingLessonUserResponse;
import com.example.brainutrain.model.FastWritingLesson;
import com.sun.tools.javac.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface FastWritingLessonMapper {

    FastWritingLessonMapper INSTANCE = Mappers.getMapper(FastWritingLessonMapper.class);

    FastWritingLessonDto toDto(FastWritingLesson fastWritingLesson);

    List<FastWritingLessonDto> toDto(Collection<FastWritingLesson> fastWritingLessons);

    FastWritingLesson fromDto(FastWritingLessonDto fastWritingLessonDto);

    @Named("mapForUserResponse")
    @Mapping(target = "idFastWritingCourse",source = "fastWritingCourse.idFastWritingCourse")
    @Mapping(target = "score",source = "fastWritingCourse.source")
    FastWritingLessonUserResponse toResponse(FastWritingLesson fastWritingLesson);

    @IterableMapping(qualifiedByName = "mapForUserResponse")
    List<FastWritingLessonUserResponse> toResponseList(FastWritingLesson fastWritingLesson);

}
