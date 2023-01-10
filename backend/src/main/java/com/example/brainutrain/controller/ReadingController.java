package com.example.brainutrain.controller;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeReading;
import com.example.brainutrain.dto.ReadingResultDto;
import com.example.brainutrain.dto.ReadingTextDto;
import com.example.brainutrain.dto.response.FindingNumbersResponse;
import com.example.brainutrain.service.ReadingService;
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
public class ReadingController {

    private final ReadingService readingService;

    @PostMapping()
    public ResponseEntity<Void> saveResult(@RequestBody ReadingResultDto readingResultDto){
        readingService.createResult(readingResultDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/text")
    public ResponseEntity<Void> saveText(@Valid @RequestBody ReadingTextDto readingTextDto){
        readingService.createNewText(readingTextDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<List<ReadingResultDto>> getAllUserResults(){
        return ResponseEntity.ok(readingService.getAllResults());
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<ReadingResultDto>> getAllUserResultsByType(@PathVariable TypeReading type){
        return ResponseEntity.ok(readingService.getAllResultsByType(type));
    }

    @GetMapping("/text/guest")
    public ResponseEntity<List<ReadingTextDto>> getAllTexts(){
        return ResponseEntity.ok(readingService.getAllTexts());
    }

    @GetMapping("/text/level/{level}")
    public ResponseEntity<List<ReadingTextDto>> getAllTextsByLevel(@PathVariable Level level){
        return ResponseEntity.ok(readingService.getAllTextsByLevel(level));
    }

    @GetMapping("/text/guest/{id}")
    public ResponseEntity<ReadingTextDto> getText(@PathVariable("id") Long id){
        return ResponseEntity.ok(readingService.getText(id));
    }

    @GetMapping("/text/guest/draw")
    public ResponseEntity<ReadingTextDto> drawText(){
        return ResponseEntity.ok(readingService.drawText());
    }

    @GetMapping("/text/guest/draw/{level}")
    public ResponseEntity<ReadingTextDto> drawTextByLevel(@PathVariable("level") String level){
        return ResponseEntity.ok(readingService.drawTextByLevel(level));
    }

    @GetMapping("/text/guest/numbers/{level}")
    public ResponseEntity<List<Long>> getNumbers(@PathVariable("level") String level){
        return ResponseEntity.ok(readingService.createSchultzTable(level));
    }

    @GetMapping("/text/guest/finding_numbers/{level}")
    public ResponseEntity<FindingNumbersResponse> getNumbersForGame(@PathVariable("level") String level){
        return ResponseEntity.ok(readingService.createFindingNumbersGame(level));
    }
}
