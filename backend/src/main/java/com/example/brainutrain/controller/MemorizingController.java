package com.example.brainutrain.controller;

import com.example.brainutrain.constants.TypeMemory;
import com.example.brainutrain.dto.MemorizingDto;
import com.example.brainutrain.service.MemorizingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/memorizing",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MemorizingController {

    private final MemorizingService memorizingService;

    @PostMapping("")
    public ResponseEntity<Void> saveMemorizingResult(@Valid @RequestBody MemorizingDto memorizingDto){
        memorizingService.saveMemorizing(memorizingDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/memory")
    public ResponseEntity<List<MemorizingDto>> getMemoryResults(){
        return ResponseEntity.ok(memorizingService.getAllUserResultsByType(TypeMemory.MEMORY));
    }

    @GetMapping("/mnemonics")
    public ResponseEntity<List<MemorizingDto>> getMnemonicsResults(){
        return ResponseEntity.ok(memorizingService.getAllUserResultsByType(TypeMemory.MNEMONICS));
    }
}
