package com.example.brainutrain.service;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.dto.WritingLessonDto;
import com.example.brainutrain.dto.WritingLessonResultDto;
import com.example.brainutrain.dto.WritingModuleDto;
import com.example.brainutrain.dto.WritingTextDto;
import com.example.brainutrain.dto.WritingTextResultDto;
import com.example.brainutrain.dto.response.WritingLessonUserResponse;
import com.example.brainutrain.dto.response.WritingModuleUserResponse;
import com.example.brainutrain.exception.AlreadyExistsException;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.mapper.WritingLessonMapper;
import com.example.brainutrain.mapper.WritingLessonResultMapper;
import com.example.brainutrain.mapper.WritingModuleMapper;
import com.example.brainutrain.mapper.WritingTextMapper;
import com.example.brainutrain.mapper.WritingTextResultMapper;
import com.example.brainutrain.mapper.enum_mapper.LevelMapper;
import com.example.brainutrain.model.WritingLessonResult;
import com.example.brainutrain.model.WritingLesson;
import com.example.brainutrain.model.WritingModule;
import com.example.brainutrain.model.WritingText;
import com.example.brainutrain.model.WritingTextResult;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.WritingLessonResultRepository;
import com.example.brainutrain.repository.WritingLessonRepository;
import com.example.brainutrain.repository.WritingModuleRepository;
import com.example.brainutrain.repository.WritingTextResultRepository;
import com.example.brainutrain.repository.WritingTextRepository;
import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.utils.StringGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Service for fast writing services
 */

@AllArgsConstructor
@Service
@Slf4j
public class WritingService {

    private final WritingModuleRepository moduleRepository;

    private final WritingLessonRepository lessonRepository;

    private final WritingLessonResultRepository lessonResultRepository;

    private final WritingTextResultRepository writingTextResultRepository;

    private final WritingTextRepository textRepository;

    private final AuthenticationUtils authenticationUtils;

    private final StringGenerator stringGenerator;

    /**
     * Method to get all modules with lessons
     *
     * @return is List of WritingModuleDto
     */
    public List<WritingModuleDto> getAllModules(){
        List<WritingModule> modules = moduleRepository.findAll();
        List<WritingModuleDto> moduleDtoList = WritingModuleMapper.INSTANCE.toDto(modules);
        for(WritingModuleDto moduleDto : moduleDtoList){
            List<WritingLesson> writingLessons = lessonRepository.findAllByModuleName(moduleDto.getName());
            List<WritingLessonDto> writingLessonDtoList = WritingLessonMapper.INSTANCE.toDto(writingLessons);
            moduleDto.setLessons(writingLessonDtoList);
        }
        return moduleDtoList;
    }

    /**
     * Method to get all modules with lesson and user scores for every lesson
     *
     * @return is List of WritingModuleUserResponse
     */
    public List<WritingModuleUserResponse> getAllUserModules(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<WritingModule> modules = moduleRepository.findAll();
        List<WritingModuleUserResponse> moduleUserResponseList = WritingModuleMapper.INSTANCE.toUserResponse(modules);
        for(WritingModuleUserResponse moduleUserResponse : moduleUserResponseList){
            List<WritingLesson> writingLessons = lessonRepository.findAllByModuleName(moduleUserResponse.getName());
            List<WritingLessonUserResponse> lessonUserResponseList = writingLessons
                    .stream().map(
                            writingLesson -> {
                                WritingLessonUserResponse lessonUserResponse =
                                        WritingLessonMapper.INSTANCE.toUserResponse(writingLesson);
                                WritingLessonResult course = lessonResultRepository.findByUserAndWritingLesson(user,writingLesson).orElse(
                                        new WritingLessonResult(null,0.0)
                                );
                                lessonUserResponse.setIdWritingLessonResult(course.getIdWritingLessonResult());
                                lessonUserResponse.setScore(course.getScore());
                                return lessonUserResponse;
                            }).toList();
            moduleUserResponse.setWritingLessons(lessonUserResponseList);
        }
        return moduleUserResponseList;
    }

