package com.example.brainutrain.controller;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.dto.FastWritingCourseDto;
import com.example.brainutrain.dto.FastWritingLessonDto;
import com.example.brainutrain.dto.FastWritingModuleDto;
import com.example.brainutrain.dto.FastWritingTestDto;
import com.example.brainutrain.dto.FastWritingTextDto;
import com.example.brainutrain.dto.response.FastWritingModuleUserResponse;
import com.example.brainutrain.service.FastWritingService;
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

import javax.persistence.GeneratedValue;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/fast_writing",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FastWritingController {

    private final FastWritingService fastWritingService;

    @GetMapping("/guest")
    public ResponseEntity<List<FastWritingModuleDto>> getAllFastWritingModules(){
        return ResponseEntity.ok(fastWritingService.getAllModules());
    }

    @GetMapping("")
    public ResponseEntity<List<FastWritingModuleUserResponse>> getAllUserFastWritingModules(){
        return ResponseEntity.ok(fastWritingService.getAllUserModules());
    }

    @GetMapping("/guest/lesson/{id}")
    public ResponseEntity<FastWritingLessonDto> getFastWritingLessonById(@PathVariable("id") Long id){
        return ResponseEntity.ok(fastWritingService.getLessonById(id));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<FastWritingCourseDto> getFastWritingCourseById(@PathVariable("id") Long id){
        return ResponseEntity.ok(fastWritingService.getCourseById(id));
    }

    @PostMapping("/course")
    public ResponseEntity<Void> saveFastWritingCourseResult(@Valid @RequestBody FastWritingCourseDto fastWritingCourseDto){
        fastWritingService.saveNewCourse(fastWritingCourseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/course")
    public ResponseEntity<Void> updateFastWritingCourseResult(@Valid @RequestBody FastWritingCourseDto fastWritingCourseDto){
        fastWritingService.updateNewCourse(fastWritingCourseDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/guest/text")
    public ResponseEntity<List<FastWritingTextDto>> getAllFastWritingTexts(){
        return ResponseEntity.ok(fastWritingService.getAllTexts());
    }

    @GetMapping("/guest/text/{id}")
    public ResponseEntity <FastWritingTextDto> getFastWritingTextById(@PathVariable("id") Long id ){
        return ResponseEntity.ok(fastWritingService.getTextById(id));
    }

    @GetMapping("/guest/draw")
    public ResponseEntity <FastWritingTextDto> drawFastWritingText(){
        return ResponseEntity.ok(fastWritingService.drawText());
    }

    @GetMapping("/guest/draw/{level}")
    public ResponseEntity<FastWritingTextDto> drawFastWritingTextByLevel(@PathVariable Level level){
        return ResponseEntity.ok(fastWritingService.drawTextByLevel(level));
    }

    @PostMapping("/test")
    public ResponseEntity<Void> createNewFastWritingTest(@Valid @RequestBody FastWritingTestDto testDto){
        fastWritingService.createNewTest(testDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/test")
    public ResponseEntity<List<FastWritingTestDto>> getAllUserFastWritingTests(){
        return ResponseEntity.ok(fastWritingService.getAllUserTests());
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<FastWritingTestDto> getUserFastWritingTestById(@PathVariable("id") Long id){
        return ResponseEntity.ok(fastWritingService.getUserTestById(id));
    }
}
