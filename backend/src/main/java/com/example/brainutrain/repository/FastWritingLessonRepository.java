package com.example.brainutrain.repository;

import com.example.brainutrain.model.FastWritingLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FastWritingLessonRepository extends JpaRepository<FastWritingLesson,Long> {

    List<FastWritingLesson> findAllByModuleName(String moduleName);
}
