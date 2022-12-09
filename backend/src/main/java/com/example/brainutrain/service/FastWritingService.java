package com.example.brainutrain.service;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.dto.FastWritingCourseDto;
import com.example.brainutrain.dto.FastWritingLessonDto;
import com.example.brainutrain.dto.FastWritingModuleDto;
import com.example.brainutrain.dto.FastWritingTestDto;
import com.example.brainutrain.dto.FastWritingTextDto;
import com.example.brainutrain.dto.response.FastWritingLessonUserResponse;
import com.example.brainutrain.dto.response.FastWritingModuleUserResponse;
import com.example.brainutrain.exception.AlreadyExistsException;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.mapper.FastWritingCourseMapper;
import com.example.brainutrain.mapper.FastWritingLessonMapper;
import com.example.brainutrain.mapper.FastWritingModuleMapper;
import com.example.brainutrain.mapper.FastWritingTestMapper;
import com.example.brainutrain.mapper.FastWritingTextMapper;
import com.example.brainutrain.model.FastWritingCourse;
import com.example.brainutrain.model.FastWritingLesson;
import com.example.brainutrain.model.FastWritingModule;
import com.example.brainutrain.model.FastWritingTest;
import com.example.brainutrain.model.FastWritingText;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.FastWritingCourseRepository;
import com.example.brainutrain.repository.FastWritingLessonRepository;
import com.example.brainutrain.repository.FastWritingModuleRepository;
import com.example.brainutrain.repository.FastWritingTestRepository;
import com.example.brainutrain.repository.FastWritingTextRepository;
import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.utils.StringGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
@Slf4j
public class FastWritingService {

    private final FastWritingModuleRepository moduleRepository;

    private final FastWritingLessonRepository lessonRepository;

    private final FastWritingCourseRepository courseRepository;

    private final FastWritingTestRepository testRepository;

    private final FastWritingTextRepository textRepository;

    private final AuthenticationUtils authenticationUtils;

    private final StringGenerator stringGenerator;

    public List<FastWritingModuleDto> getAllModules(){
        List<FastWritingModule> modules = moduleRepository.findAll();
        List<FastWritingModuleDto> moduleDtoList = FastWritingModuleMapper.INSTANCE.toDto(modules);
        for(FastWritingModuleDto moduleDto : moduleDtoList){
            List<FastWritingLesson> writingLessons = lessonRepository.findAllByModuleNameAndOrderByName(moduleDto.getName());
            List<FastWritingLessonDto> writingLessonDtoList = FastWritingLessonMapper.INSTANCE.toDto(writingLessons);
            moduleDto.setLessons(writingLessonDtoList);
            log.info(Integer.toString(moduleDto.getLessons().size()));
        }
        return moduleDtoList;
    }

    public List<FastWritingModuleUserResponse> getAllUserModules(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<FastWritingModule> modules = moduleRepository.findAll();
        List<FastWritingModuleUserResponse> moduleUserResponseList = FastWritingModuleMapper.INSTANCE.toUserResponse(modules);
        for(FastWritingModuleUserResponse moduleUserResponse : moduleUserResponseList){
            List<FastWritingLesson> writingLessons = lessonRepository.findAllByModuleNameAndOrderByName(moduleUserResponse.getName());
            List<FastWritingLessonUserResponse> lessonUserResponseList = writingLessons
                    .stream().map(
                            writingLesson -> {
                                FastWritingLessonUserResponse lessonUserResponse =
                                        FastWritingLessonMapper.INSTANCE.toUserResponse(writingLesson);
                                FastWritingCourse course = courseRepository.findByUserAndAndFastWritingLesson(user,writingLesson).orElse(
                                        new FastWritingCourse(null,0.0)
                                );
                                lessonUserResponse.setIdFastWritingCourse(course.getIdFastWritingCourse());
                                lessonUserResponse.setScore(course.getScore());
                                return lessonUserResponse;
                            }).toList();
            moduleUserResponse.setFastWritingLessons(lessonUserResponseList);
        }
        return moduleUserResponseList;
    }

