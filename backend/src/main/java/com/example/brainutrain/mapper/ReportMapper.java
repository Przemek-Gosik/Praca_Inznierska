package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.ReportDto;
import com.example.brainutrain.model.Report;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface ReportMapper {

    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    ReportDto toDto(Report report);

    @Named("mapWithoutDetails")
    @Mapping(target = "text",ignore = true)
    ReportDto toDtoList(Report report);

    @IterableMapping(qualifiedByName = "mapWithoutDetails")
    List<ReportDto> toDto(Collection<Report> reports);

    @Mapping(target = "user",ignore = true)
    Report fromDto(ReportDto reportDto);

    default Timestamp fromLocalDateTime(LocalDateTime localDateTime){
        return  localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
