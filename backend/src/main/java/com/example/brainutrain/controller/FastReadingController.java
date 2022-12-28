package com.example.brainutrain.controller;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeFastReading;
import com.example.brainutrain.dto.FastReadingResultDto;
import com.example.brainutrain.dto.FastReadingTextDto;
import com.example.brainutrain.model.FastReadingText;
import com.example.brainutrain.service.FastReadingService;
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
        path = "/api/fast_reading",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FastReadingController {

    private final FastReadingService fastReadingService;

    @PostMapping()
    public ResponseEntity<Void> saveResult(@RequestBody FastReadingResultDto fastReadingResultDto){
        fastReadingService.createResult(fastReadingResultDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/text")
    public ResponseEntity<Void> saveText(@Valid @RequestBody FastReadingTextDto fastReadingTextDto){
        fastReadingService.createNewText(fastReadingTextDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<List<FastReadingResultDto>> getAllUserResults(){
        return ResponseEntity.ok(fastReadingService.getAllResults());
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<FastReadingResultDto>> getAllUserResultsByType(@PathVariable TypeFastReading type){
        return ResponseEntity.ok(fastReadingService.getAllResultsByType(type));
    }

    @GetMapping("/text/guest")
    public ResponseEntity<List<FastReadingTextDto>> getAllTexts(){
        return ResponseEntity.ok(fastReadingService.getAllTexts());
    }

    @GetMapping("/text/level/{level}")
    public ResponseEntity<List<FastReadingTextDto>> getAllTextsByLevel(@PathVariable Level level){
        return ResponseEntity.ok(fastReadingService.getAllTextsByLevel(level));
    }

    @GetMapping("/text/guest/{id}")
    public ResponseEntity<FastReadingTextDto> getText(@PathVariable("id") Long id){
        return ResponseEntity.ok(fastReadingService.getText(id));
    }

    @GetMapping("/text/guest/draw")
    public ResponseEntity<FastReadingTextDto> drawText(){
        return ResponseEntity.ok(fastReadingService.drawText());
    }

    @GetMapping("/text/guest/draw/{level}")
    public ResponseEntity<FastReadingTextDto> drawTextByLevel(@PathVariable("level") String level){
        return ResponseEntity.ok(fastReadingService.drawTextByLevel(level));
    }

    @GetMapping("/text/guest/numbers/{level}")
    public ResponseEntity<List<Long>> getNumbers(@PathVariable("level") String level){
        return ResponseEntity.ok(fastReadingService.getSchubertNumbers(level));
    }
}
