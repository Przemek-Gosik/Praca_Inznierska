package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastReadingTextDto;
import com.example.brainutrain.model.FastReadingText;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface FastReadingTextMapper {

    FastReadingTextMapper INSTANCE = Mappers.getMapper(FastReadingTextMapper.class);

    FastReadingTextDto toDto(FastReadingText fastReadingText);

    @Named("mapWithoutDetails")
    @Mapping(target = "text",ignore = true)
    @Mapping(target = "questions",ignore = true)
    FastReadingTextDto toDtoList(FastReadingText fastReadingText);

    @IterableMapping(qualifiedByName = "mapWithoutDetails")
    List<FastReadingTextDto> toDto(Collection<FastReadingText> fastReadingTexts);


    FastReadingText fromDto(FastReadingTextDto fastReadingTextDto);
}
