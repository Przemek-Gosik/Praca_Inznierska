package com.example.brainutrain.repository;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.model.WritingText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WritingTextRepository extends JpaRepository<WritingText,Long> {

    List<WritingText> findAllByLevel(Level level);
}
