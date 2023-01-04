package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.ReadingTextDto;
import com.example.brainutrain.model.ReadingText;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ReadingTextMapper {

    ReadingTextMapper INSTANCE = Mappers.getMapper(ReadingTextMapper.class);

    ReadingTextDto toDto(ReadingText readingText);

    @Named("mapWithoutDetails")
    @Mapping(target = "text",ignore = true)
    @Mapping(target = "questions",ignore = true)
    ReadingTextDto toDtoList(ReadingText readingText);

    @IterableMapping(qualifiedByName = "mapWithoutDetails")
    List<ReadingTextDto> toDto(Collection<ReadingText> readingTexts);


    ReadingText fromDto(ReadingTextDto readingTextDto);
}
