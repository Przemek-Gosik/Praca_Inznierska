package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.WritingLessonDto;
import com.example.brainutrain.dto.response.WritingLessonUserResponse;
import com.example.brainutrain.model.WritingLesson;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.Collection;
import java.util.List;

@Mapper
public interface WritingLessonMapper {

    WritingLessonMapper INSTANCE = Mappers.getMapper(WritingLessonMapper.class);

    WritingLessonDto toDto(WritingLesson writingLesson);

    List<WritingLessonDto> toDto(Collection<WritingLesson> writingLessons);

    WritingLesson fromDto(WritingLessonDto writingLessonDto);

    WritingLessonUserResponse toUserResponse(WritingLesson writingLesson);

    List<WritingLessonUserResponse> toUserResponse(Collection<WritingLesson> writingLessons);

}
