package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.model.Setting;
import org.mapstruct.Mapper;

@Mapper
public interface SettingMapper {

    SettingDto toDto(Setting setting);

    Setting fromDto(SettingDto settingDto);
}
