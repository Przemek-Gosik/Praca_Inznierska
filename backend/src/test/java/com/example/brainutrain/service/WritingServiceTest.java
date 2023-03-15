package com.example.brainutrain.service;

import com.example.brainutrain.dto.WritingLessonResultDto;
import com.example.brainutrain.dto.WritingModuleDto;
import com.example.brainutrain.dto.WritingTextResultDto;
import com.example.brainutrain.model.WritingLesson;
import com.example.brainutrain.model.WritingLessonResult;
import com.example.brainutrain.model.WritingModule;
import com.example.brainutrain.model.WritingText;
import com.example.brainutrain.model.WritingTextResult;
import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.dto.WritingLessonDto;
import com.example.brainutrain.dto.WritingTextDto;
import com.example.brainutrain.dto.response.WritingModuleUserResponse;
import com.example.brainutrain.exception.AlreadyExistsException;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.WritingLessonResultRepository;
import com.example.brainutrain.repository.WritingLessonRepository;
import com.example.brainutrain.repository.WritingModuleRepository;
import com.example.brainutrain.repository.WritingTextResultRepository;
import com.example.brainutrain.repository.WritingTextRepository;
import com.example.brainutrain.utils.StringGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class WritingServiceTest {

    @Mock
    private WritingModuleRepository moduleRepository;

    @Mock
    private WritingLessonRepository lessonRepository;

    @Mock
    private WritingLessonResultRepository lessonResultRepository;

    @Mock
    private AuthenticationUtils utils;

    @Mock
    private WritingTextRepository textRepository;

    @Mock
    private WritingTextResultRepository testRepository;

    @Mock
    private StringGenerator generator;

    @InjectMocks
    private WritingService writingService;

    private User user;
    private Role role;
    private Set<Role> roles = new HashSet<>();
    private WritingLesson lesson1;
    private WritingLesson lesson2;
    private List<WritingLesson> lessons = new ArrayList<>();
    private WritingLessonResult lessonResult1;
    private WritingLessonResultDto lessonResultDto1;
    private WritingLessonResult lessonResult2;
    private List<WritingLessonResult> lessonResults = new ArrayList<>();
    private WritingModule module;
    private List<WritingModule> modules = new ArrayList<>();

    private WritingTextResult textResult1;
    private WritingTextResult textResult2;
    private WritingText text1;
    private WritingText text2;
    private Timestamp timestamp;

    @BeforeEach
    public void init(){
        role = new Role(1L, RoleName.USER);
        roles.add(role);
        user = new User(1L,"login","login@email.com","password",true,true,roles);
        timestamp = new Timestamp(System.currentTimeMillis());
        module = new WritingModule(1L,1,"Pełne dłonie");
        modules.add(module);
        lesson1 = new WritingLesson(1L,1,"Lewa ręka",
                "aAqQwW",module);
        lessonResult1 = new WritingLessonResult(1L,0.8,timestamp, (float) 65.23,1,13,lesson1,user);
        lessonResultDto1 = new WritingLessonResultDto(lessonResult1.getIdWritingLessonResult(), lessonResult1.getScore(),
                lessonResult1.getStartTime().toLocalDateTime(), lessonResult1.getTime(), lessonResult1.getNumberOfAttempts(),
                lessonResult1.getNumberOfTypedLetters(),lesson1.getIdWritingLesson());
        lesson2 = new WritingLesson(2L,2,"Prawa ręka","pPoOlL",module);
        lessonResult2 = new WritingLessonResult(2L,0.4,timestamp,(float) 55.05,1,13,lesson2,user);
        lessonResults.add(lessonResult1);
        lessonResults.add(lessonResult2);
        lessons.add(lesson1);
        lessons.add(lesson2);
        text1 = new WritingText(1L,"Kopciuszek","Dawno dawno temu....", Level.EASY);
        text2 = new WritingText(2L,"Czerwony kapturek","Jeszcze dawniej temu...",Level.MEDIUM);
        textResult1 = new WritingTextResult(1L,"Dawno dawwwno ttttm",20.5,timestamp, (float) 154.04,text1,user);
        textResult2 = new WritingTextResult(2L,"tttttt",0.3,timestamp, (float) 10.04,text1,user);

    }

    @Test
    void getAllModules_Exists_GetModuleDto(){

        //when
        when(moduleRepository.findAll()).thenReturn(modules);
        when(lessonRepository.findAllByModuleName(module.getName())).thenReturn(lessons);


        List<WritingModuleDto> moduleDtos = writingService.getAllModules();

        //Then
        WritingModuleDto moduleDto = moduleDtos.get(0);
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
        when(lessonRepository.findAllByModuleName(module.getName())).thenReturn(lessons);
        when(lessonResultRepository.findByUserAndWritingLesson(user,lesson1)).thenReturn(Optional.of(lessonResult1));
        when(lessonResultRepository.findByUserAndWritingLesson(user,lesson2)).thenReturn(Optional.ofNullable(null));

        List<WritingModuleUserResponse> responses = writingService.getAllUserModules();
        WritingModuleUserResponse response = responses.get(0);
        //Then
        assertAll(
                ()->assertEquals(lessons.size(),response.getLessons().size()),
                ()->assertEquals(lessons.get(0).getGeneratedCharacters(),response.getLessons().get(0).getGeneratedCharacters()),
                ()->assertEquals(lessonResult1.getScore(),response.getLessons().get(0).getScore()),
                ()->assertEquals(0.0,response.getLessons().get(1).getScore())
        );
    }

    @Test
    void getLessonById_GivenValidId_GetLessonDto(){
        Long idLesson = 1L;
        String[] texts = new String[10];
        for(int i =0;i<10;i++){
            texts[i]="tekst";
        }
        when(lessonRepository.findById(idLesson)).thenReturn(Optional.of(lesson1));
        when(generator.generateTexts(lesson1.getGeneratedCharacters())).thenReturn(texts);

        WritingLessonDto lessonDto = writingService.getLessonById(idLesson);

        assertEquals(texts,lessonDto.getText());
    }

    @Test
    void getLessonById_GivenInvalidId_ResourceNotFoundThrown(){
        Long idLesson = 1L;
        when(lessonRepository.findById(idLesson)).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class,()-> writingService.getLessonById(idLesson));
    }

    @Test
    void getCourseById_GivenValidId_GetCourseDto(){

        Long idResult = 1L;

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(lessonResultRepository.findByIdWritingLessonResult(idResult)).thenReturn(Optional.of(lessonResult1));
        WritingLessonResultDto lessonResultDto = writingService.getLessonResultById(idResult);

        assertAll(
                ()->assertEquals(idResult,lessonResultDto.getIdWritingLessonResult()),
                ()->assertEquals(lessonResult1.getNumberOfAttempts(),lessonResultDto.getNumberOfAttempts())
        );
    }

    @Test
    void getCourseById_GivenWrongId_ResourceNotFoundExceptionThrown(){
        Long idResult = 3L;

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(lessonResultRepository.findByIdWritingLessonResult(idResult)).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class,()-> writingService.getLessonResultById(idResult));
    }

    @Test
    void getCourseById_GivenUserWithoutPermissions_AccessDeniedExceptionThrown(){
        User user2 = new User(2L,"login2","pass2@email.com","pass2",true,true,roles);
        Long idResult = 1L;

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user2);
        when(lessonResultRepository.findByIdWritingLessonResult(idResult)).thenReturn(Optional.of(lessonResult1));

        assertThrows(AccessDeniedException.class,()-> writingService.getLessonResultById(idResult));
    }

    @Test
    void saveNewCourse_GivenValidData_ExistsInRepository(){

        lessonResultDto1.setIdWritingLessonResult(null);
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(lessonResultRepository.save(any(WritingLessonResult.class))).thenReturn(lessonResult1);
        when(lessonRepository.findById(lessonResultDto1.getIdWritingLesson())).thenReturn(Optional.of(lesson1));
        when(lessonResultRepository.existsByUserAndWritingLesson(user,lesson1)).thenReturn(false);

        writingService.saveNewLessonResult(lessonResultDto1);

        //Then
        verify(lessonResultRepository,times(1)).save(any(WritingLessonResult.class));
    }

    @Test
    void saveNewCourse_GivenLessonId_ThrowAlreadyExistsException(){

        lessonResultDto1.setIdWritingLessonResult(null);
        WritingLessonResult lessonResult = lessonResult1;
        lessonResult.setIdWritingLessonResult(null);
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(lessonRepository.findById(lessonResultDto1.getIdWritingLesson())).thenReturn(Optional.of(lesson1));
        when(lessonResultRepository.existsByUserAndWritingLesson(user,lesson1)).thenReturn(true);

        assertThrows(AlreadyExistsException.class,()-> writingService.saveNewLessonResult(lessonResultDto1));
    }

    @Test
    void updateNewCourse_GivenCourseId_ModifyExistingObject(){
        lessonResultDto1.setScore(0.91);
        int numberOfAttempts = lessonResult1.getNumberOfAttempts();
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(lessonResultRepository.findByIdWritingLessonResult(lessonResultDto1.getIdWritingLesson())).thenReturn(Optional.of(lessonResult1));


        writingService.updateLessonResult(lessonResultDto1);

        assertEquals(numberOfAttempts+1, lessonResult1.getNumberOfAttempts());
        assertEquals(lessonResultDto1.getScore(), lessonResult1.getScore());
    }

    @Test
    void updateNewCourse_GivenWrongUser_AccessDeniedExceptionThrown(){

        User user2 = new User(2L,"login2","pass2@email.com","pass2",true,true,roles);

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user2);
        when(lessonResultRepository.findByIdWritingLessonResult(lessonResultDto1.getIdWritingLesson())).thenReturn(Optional.of(lessonResult1));

        assertThrows(AccessDeniedException.class,
                ()-> writingService.updateLessonResult(lessonResultDto1));
    }

    @Test
    void updateNewCourse_GivenWrongCourseId_ThrowResourceNotFoundException(){

        lessonResultDto1.setScore(0.91);
        WritingLessonResult lessonResult = new WritingLessonResult(
                3L,
                lessonResultDto1.getScore(),
                Timestamp.valueOf(lessonResultDto1.getStartTime()),
                lessonResultDto1.getTime(),
                lessonResultDto1.getNumberOfAttempts(),
                lessonResultDto1.getNumberOfTypedLetters(),
                lesson1,user
        );
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(lessonResultRepository.findByIdWritingLessonResult(lessonResultDto1.getIdWritingLesson())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class,()-> writingService.updateLessonResult(lessonResultDto1));
    }

    @Test
    void getAllTexts_Exists_GetListOfTextsDto(){

        List<WritingText> writingTextList = new ArrayList<>();
        writingTextList.add(text1);
        writingTextList.add(text2);

        //When
        when(textRepository.findAll()).thenReturn(writingTextList);

        List<WritingTextDto> results = writingService.getAllTexts();


        assertEquals(writingTextList.size(),results.size());
    }

    @Test
    void getTextById_GivenValidId_GetTextDto(){

        Long idText = text1.getIdWritingText();
        //When
        when(textRepository.findById(idText)).thenReturn(Optional.of(text1));

        WritingTextDto result = writingService.getTextById(idText);

        assertEquals(idText,result.getIdWritingText());
    }

    @Test
    void getTextById_GivenInvalidId_ThrowResourceNotFoundException(){

        Long idText = 3L;
        //When
        when(textRepository.findById(idText)).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class,()-> writingService.getTextById(idText));
    }

    @Test
    void drawText_Exists_GetTextDto(){

        List<WritingText> textList = new ArrayList<>();
        textList.add(text1);
        textList.add(text2);

        //When
        when(textRepository.findAll()).thenReturn(textList);

        WritingTextDto result = writingService.drawText();

        assertNotNull(result.getText());
    }

    @Test
    void drawTextByLevel_GivenLevelEasy_GetTextWithGivenLevel(){
        WritingText text3 = new WritingText(3L,"Shrek","Somebody once told me ..",Level.EASY);
        List<WritingText> textList = new ArrayList<>();
        textList.add(text1);
        textList.add(text3);

        //When
        when(textRepository.findAllByLevel(Level.EASY)).thenReturn(textList);

        WritingTextDto result = writingService.drawTextByLevel(Level.EASY);

        assertTrue(result.getText().equals(text1.getText()) || result.getText().equals(text3.getText()));

        assertEquals(Level.EASY,result.getLevel());
    }

    @Test
    void createNewTest_GivenValidData_ExistsInRepository(){

        WritingTextResultDto testDto1 = new WritingTextResultDto(null, textResult1.getTypedText(), textResult1.getScore(),
                textResult1.getStartTime().toLocalDateTime(), textResult1.getTime(), textResult1.getIdWritingTextResult(),textResult1.getText().getTitle());
        WritingTextResult textResult = textResult1;
        textResult.setIdWritingTextResult(null);

        //When
        when(testRepository.save(textResult)).thenReturn(textResult);
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(textRepository.findById(testDto1.getIdText())).thenReturn(Optional.of(text1));

        writingService.createNewTextResult(testDto1);

        verify(testRepository,times(1)).save(textResult);
    }

    @Test
    void createNewTest_GivenInValidTextId_ThrowResourceNotFound(){

        WritingTextResultDto testDto1 = new WritingTextResultDto(null, textResult1.getTypedText(), textResult1.getScore(),
                textResult1.getStartTime().toLocalDateTime(), textResult1.getTime(),3L,textResult1.getText().getTitle());
        WritingTextResult textResult = textResult1;
        textResult.setIdWritingTextResult(null);

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(textRepository.findById(testDto1.getIdText())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class,()-> writingService.createNewTextResult(testDto1));
    }

    @Test
    void getAllUserTests_GivenValidUser_GetListOfTestsDto(){

        List<WritingTextResult> testList = new ArrayList<>();
        testList.add(textResult1);
        testList.add(textResult2);

        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(testRepository.findAllByUser(user)).thenReturn(testList);

        List<WritingTextResultDto> resultList = writingService.getAllUserTextResults();

        assertEquals(testList.size(),resultList.size());
        assertEquals(testList.get(0).getIdWritingTextResult(),resultList.get(0).getIdWritingTextResult());
        assertEquals(testList.get(0).getIdWritingTextResult(),resultList.get(0).getIdWritingTextResult());
    }

    @Test
    void getUserTestById_GivenValidId_GetTestDto(){

        Long idTest = 1L;
        //When
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(testRepository.findById(idTest)).thenReturn(Optional.of(textResult1));

        WritingTextResultDto result = writingService.getUserTextResultById(idTest);

        assertEquals(textResult1.getIdWritingTextResult(),result.getIdWritingTextResult());
    }
}