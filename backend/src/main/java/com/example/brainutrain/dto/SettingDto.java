package com.example.brainutrain.dto;

import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Theme;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SettingDto {
    private Long idSetting;
    private FontSize fontSize;
    private Theme theme;
}
