package com.example.brainutrain.repository;

import com.example.brainutrain.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ReportRepository extends JpaRepository<Report,Long> {

    Optional<Report> findByIdReportAndActiveTrue(Long id);

    List<Report> findAllByActiveTrue();

    List<Report> findAllByUserLoginAndActiveTrue(String login);
}
