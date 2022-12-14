package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.request.RegisterRequest;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roles",target = "roles")
    UserDto toDto(User user);

    @Mapping(target = "roles",ignore = true)
    List<UserDto> toDto(Collection<User> users);

    User fromDto(RegisterRequest registerRequest);
}
