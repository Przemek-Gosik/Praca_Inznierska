package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastWritingTextDto;
import com.example.brainutrain.model.FastWritingText;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface FastWritingTextMapper {

    FastWritingTextMapper INSTANCE = Mappers.getMapper(FastWritingTextMapper.class);

    FastWritingTextDto toDto(FastWritingText fastWritingText);

    List<FastWritingTextDto> toDto(Collection<FastWritingText> fastWritingTexts);

    FastWritingText fromDto(FastWritingTextDto fastWritingTextDto);
}
