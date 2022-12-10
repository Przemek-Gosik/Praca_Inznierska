package com.example.brainutrain.repository;


import com.example.brainutrain.constants.TypeMemory;
import com.example.brainutrain.model.Memorizing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MemorizingRepository extends JpaRepository<Memorizing,Long> {

    Optional<Memorizing> findMemorizingByIdMemorizing(Long idMemorizing);

    List<Memorizing> findAllByUserIdUser(Long idUser);

    List<Memorizing> findAllByUserIdUserAndTypeOrderByStartTime(Long idUser, TypeMemory type);
}
