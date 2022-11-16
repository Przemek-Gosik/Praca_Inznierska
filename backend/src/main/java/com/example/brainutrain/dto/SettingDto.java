package com.example.brainutrain.dto;

import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Theme;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SettingDto {

    @NotBlank
    private Long idSetting;
    @NotBlank
    private FontSize fontSize;
    @NotBlank
    private Theme theme;
}
