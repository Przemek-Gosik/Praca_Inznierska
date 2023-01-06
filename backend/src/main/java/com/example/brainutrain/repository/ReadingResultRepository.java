package com.example.brainutrain.repository;

import com.example.brainutrain.constants.TypeReading;
import com.example.brainutrain.model.ReadingResult;
import com.example.brainutrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingResultRepository extends JpaRepository<ReadingResult,Long> {

    List<ReadingResult> findAllByUser(User user);

    List<ReadingResult> findAllByUserAndAndType(User user, TypeReading type);
}
