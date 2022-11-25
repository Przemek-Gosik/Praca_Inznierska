package com.example.brainutrain.repository;

import com.example.brainutrain.constants.TypeFastReading;
import com.example.brainutrain.model.FastReadingResult;
import com.example.brainutrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FastReadingResultRepository extends JpaRepository<FastReadingResult,Long> {

    List<FastReadingResult> findAllByUser(User user);

    List<FastReadingResult> findAllByUserAndAndType(User user, TypeFastReading type);
}
