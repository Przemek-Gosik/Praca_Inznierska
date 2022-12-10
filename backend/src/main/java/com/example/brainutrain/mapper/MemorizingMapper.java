package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.MemorizingDto;
import com.example.brainutrain.model.Memorizing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface MemorizingMapper {

    MemorizingMapper INSTANCE = Mappers.getMapper(MemorizingMapper.class);

    MemorizingDto toDto(Memorizing memorizing);

    List<MemorizingDto> toDto(Collection<Memorizing> memorizingList);

    @Mapping(target = "user",ignore = true)
    @Mapping(target = "idMemorizing",ignore = true)
    Memorizing fromDto(MemorizingDto memorizingDto);

    default Timestamp fromLocalDateTime(LocalDateTime localDateTime){
        return  localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
