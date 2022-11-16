package com.example.brainutrain.repository;

import com.example.brainutrain.model.User;
import com.example.brainutrain.model.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationCodeRepository extends JpaRepository <ValidationCode,Long> {

    Optional<ValidationCode> findValidationCodeByCodeAndUser(String code, User user);
    Optional<ValidationCode> findValidationCodeByUserIdUser(Long userId);
}
