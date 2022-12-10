package com.example.brainutrain.repository;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.model.FastReadingText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FastReadingTextRepository extends JpaRepository<FastReadingText,Long> {

    List<FastReadingText> findAllByLevel(Level level);

    Boolean existsByTitle(String title);
}
