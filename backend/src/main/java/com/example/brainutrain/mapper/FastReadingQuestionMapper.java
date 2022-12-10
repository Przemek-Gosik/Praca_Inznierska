package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastReadingQuestionDto;
import com.example.brainutrain.model.FastReadingQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.Collection;
import java.util.List;

@Mapper
public interface FastReadingQuestionMapper {

    FastReadingQuestionMapper INSTANCE = Mappers.getMapper(FastReadingQuestionMapper.class);

    FastReadingQuestionDto toDto(FastReadingQuestion fastReadingQuestion);

    List<FastReadingQuestionDto> toDto(Collection<FastReadingQuestion> fastReadingQuestions);

    FastReadingQuestion fromDto(FastReadingQuestionDto fastReadingQuestionDto);
}
