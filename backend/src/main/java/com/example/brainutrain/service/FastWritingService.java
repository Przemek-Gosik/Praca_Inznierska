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

import java.util.List;
import java.util.Random;

/**
 * Service for fast writing services
 */

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

    /**
     * Method to get all modules with lessons
     *
     * @return is List of FastWritingModuleDto
     */
    public List<FastWritingModuleDto> getAllModules(){
        List<FastWritingModule> modules = moduleRepository.findAll();
        List<FastWritingModuleDto> moduleDtoList = FastWritingModuleMapper.INSTANCE.toDto(modules);
        for(FastWritingModuleDto moduleDto : moduleDtoList){
            List<FastWritingLesson> writingLessons = lessonRepository.findAllByModuleName(moduleDto.getName());
            List<FastWritingLessonDto> writingLessonDtoList = FastWritingLessonMapper.INSTANCE.toDto(writingLessons);
            moduleDto.setLessons(writingLessonDtoList);
        }
        return moduleDtoList;
    }

    /**
     * Method to get all modules with lesson and user scores for every lesson
     *
     * @return is List of FastWritingModuleUserResponse
     */
    public List<FastWritingModuleUserResponse> getAllUserModules(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<FastWritingModule> modules = moduleRepository.findAll();
        List<FastWritingModuleUserResponse> moduleUserResponseList = FastWritingModuleMapper.INSTANCE.toUserResponse(modules);
        for(FastWritingModuleUserResponse moduleUserResponse : moduleUserResponseList){
            List<FastWritingLesson> writingLessons = lessonRepository.findAllByModuleName(moduleUserResponse.getName());
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

    /**
     * Method to get lesson details
     *
     * @param id is Long
     * @return is FastWritingLessonDto
     */
    public FastWritingLessonDto getLessonById(Long id){
        FastWritingLesson lesson = lessonRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono lekcji szybkiego pisania dla id: "+id)
        );
        FastWritingLessonDto lessonDto = FastWritingLessonMapper.INSTANCE.toDto(lesson);
        lessonDto.setText(stringGenerator.generateTexts(lessonDto.getGeneratedCharacters()));
        return lessonDto;
    }

    /**
     * Method to get course details
     *
     * @param id is Long
     * @return is FastWritingCourseDto
     */
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

    /**
     * Method to create new result of course
     *
     * @param courseDto is FastWritingCourseDto
     */
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

    /**
     * Method to update result for user
     *
     * @param courseDto is FastWritingCourseDto
     */
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

    /**
     * Method to increment number of attempts to result
     *
     * @param course is FastWritingCourse
     * @return is FastWritingCourse
     */
    private FastWritingCourse incrementNumberOfAttempts(FastWritingCourse course){
        int numberOfAttempts = course.getNumberOfAttempts();
        course.setNumberOfAttempts(numberOfAttempts+1);
        return course;
    }

    /**
     * Method to get all text for fast writing
     *
     * @return is List of FastWritingTextDto
     */
    public List<FastWritingTextDto> getAllTexts(){
        List<FastWritingText> texts = textRepository.findAll();
        return FastWritingTextMapper.INSTANCE.toDto(texts);
    }

    /**
     * Method to get text details
     *
     * @param id is Long
     * @return is FastWritingTextDto
     */
    public FastWritingTextDto getTextById(Long id){
        FastWritingText text = textRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie znaleziono tekstu dla id: "+id)
        );
        return FastWritingTextMapper.INSTANCE.toDto(text);
    }

    /**
     * Method to get random text details
     *
     * @return is FastWritingTextDto
     */
    public FastWritingTextDto drawText(){
        List<FastWritingText> textsPool = textRepository.findAll();
        Random random = new Random();
        FastWritingText text = textsPool.get(random.nextInt(textsPool.size()));
        return FastWritingTextMapper.INSTANCE.toDto(text);
    }

    /**
     * Method to get random text from one level
     *
     * @param level is Level
     * @return is FastWritingTextDto
     */
    public FastWritingTextDto drawTextByLevel(Level level){
        List<FastWritingText> textPool = textRepository.findAllByLevel(level);
        Random random = new Random();
        FastWritingText text = textPool.get(random.nextInt(textPool.size()));
        return FastWritingTextMapper.INSTANCE.toDto(text);
    }

    /**
     * Method to add new test result
     *
     * @param testDto is FastWritingTestDto
     */
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

    /**
     * Method to get user results of test
     *
     * @return is List of FastWritingTestDto
     */
    public List<FastWritingTestDto> getAllUserTests(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<FastWritingTest> tests = testRepository.findAllByUser(user);
        return FastWritingTestMapper.INSTANCE.toDto(tests);
    }

    /**
     * Method to get test result details
     *
     * @param id is Long
     * @return is FastWritingTestDto
     */
    public FastWritingTestDto getUserTestById(Long id){
        User user = authenticationUtils.getUserFromAuthentication();
        FastWritingTest test = testRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono testu na szybkie pisanie dla id: "+id)
        );
        if(!test.getUser().equals(user)){
            throw new AccessDeniedException("Brak uprawnień do wyświetlenia testu o id: "+test.getIdFastWritingTest());
        }
        return FastWritingTestMapper.INSTANCE.toDto(test);
    }
}
