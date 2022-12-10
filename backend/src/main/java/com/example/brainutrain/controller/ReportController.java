package com.example.brainutrain.controller;

import com.example.brainutrain.dto.ReportDto;
import com.example.brainutrain.service.ReportService;
import com.example.brainutrain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/report",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Void> createNewReport(@Valid @RequestBody ReportDto reportDto){
        reportService.createReport(reportDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getReport(@PathVariable("id") Long id){
        return ResponseEntity.ok(reportService.getUserReport(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<ReportDto>> getAllReports(@PathVariable("username") String username){
        return ResponseEntity.ok(reportService.getAllUserReports(username));
    }
}
