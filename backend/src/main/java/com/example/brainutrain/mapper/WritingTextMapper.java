package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.WritingTextDto;
import com.example.brainutrain.model.WritingText;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.Collection;
import java.util.List;

@Mapper
public interface WritingTextMapper {

    WritingTextMapper INSTANCE = Mappers.getMapper(WritingTextMapper.class);

    WritingTextDto toDto(WritingText writingText);


    List<WritingTextDto> toDto(Collection<WritingText> writingTexts);

    WritingText fromDto(WritingTextDto writingTextDto);
}
