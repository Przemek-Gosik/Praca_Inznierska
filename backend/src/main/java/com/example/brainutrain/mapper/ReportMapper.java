package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.ReportDto;
import com.example.brainutrain.model.Report;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ReportMapper {

    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(target = "idUser",source = "user.idUser")
    @Mapping(target = "username",source = "user.login")
    ReportDto toDto(Report report);

    @Named("mapWithoutDetails")
    @Mapping(target = "text",ignore = true)
    @Mapping(target = "idUser",source = "user.idUser")
    @Mapping(target = "username",source = "user.login")
    ReportDto toDtoList(Report report);

    @IterableMapping(qualifiedByName = "mapWithoutDetails")
    @Mapping(target = "username",source = "user.login")
    List<ReportDto> toDto(Collection<Report> reports);

    @Mapping(target = "user",ignore = true)
    Report fromDto(ReportDto reportDto);
}
