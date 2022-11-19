package com.example.brainutrain.repository;

import com.example.brainutrain.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findUserByLogin(String login);
    Optional<User> findUserByIdUser(Long id);
    Optional<User> findUsersByEmail(String email);
    Boolean existsByLogin(String login);
    Boolean existsByEmail(String email);
    List<User> findUsersByLoginIsNotLike(String login);
}
