package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastWritingModuleDto;
import com.example.brainutrain.dto.response.FastWritingModuleUserResponse;
import com.example.brainutrain.model.FastWritingModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.Collection;
import java.util.List;

@Mapper(uses = FastWritingLessonMapper.class )
public interface FastWritingModuleMapper {

    FastWritingModuleMapper INSTANCE = Mappers.getMapper(FastWritingModuleMapper.class);

    @Mapping(target = "lessons",ignore = true)
    FastWritingModuleDto toDto(FastWritingModule fastWritingModule);

    List<FastWritingModuleDto> toDto(Collection<FastWritingModule> fastWritingModules);

    FastWritingModule fromDto(FastWritingModuleDto fastWritingModuleDto);

    FastWritingModuleUserResponse toUserResponse(FastWritingModule fastWritingModule);

    List<FastWritingModuleUserResponse> toUserResponse(Collection<FastWritingModule> fastWritingModules);

}
