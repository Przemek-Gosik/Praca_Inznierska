package com.example.brainutrain.repository;

import com.example.brainutrain.model.ReadingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingQuestionRepository extends JpaRepository<ReadingQuestion,Long> {

    List<ReadingQuestion> findAllByReadingTextIdReadingText(Long id);
}
