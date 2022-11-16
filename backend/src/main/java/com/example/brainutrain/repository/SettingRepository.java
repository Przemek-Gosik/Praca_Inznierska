package com.example.brainutrain.repository;

import com.example.brainutrain.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting,Long> {
    Optional<Setting> findByUserIdUser(Long idUser);
}
