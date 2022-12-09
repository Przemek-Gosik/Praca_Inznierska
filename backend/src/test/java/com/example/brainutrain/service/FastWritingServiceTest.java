package com.example.brainutrain.service;

import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.RoleName;
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
import com.example.brainutrain.model.FastWritingCourse;
import com.example.brainutrain.model.FastWritingLesson;
import com.example.brainutrain.model.FastWritingModule;
import com.example.brainutrain.model.FastWritingTest;
import com.example.brainutrain.model.FastWritingText;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.FastWritingCourseRepository;
import com.example.brainutrain.repository.FastWritingLessonRepository;
import com.example.brainutrain.repository.FastWritingModuleRepository;
import com.example.brainutrain.repository.FastWritingTestRepository;
import com.example.brainutrain.repository.FastWritingTextRepository;
import com.example.brainutrain.utils.StringGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FastWritingServiceTest {

    @Mock
    private FastWritingModuleRepository moduleRepository;

    @Mock
    private FastWritingLessonRepository lessonRepository;

    @Mock
    private FastWritingCourseRepository courseRepository;

    @Mock
    private AuthenticationUtils utils;

    @Mock
    private FastWritingTextRepository textRepository;

    @Mock
    private FastWritingTestRepository testRepository;

    @Mock
    private StringGenerator generator;

    @InjectMocks
    private FastWritingService fastWritingService;

    private User user;
    private Role role;
    private Set<Role> roles = new HashSet<>();
    private FastWritingLesson lesson1;
    private FastWritingLesson lesson2;
    private List<FastWritingLesson> lessons = new ArrayList<>();
    private FastWritingCourse course1;
    private FastWritingCourseDto courseDto1;
    private FastWritingCourse course2;
    private List<FastWritingCourse> courses = new ArrayList<>();
    private FastWritingModule module;
    private List<FastWritingModule> modules = new ArrayList<>();

    private FastWritingTest test1;
    private FastWritingTest test2;
    private FastWritingText text1;
    private FastWritingText text2;
    private Timestamp timestamp;

    @BeforeEach
    public void init(){
        role = new Role(1L, RoleName.USER);
        roles.add(role);
        user = new User(1L,"login","login@email.com","password",true,true,roles);
        timestamp = new Timestamp(System.currentTimeMillis());
        module = new FastWritingModule(1L,1,"Pełne dłonie");
        modules.add(module);
        lesson1 = new FastWritingLesson(1L,1,"Lewa ręka",
                "aAqQwW",module);
        course1 = new FastWritingCourse(1L,0.8,timestamp, (float) 65.23,1,lesson1,user);
        courseDto1 = new FastWritingCourseDto(course1.getIdFastWritingCourse(),course1.getScore(),
                course1.getStartTime().toLocalDateTime(),course1.getTime(),course1.getNumberOfAttempts(),
                lesson1.getIdFastWritingLesson());
        lesson2 = new FastWritingLesson(2L,2,"Prawa ręka","pPoOlL",module);
        course2 = new FastWritingCourse(2L,0.4,timestamp,(float) 55.05,1,lesson2,user);
        courses.add(course1);
        courses.add(course2);
        lessons.add(lesson1);
        lessons.add(lesson2);
        text1 = new FastWritingText(1L,"Kopciuszek","Dawno dawno temu....", Level.EASY);
        text2 = new FastWritingText(2L,"Czerwony kapturek","Jeszcze dawniej temu...",Level.MEDIUM);
        test1 = new FastWritingTest(1L,"Dawno dawwwno ttttm",20.5,timestamp, (float) 154.04,text1,user);
        test2 = new FastWritingTest(2L,"tttttt",0.3,timestamp, (float) 10.04,text1,user);

    }

    @Test
    void getAllModules_Exists_GetModuleDto(){

        //when
        when(moduleRepository.findAll()).thenReturn(modules);
        when(lessonRepository.findAllByModuleAndOrderByNumber(module)).thenReturn(lessons);


        List<FastWritingModuleDto> moduleDtos = fastWritingService.getAllModules();

        //Then
        FastWritingModuleDto moduleDto = moduleDtos.get(0);
        assertAll(()->assertFalse(moduleDtos.isEmpty()),
                ()->assertEquals(modules.size(),moduleDtos.size()),
                ()->assertEquals(module.getName(),moduleDtos.get(0).getName()),
                ()->assertEquals(lessons.size(),moduleDto.getLessons().size()));
    }

    @Test
    void getAllUserModules_GivenAuthenticatedUser_GetModuleUserResponse(){

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(moduleRepository.findAll()).thenReturn(modules);
        when(courseRepository.findAllByUser(user)).thenReturn(courses);

        //Before
        FastWritingLessonUserResponse userResponse1 = new FastWritingLessonUserResponse(
                lesson1.getIdFastWritingLesson(),
                lesson1.getNumber(),
                lesson1.getName(),
                lesson1.getGeneratedCharacters(),
                course1.getIdFastWritingCourse(),
                course1.getScore()
        );
        FastWritingLessonUserResponse userResponse2 = new FastWritingLessonUserResponse(
                lesson2.getIdFastWritingLesson(),
                lesson2.getNumber(),
                lesson2.getName(),
                lesson2.getGeneratedCharacters(),
                course2.getIdFastWritingCourse(),
                course2.getScore()
        );
        List<FastWritingLessonUserResponse> userResponseList = new ArrayList<>();
        userResponseList.add(userResponse1);
        userResponseList.add(userResponse2);

        FastWritingModuleUserResponse moduleUserResponse = new FastWritingModuleUserResponse(
                module.getIdFastWritingModule(),
                module.getNumber(),
                module.getName(),
                userResponseList
        );

        List<FastWritingModuleUserResponse> responses = fastWritingService.getAllUserModules();

        //Then
        assertTrue(responses.equals(moduleUserResponse));
    }

    @Test
    void getCourseById_GivenValidId_GetCourseDto(){

        Long idCourse = 1L;

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(courseRepository.findByIdFastWritingCourse(idCourse)).thenReturn(Optional.of(course1));

        FastWritingCourseDto courseDto = fastWritingService.getCourseById(idCourse);

        assertAll(
                ()->assertEquals(idCourse,courseDto.getIdFastWritingCourse()),
                ()->assertEquals(course1.getNumberOfAttempts(),courseDto.getNumberOfAttempts())
        );
    }

    @Test
    void getCourseById_GivenWrongId_ResourceNotFoundExceptionThrown(){
        Long idCourse = 3L;

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(courseRepository.findByIdFastWritingCourse(idCourse)).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class,()->fastWritingService.getCourseById(idCourse));
    }

    @Test
    void getCourseById_GivenUserWithoutPermissions_AccessDeniedExceptionThrown(){
        User user2 = new User(2L,"login2","pass2@email.com","pass2",true,true,roles);
        Long idCourse = 1L;

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user2);
        when(courseRepository.findByIdFastWritingCourse(idCourse)).thenReturn(Optional.of(course1));

        assertThrows(AccessDeniedException.class,()->fastWritingService.getCourseById(idCourse));
    }

    @Test
    void saveNewCourse_GivenValidData_ExistsInRepository(){

        courseDto1.setIdFastWritingCourse(null);
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(courseRepository.save(any(FastWritingCourse.class))).thenReturn(course1);
        when(lessonRepository.findById(courseDto1.getIdFastWritingLesson())).thenReturn(Optional.of(lesson1));
        when(courseRepository.existsByUserAndAndFastWritingLesson(user,lesson1)).thenReturn(false);

        fastWritingService.saveNewCourse(courseDto1);

        //Then
        verify(courseRepository,times(1)).save(any(FastWritingCourse.class));
    }

    @Test
    void saveNewCourse_GivenLessonId_ThrowAlreadyExistsException(){

        courseDto1.setIdFastWritingCourse(null);
        FastWritingCourse course = course1;
        course.setIdFastWritingCourse(null);
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(lessonRepository.findById(courseDto1.getIdFastWritingLesson())).thenReturn(Optional.of(lesson1));
        when(courseRepository.existsByUserAndAndFastWritingLesson(user,lesson1)).thenReturn(true);

        assertThrows(AlreadyExistsException.class,()->fastWritingService.saveNewCourse(courseDto1));
    }

    @Test
    void updateNewCourse_GivenCourseId_ModifyExistingObject(){
        courseDto1.setScore(0.91);
        int numberOfAttempts = course1.getNumberOfAttempts();
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(courseRepository.findByIdFastWritingCourse(courseDto1.getIdFastWritingLesson())).thenReturn(Optional.of(course1));


        fastWritingService.updateNewCourse(courseDto1);

        assertEquals(numberOfAttempts+1,course1.getNumberOfAttempts());
        assertEquals(courseDto1.getScore(),course1.getScore());
    }

    @Test
    void updateNewCourse_GivenWrongUser_AccessDeniedExceptionThrown(){

        User user2 = new User(2L,"login2","pass2@email.com","pass2",true,true,roles);

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user2);
        when(courseRepository.findByIdFastWritingCourse(courseDto1.getIdFastWritingLesson())).thenReturn(Optional.of(course1));

        assertThrows(AccessDeniedException.class,
                ()->fastWritingService.updateNewCourse(courseDto1));
    }

    @Test
    void updateNewCourse_GivenWrongCourseId_ThrowResourceNotFoundException(){

        courseDto1.setScore(0.91);
        FastWritingCourse course = new FastWritingCourse(
                3L,
                courseDto1.getScore(),
                Timestamp.valueOf(courseDto1.getStartTime()),
                courseDto1.getTime(),
                courseDto1.getNumberOfAttempts(),
                lesson1,user
        );
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(courseRepository.findByIdFastWritingCourse(courseDto1.getIdFastWritingLesson())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class,()->fastWritingService.updateNewCourse(courseDto1));
    }

    @Test
    void getAllTexts_Exists_GetListOfTextsDto(){

        List<FastWritingText> fastWritingTextList = new ArrayList<>();
        fastWritingTextList.add(text1);
        fastWritingTextList.add(text2);

        //When
        when(textRepository.findAll()).thenReturn(fastWritingTextList);

        List<FastWritingTextDto> results = fastWritingService.getAllTexts();


        assertEquals(fastWritingTextList.size(),results.size());
    }

    @Test
    void getTextById_GivenValidId_GetTextDto(){

        Long idText = text1.getIdFastWritingText();
        //When
        when(textRepository.findById(idText)).thenReturn(Optional.of(text1));

        FastWritingTextDto result = fastWritingService.getTextById(idText);

        assertEquals(idText,result.getIdFastWritingText());
    }

    @Test
    void getTextById_GivenInvalidId_ThrowResourceNotFoundException(){

        Long idText = 3L;
        //When
        when(textRepository.findById(idText)).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class,()->fastWritingService.getTextById(idText));
    }

    @Test
    void drawText_Exists_GetTextDto(){

        List<FastWritingText> textList = new ArrayList<>();
        textList.add(text1);
        textList.add(text2);

        //When
        when(textRepository.findAll()).thenReturn(textList);

        FastWritingTextDto result = fastWritingService.drawText();

        assertNotNull(result.getText());
    }

    @Test
    void drawTextByLevel_GivenLevelEasy_GetTextWithGivenLevel(){
        FastWritingText text3 = new FastWritingText(3L,"Shrek","Somebody once told me ..",Level.EASY);
        List<FastWritingText> textList = new ArrayList<>();
        textList.add(text1);
        textList.add(text3);

        //When
        when(textRepository.findAllByLevel(Level.EASY)).thenReturn(textList);

        FastWritingTextDto result = fastWritingService.drawTextByLevel(Level.EASY);

        assertTrue(result.getText().equals(text1.getText()) || result.getText().equals(text3.getText()));

        assertEquals(Level.EASY,result.getLevel());
    }

    @Test
    void createNewTest_GivenValidData_ExistsInRepository(){

        FastWritingTestDto testDto1 = new FastWritingTestDto(null,test1.getTypedText(),test1.getScore(),
                test1.getStartTime().toLocalDateTime(),test1.getTime(),test1.getIdFastWritingTest());
        FastWritingTest test = test1;
        test.setIdFastWritingTest(null);

        //When
        when(testRepository.save(test)).thenReturn(test1);
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(textRepository.findById(testDto1.getIdText())).thenReturn(Optional.of(text1));

        fastWritingService.createNewTest(testDto1);

        verify(testRepository,times(1)).save(test);
    }

    @Test
    void createNewTest_GivenInValidTextId_ThrowResourceNotFound(){

        FastWritingTestDto testDto1 = new FastWritingTestDto(null,test1.getTypedText(),test1.getScore(),
                test1.getStartTime().toLocalDateTime(),test1.getTime(),3L);
        FastWritingTest test = test1;
        test.setIdFastWritingTest(null);

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(textRepository.findById(testDto1.getIdText())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class,()->fastWritingService.createNewTest(testDto1));
    }

    @Test
    void getAllUserTests_GivenValidUser_GetListOfTestsDto(){

        List<FastWritingTest> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(testRepository.findAllByUser(user)).thenReturn(testList);

        List<FastWritingTestDto> resultList = fastWritingService.getAllUserTests();

        assertEquals(testList.size(),resultList.size());
        assertEquals(testList.get(0).getIdFastWritingTest(),resultList.get(0).getIdFastWritingTest());
        assertEquals(testList.get(0).getIdFastWritingTest(),resultList.get(0).getIdFastWritingTest());
    }

    @Test
    void getUserTestById_GivenValidId_GetTestDto(){

        Long idTest = 1L;
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(testRepository.findById(idTest)).thenReturn(Optional.of(test1));

        FastWritingTestDto result = fastWritingService.getUserTestById(idTest);

        assertEquals(test1.getIdFastWritingTest(),result.getIdFastWritingTest());
    }
}