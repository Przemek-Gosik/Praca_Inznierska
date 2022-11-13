package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.RoleDto;
import com.example.brainutrain.model.Role;
import org.mapstruct.Mapper;
import java.util.Collection;
import java.util.List;

@Mapper
public interface RoleMapper {

    RoleDto toDto(Role role);
    List<RoleDto> toDto(Collection<Role> roles);
    Role fromDto(RoleDto roleDto);
}
