package com.example.brainutrain.repository;

import com.example.brainutrain.model.FastWritingCourse;
import com.example.brainutrain.model.FastWritingLesson;
import com.example.brainutrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FastWritingCourseRepository extends JpaRepository<FastWritingCourse,Long> {
    List<FastWritingCourse> findAllByUser(User user);

    Optional<FastWritingCourse> findByUserAndAndFastWritingLesson(User user,FastWritingLesson lesson);
    Optional<FastWritingCourse> findByIdFastWritingCourse(Long id);

    Boolean existsByUserAndAndFastWritingLesson(User user, FastWritingLesson lesson);

 }
