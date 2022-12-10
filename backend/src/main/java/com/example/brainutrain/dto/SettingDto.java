package com.example.brainutrain.dto;

import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Theme;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SettingDto {

    private Long idSetting;
    @NotNull
    private FontSize fontSize;
    @NotNull
    private Theme theme;
}
