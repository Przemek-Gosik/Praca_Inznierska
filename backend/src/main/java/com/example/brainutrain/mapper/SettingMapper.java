package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.model.Setting;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SettingMapper {

    SettingMapper INSTANCE = Mappers.getMapper(SettingMapper.class);

    SettingDto toDto(Setting setting);

    Setting fromDto(SettingDto settingDto);
}
