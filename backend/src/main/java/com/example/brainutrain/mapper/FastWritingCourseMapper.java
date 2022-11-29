package com.example.brainutrain.mapper;

import com.example.brainutrain.dto.FastWritingCourseDto;
import com.example.brainutrain.model.FastWritingCourse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface FastWritingCourseMapper {

    FastWritingCourseMapper INSTANCE = Mappers.getMapper(FastWritingCourseMapper.class);

    FastWritingCourseDto toDto(FastWritingCourse fastWritingCourse);

    List<FastWritingCourseDto> toDto(Collection<FastWritingCourse> fastWritingCourses);

    FastWritingCourse fromDto(FastWritingCourseDto fastWritingCourseDto);

    default Timestamp fromLocalDateTime(LocalDateTime localDateTime){
        return  localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
