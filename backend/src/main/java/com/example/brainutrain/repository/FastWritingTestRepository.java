package com.example.brainutrain.repository;

import com.example.brainutrain.model.FastWritingTest;
import com.example.brainutrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FastWritingTestRepository extends JpaRepository<FastWritingTest,Long> {

    List<FastWritingTest> findAllByUser(User user);
}
