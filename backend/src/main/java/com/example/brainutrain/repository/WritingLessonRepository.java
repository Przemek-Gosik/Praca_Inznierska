package com.example.brainutrain.repository;

import com.example.brainutrain.model.WritingLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WritingLessonRepository extends JpaRepository<WritingLesson,Long> {

    List<WritingLesson> findAllByModuleName(String moduleName);
}
