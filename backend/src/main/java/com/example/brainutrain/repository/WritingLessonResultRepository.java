package com.example.brainutrain.repository;

import com.example.brainutrain.model.WritingLessonResult;
import com.example.brainutrain.model.WritingLesson;
import com.example.brainutrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WritingLessonResultRepository extends JpaRepository<WritingLessonResult,Long> {
    List<WritingLessonResult> findAllByUser(User user);
    Optional<WritingLessonResult> findByIdWritingLessonResult(Long id);
    Optional<WritingLessonResult> findByUserAndWritingLesson(User user,WritingLesson writingLesson);
    Boolean existsByUserAndWritingLesson(User user,WritingLesson writingLesson);
 }
