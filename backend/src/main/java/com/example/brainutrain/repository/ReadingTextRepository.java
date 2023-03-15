package com.example.brainutrain.repository;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.model.ReadingText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingTextRepository extends JpaRepository<ReadingText,Long> {

    List<ReadingText> findAllByLevel(Level level);

    Boolean existsByTitle(String title);
}
