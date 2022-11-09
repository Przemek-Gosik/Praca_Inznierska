package com.example.brainutrain.repository;

import com.example.brainutrain.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findUserByLogin(String login);
    Optional<User> findUserByIdUser(Long id);
    Boolean existsByLogin(String login);
    Boolean existsByEmail(String email);
    List<User> findAllBy(Pageable pageable);
}
