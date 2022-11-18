package com.example.brainutrain.dto.response;

import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.dto.UserDto;
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
public class ResponseWithToken {
    private UserDto userDto;
    private SettingDto settingDto;
    private String token;
}