    public FastWritingLessonDto getLessonById(Long id){
        FastWritingLesson lesson = lessonRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono lekcji szybkiego pisania dla id: "+id)
        );
        FastWritingLessonDto lessonDto = FastWritingLessonMapper.INSTANCE.toDto(lesson);
        lessonDto.setText(stringGenerator.generateTexts(lessonDto.getGeneratedCharacters()));
        return lessonDto;
    }

    public FastWritingCourseDto getCourseById(Long id){
        User user = authenticationUtils.getUserFromAuthentication();
        FastWritingCourse course = courseRepository.findByIdFastWritingCourse(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono wyniku lekcji szybkiego pisania dla id: "+id)
        );
        if(!course.getUser().equals(user)){
            throw new AccessDeniedException("Brak dostepu do kursu o id: "+id);
        }
        return FastWritingCourseMapper.INSTANCE.toDto(course);
    }

    public void saveNewCourse(FastWritingCourseDto courseDto){
        User user = authenticationUtils.getUserFromAuthentication();
        FastWritingLesson lesson = lessonRepository.findById(courseDto.getIdFastWritingLesson()).
                orElseThrow(()->new ResourceNotFoundException("Nie odnaleziono lekcji szybkiego pisania dla id:"
                        +courseDto.getIdFastWritingLesson()));
        if(courseRepository.existsByUserAndAndFastWritingLesson(user,lesson)){
            throw new AlreadyExistsException("Wynik lekcji instnieje już w bazie dla id lekcji: "+ lesson.getIdFastWritingLesson());
        }
        FastWritingCourse course = FastWritingCourseMapper.INSTANCE.fromDto(courseDto);
        course.setUser(user);
        course.setFastWritingLesson(lesson);
        course.setNumberOfAttempts(1);
        courseRepository.save(course);
    }

    public void updateNewCourse(FastWritingCourseDto courseDto){
        User user = authenticationUtils.getUserFromAuthentication();
        FastWritingCourse course = courseRepository.findByIdFastWritingCourse(courseDto.getIdFastWritingCourse())
                .orElseThrow(()->new ResourceNotFoundException("Nie odnaleziono wyniku kursu dla id: "
                        +courseDto.getIdFastWritingCourse()));
        if(!course.getUser().equals(user)){
            throw new AccessDeniedException("Brak uprawnień do zmiany wyniku lekcji szybkiego pisania dla id : "
                    +course.getIdFastWritingCourse());
        }
        course.setScore(courseDto.getScore());
        course.setTime(courseDto.getTime());
        course.setStartTime(FastWritingCourseMapper.INSTANCE.fromLocalDateTime(courseDto.getStartTime()));
        incrementNumberOfAttempts(course);
        courseRepository.save(course);
    }

    private FastWritingCourse incrementNumberOfAttempts(FastWritingCourse course){
        int numberOfAttempts = course.getNumberOfAttempts();
        course.setNumberOfAttempts(numberOfAttempts+1);
        return course;
    }

    public List<FastWritingTextDto> getAllTexts(){
        List<FastWritingText> texts = textRepository.findAll();
        return FastWritingTextMapper.INSTANCE.toDto(texts);
    }

    public FastWritingTextDto getTextById(Long id){
        FastWritingText text = textRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie znaleziono tekstu dla id: "+id)
        );
        return FastWritingTextMapper.INSTANCE.toDto(text);
    }

    public FastWritingTextDto drawText(){
        List<FastWritingText> textsPool = textRepository.findAll();
        Random random = new Random();
        FastWritingText text = textsPool.get(random.nextInt(textsPool.size()));
        return FastWritingTextMapper.INSTANCE.toDto(text);
    }

    public FastWritingTextDto drawTextByLevel(Level level){
        List<FastWritingText> textPool = textRepository.findAllByLevel(level);
        Random random = new Random();
        FastWritingText text = textPool.get(random.nextInt(textPool.size()));
        return FastWritingTextMapper.INSTANCE.toDto(text);
    }

    public void createNewTest(FastWritingTestDto testDto){
        User user = authenticationUtils.getUserFromAuthentication();
        FastWritingText text = textRepository.findById(testDto.getIdText()).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono tekstu dla id: "+testDto.getIdText())
        );
        FastWritingTest test = FastWritingTestMapper.INSTANCE.fromDto(testDto);
        test.setText(text);
        test.setUser(user);
        testRepository.save(test);
        log.info("Zapisano nowy wynik testu na zapamiętywanie o id: "+test.getIdFastWritingTest());
    }

    public List<FastWritingTestDto> getAllUserTests(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<FastWritingTest> tests = testRepository.findAllByUser(user);
        return FastWritingTestMapper.INSTANCE.toDto(tests);
    }

    public FastWritingTestDto getUserTestById(Long id){
        User user = authenticationUtils.getUserFromAuthentication();
        FastWritingTest test = testRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono testu na szybkie pisanie dla id: "+id)
        );
        return FastWritingTestMapper.INSTANCE.toDto(test);
    }
}
