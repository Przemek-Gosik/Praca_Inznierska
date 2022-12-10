package com.example.brainutrain.mapper;

import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Theme;
import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.model.Setting;
import com.example.brainutrain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SettingMapperTest {

    private User user;

    private Setting setting;

    private SettingMapper settingMapper;

    @Test
    public void INSTANCE_getSettingMapperClass(){
        assertInstanceOf(SettingMapper.class,SettingMapper.INSTANCE);
    }

    @BeforeEach
    public void init(){
        settingMapper = SettingMapper.INSTANCE;
        user = new User(1L,"login","email@com.pl","pass",false,true,null);
        setting = new Setting(1L, FontSize.MEDIUM, Theme.CONTRAST,user);
    }

    @Test
    public void toDto_givenEntity_getDto(){
        SettingDto settingDto = settingMapper.toDto(setting);
        assertAll(
                ()->assertEquals(setting.getFontSize(),settingDto.getFontSize()),
                ()->assertEquals(setting.getTheme(),settingDto.getTheme())
        );
    }

    @Test
    public void fromDto_givenDto_getEntity(){
        SettingDto settingDto = new SettingDto(null,setting.getFontSize(),setting.getTheme());
        Setting setting1 = settingMapper.fromDto(settingDto);
        assertAll(
                ()->assertNull(setting1.getIdSetting()),
                ()->assertNull(setting1.getUser()),
                ()->assertEquals(settingDto.getFontSize(),setting1.getFontSize()),
                ()->assertEquals(settingDto.getTheme(),setting1.getTheme())
        );
    }

}
