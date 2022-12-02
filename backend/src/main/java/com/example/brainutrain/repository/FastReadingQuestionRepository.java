package com.example.brainutrain.repository;

import com.example.brainutrain.model.FastReadingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FastReadingQuestionRepository extends JpaRepository<FastReadingQuestion,Long> {

    List<FastReadingQuestion> findAllByFastReadingTextIdFastReadingText(Long id);
}
