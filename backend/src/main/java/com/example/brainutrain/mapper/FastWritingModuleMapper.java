package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastWritingModuleDto;
import com.example.brainutrain.model.FastWritingModule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.Collection;
import java.util.List;

@Mapper(uses = FastWritingLessonMapper.class )
public interface FastWritingModuleMapper {

    FastWritingModuleMapper INSTANCE = Mappers.getMapper(FastWritingModuleMapper.class);

    FastWritingModuleDto toDto(FastWritingModule fastWritingModule);

    List<FastWritingModuleDto> toDto(Collection<FastWritingModule> fastWritingModules);

    FastWritingModule fromDto(FastWritingModuleDto fastWritingModuleDto);

}
