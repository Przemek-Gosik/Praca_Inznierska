package com.example.brainutrain.repository;

import com.example.brainutrain.model.WritingTextResult;
import com.example.brainutrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WritingTextResultRepository extends JpaRepository<WritingTextResult,Long> {

    List<WritingTextResult> findAllByUser(User user);

    Optional<WritingTextResult> findByIdWritingTextResult(Long id);
}
