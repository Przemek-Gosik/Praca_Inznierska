package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.ReadingQuestionDto;
import com.example.brainutrain.model.ReadingQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.Collection;
import java.util.List;

@Mapper
public interface ReadingQuestionMapper {

    ReadingQuestionMapper INSTANCE = Mappers.getMapper(ReadingQuestionMapper.class);

    ReadingQuestionDto toDto(ReadingQuestion readingQuestion);

    List<ReadingQuestionDto> toDto(Collection<ReadingQuestion> readingQuestions);

    ReadingQuestion fromDto(ReadingQuestionDto readingQuestionDto);
}
