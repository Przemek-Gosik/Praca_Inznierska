package com.example.brainutrain.repository;


import com.example.brainutrain.constants.TypeMemory;
import com.example.brainutrain.model.Memorizing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MemorizingRepository extends JpaRepository<Memorizing,Long> {

    Optional<Memorizing> findMemorizingByIdMemorizing(Long idMemorizing);

    List<Memorizing> findAllByUserIdUserAndTypeOrderByStartTime(Long idUser, TypeMemory type);
}
