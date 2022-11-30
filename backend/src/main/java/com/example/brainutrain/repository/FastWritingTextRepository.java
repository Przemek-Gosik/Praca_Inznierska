package com.example.brainutrain.repository;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.model.FastWritingText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FastWritingTextRepository extends JpaRepository<FastWritingText,Long> {

    List<FastWritingText> findAllByLevel(Level level);
}
