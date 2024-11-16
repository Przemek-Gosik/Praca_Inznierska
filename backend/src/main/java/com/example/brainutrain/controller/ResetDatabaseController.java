package com.example.brainutrain.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping()
@Slf4j
public class ResetDatabaseController {

    private final Flyway flyway;

    @PostMapping("/reset-database")
    public ResponseEntity<String> resetDatabase() {
        try {
            flyway.clean();
            flyway.migrate();
            return ResponseEntity.ok("Database reset successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset database: "+ e.getMessage());
        }
    }
}
