package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.WritingModuleDto;
import com.example.brainutrain.dto.response.WritingModuleUserResponse;
import com.example.brainutrain.model.WritingModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.Collection;
import java.util.List;

@Mapper(uses = WritingLessonMapper.class )
public interface WritingModuleMapper {

    WritingModuleMapper INSTANCE = Mappers.getMapper(WritingModuleMapper.class);

    @Mapping(target = "lessons",ignore = true)
    WritingModuleDto toDto(WritingModule writingModule);

    List<WritingModuleDto> toDto(Collection<WritingModule> writingModules);

    WritingModule fromDto(WritingModuleDto writingModuleDto);

    WritingModuleUserResponse toUserResponse(WritingModule writingModule);

    List<WritingModuleUserResponse> toUserResponse(Collection<WritingModule> writingModules);

}
