package com.example.brainutrain.controller;

import com.example.brainutrain.dto.ReportDto;
import com.example.brainutrain.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Void> createNewUserReport(@Valid @RequestBody ReportDto reportDto){
        reportService.createUserReport(reportDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/guest")
    public ResponseEntity<Void> createNewReport(@Valid @RequestBody ReportDto reportDto){
        reportService.createReport(reportDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getReport(@PathVariable("id") Long id){
        return ResponseEntity.ok(reportService.getUserReport(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ReportDto>> getAllReports(){
        return ResponseEntity.ok(reportService.getAllReports());
    }
}
