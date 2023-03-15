package com.example.brainutrain.controller;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.dto.WritingLessonDto;
import com.example.brainutrain.dto.WritingLessonResultDto;
import com.example.brainutrain.dto.WritingModuleDto;
import com.example.brainutrain.dto.WritingTextDto;
import com.example.brainutrain.dto.WritingTextResultDto;
import com.example.brainutrain.dto.response.WritingModuleUserResponse;
import com.example.brainutrain.service.WritingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/fast_writing",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class WritingController {

    private final WritingService writingService;

    @GetMapping("/guest")
    public ResponseEntity<List<WritingModuleDto>> getAllWritingModules(){
        return ResponseEntity.ok(writingService.getAllModules());
    }

    @GetMapping("")
    public ResponseEntity<List<WritingModuleUserResponse>> getAllUserWritingModules(){
        return ResponseEntity.ok(writingService.getAllUserModules());
    }

    @GetMapping("/guest/lesson/{id}")
    public ResponseEntity<WritingLessonDto> getWritingLessonById(@PathVariable("id") Long id){
        return ResponseEntity.ok(writingService.getLessonById(id));
    }

    @GetMapping("/lesson/result/{id}")
    public ResponseEntity<WritingLessonResultDto> getWritingLessonResultById(@PathVariable("id") Long id){
        return ResponseEntity.ok(writingService.getLessonResultById(id));
    }

    @PostMapping("/lesson/result")
    public ResponseEntity<Void> saveWritingLessonResult(@Valid @RequestBody WritingLessonResultDto writingLessonResultDto){
        writingService.saveNewLessonResult(writingLessonResultDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/lesson/result")
    public ResponseEntity<Void> updateWritingLessonResult(@Valid @RequestBody WritingLessonResultDto writingLessonResultDto){
        writingService.updateLessonResult(writingLessonResultDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/guest/text")
    public ResponseEntity<List<WritingTextDto>> getAllWritingTexts(){
        return ResponseEntity.ok(writingService.getAllTexts());
    }

    @GetMapping("guest/text/level/{level}")
    public ResponseEntity<List<WritingTextDto>> getAllWritingTextsByLevel(@PathVariable("level") String level){
        return ResponseEntity.ok(writingService.getAllTextsByLevel(level));
    }

    @GetMapping("/guest/text/{id}")
    public ResponseEntity <WritingTextDto> getWritingTextById(@PathVariable("id") Long id ){
        return ResponseEntity.ok(writingService.getTextById(id));
    }

    @GetMapping("/guest/text/draw")
    public ResponseEntity <WritingTextDto> drawWritingText(){
        return ResponseEntity.ok(writingService.drawText());
    }

    @GetMapping("/guest/text/draw/{level}")
    public ResponseEntity<WritingTextDto> drawWritingTextByLevel(@PathVariable Level level){
        return ResponseEntity.ok(writingService.drawTextByLevel(level));
    }

    @PostMapping("/text/result")
    public ResponseEntity<Void> createNewWritingTextResult(@Valid @RequestBody WritingTextResultDto textResultDto){
        writingService.createNewTextResult(textResultDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/text/result")
    public ResponseEntity<List<WritingTextResultDto>> getAllUserWritingTextResults(){
        return ResponseEntity.ok(writingService.getAllUserTextResults());
    }

    @GetMapping("/text/result/{id}")
    public ResponseEntity<WritingTextResultDto> getUserWritingTextResultById(@PathVariable("id") Long id){
        return ResponseEntity.ok(writingService.getUserTextResultById(id));
    }
}