    /**
     * Method to get lesson details
     *
     * @param id is Long
     * @return is WritingLessonDto
     */
    public WritingLessonDto getLessonById(Long id){
        WritingLesson lesson = lessonRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono lekcji szybkiego pisania dla id: "+id)
        );
        WritingLessonDto lessonDto = WritingLessonMapper.INSTANCE.toDto(lesson);
        lessonDto.setText(stringGenerator.generateTexts(lessonDto.getGeneratedCharacters()));
        return lessonDto;
    }

    /**
     * Method to get course details
     *
     * @param id is Long
     * @return is WritingLessonResultDto
     */
    public WritingLessonResultDto getLessonResultById(Long id){
        User user = authenticationUtils.getUserFromAuthentication();
        WritingLessonResult course = lessonResultRepository.findByIdWritingLessonResult(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono wyniku lekcji szybkiego pisania dla id: "+id)
        );
        if(!course.getUser().equals(user)){
            throw new AccessDeniedException("Brak dostepu do kursu o id: "+id);
        }
        return WritingLessonResultMapper.INSTANCE.toDto(course);
    }

    /**
     * Method to create new result of course
     *
     * @param lessonResultDto is WritingLessonResultDto
     */
    public void saveNewLessonResult(WritingLessonResultDto lessonResultDto){
        User user = authenticationUtils.getUserFromAuthentication();
        WritingLesson lesson = lessonRepository.findById(lessonResultDto.getIdWritingLesson()).
                orElseThrow(()->new ResourceNotFoundException("Nie odnaleziono lekcji szybkiego pisania dla id:"
                        +lessonResultDto.getIdWritingLesson()));
        if(lessonResultRepository.existsByUserAndWritingLesson(user,lesson)){
            throw new AlreadyExistsException("Wynik lekcji instnieje już w bazie dla id lekcji: "+ lesson.getIdWritingLesson());
        }
        WritingLessonResult course = WritingLessonResultMapper.INSTANCE.fromDto(lessonResultDto);
        course.setUser(user);
        course.setWritingLesson(lesson);
        course.setNumberOfAttempts(1);
        lessonResultRepository.save(course);
    }

    /**
     * Method to update result for user
     *
     * @param lessonResultDto is FastWritingCourseDto
     */
    public void updateLessonResult(WritingLessonResultDto lessonResultDto){
        User user = authenticationUtils.getUserFromAuthentication();
        WritingLessonResult lessonResult = lessonResultRepository.findByIdWritingLessonResult(lessonResultDto.getIdWritingLessonResult())
                .orElseThrow(()->new ResourceNotFoundException("Nie odnaleziono wyniku kursu dla id: "
                        +lessonResultDto.getIdWritingLessonResult()));
        if(!lessonResult.getUser().equals(user)){
            throw new AccessDeniedException("Brak uprawnień do zmiany wyniku lekcji szybkiego pisania dla id : "
                    +lessonResult.getIdWritingLessonResult());
        }
        lessonResult.setScore(lessonResultDto.getScore());
        lessonResult.setTime(lessonResultDto.getTime());
        lessonResult.setNumberOfTypedLetters(lessonResultDto.getNumberOfTypedLetters());
        lessonResult.setStartTime(WritingLessonResultMapper.INSTANCE.fromLocalDateTime(lessonResultDto.getStartTime()));
        incrementNumberOfAttempts(lessonResult);
        lessonResultRepository.save(lessonResult);
    }

    /**
     * Method to increment number of attempts to result
     *
     * @param lessonResult is WritingLessonResult
     * @return is WritingLessonResult
     */
    private WritingLessonResult incrementNumberOfAttempts(WritingLessonResult lessonResult){
        int numberOfAttempts = lessonResult.getNumberOfAttempts();
        lessonResult.setNumberOfAttempts(numberOfAttempts+1);
        return lessonResult;
    }

    /**
     * Method to get all text for fast writing
     *
     * @return is List of WritingTextDto
     */
    public List<WritingTextDto> getAllTexts(){
        List<WritingText> texts = textRepository.findAll();
        return WritingTextMapper.INSTANCE.toDto(texts);
    }

    /**
     *
     * @param level1 is String
     * @return is List of WritingTextDto
     */
    public List<WritingTextDto> getAllTextsByLevel(String level1){
        Level level = LevelMapper.getLevelFromString(level1);
        List <WritingText> texts = textRepository.findAllByLevel(level);
        return WritingTextMapper.INSTANCE.toDto(texts);
    }

    /**
     * Method to get text details
     *
     * @param id is Long
     * @return is WritingTextDto
     */
    public WritingTextDto getTextById(Long id){
        WritingText text = textRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie znaleziono tekstu dla id: "+id)
        );
        return WritingTextMapper.INSTANCE.toDto(text);
    }

    /**
     * Method to get random text details
     *
     * @return is WritingTextDto
     */
    public WritingTextDto drawText(){
        List<WritingText> textsPool = textRepository.findAll();
        Random random = new Random();
        WritingText text = textsPool.get(random.nextInt(textsPool.size()));
        return WritingTextMapper.INSTANCE.toDto(text);
    }

    /**
     * Method to get random text from one level
     *
     * @param level is Level
     * @return is WritingTextDto
     */
    public WritingTextDto drawTextByLevel(Level level){
        List<WritingText> textPool = textRepository.findAllByLevel(level);
        Random random = new Random();
        WritingText text = textPool.get(random.nextInt(textPool.size()));
        return WritingTextMapper.INSTANCE.toDto(text);
    }

    /**
     * Method to add new test result
     *
     * @param textResultDto is WritingTextResultDto
     */
    public void createNewTextResult(WritingTextResultDto textResultDto){
        User user = authenticationUtils.getUserFromAuthentication();
        WritingText text = textRepository.findById(textResultDto.getIdText()).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono tekstu dla id: "+textResultDto.getIdText())
        );
        WritingTextResult test = WritingTextResultMapper.INSTANCE.fromDto(textResultDto);
        test.setText(text);
        test.setUser(user);
        writingTextResultRepository.save(test);
        log.info("Zapisano nowy wynik testu na zapamiętywanie o id: "+test.getIdWritingTextResult());
    }

    /**
     * Method to get user results of test
     *
     * @return is List of WritingTextResultDto
     */
    public List<WritingTextResultDto> getAllUserTextResults(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<WritingTextResult> tests = writingTextResultRepository.findAllByUser(user);
        return WritingTextResultMapper.INSTANCE.toDto(tests);
    }

    /**
     * Method to get test result details
     *
     * @param id is Long
     * @return is WritingTextResultDto
     */
    public WritingTextResultDto getUserTextResultById(Long id){
        User user = authenticationUtils.getUserFromAuthentication();
        WritingTextResult textResult = writingTextResultRepository.findByIdWritingTextResult(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono testu na szybkie pisanie dla id: "+id)
        );
        if(!textResult.getUser().equals(user)){
            throw new AccessDeniedException("Brak uprawnień do wyświetlenia testu o id: "+textResult.getIdWritingTextResult());
        }
        return WritingTextResultMapper.INSTANCE.toDto(textResult);
    }
}
