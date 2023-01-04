package com.example.brainutrain.service;

import com.example.brainutrain.dto.ReadingQuestionDto;
import com.example.brainutrain.dto.ReadingTextDto;
import com.example.brainutrain.mapper.ReadingQuestionMapper;
import com.example.brainutrain.mapper.ReadingResultMapper;
import com.example.brainutrain.mapper.ReadingTextMapper;
import com.example.brainutrain.mapper.enum_mapper.LevelMapper;
import com.example.brainutrain.model.ReadingQuestion;
import com.example.brainutrain.model.ReadingResult;
import com.example.brainutrain.model.ReadingText;
import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeReading;
import com.example.brainutrain.dto.ReadingResultDto;
import com.example.brainutrain.exception.AlreadyExistsException;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.ReadingQuestionRepository;
import com.example.brainutrain.repository.ReadingResultRepository;
import com.example.brainutrain.repository.ReadingTextRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Service for fast reading module
 */
@AllArgsConstructor
@Service
@Slf4j
public class ReadingService {

    private final ReadingResultRepository readingResultRepository;
    private final ReadingTextRepository readingTextRepository;
    private final ReadingQuestionRepository readingQuestionRepository;
    private final AuthenticationUtils authenticationUtils;


    /**
     * Method to create new result
     *
     * @param readingResultDto is ReadingResultDto
     */
    public void createResult(ReadingResultDto readingResultDto){
        User user = authenticationUtils.getUserFromAuthentication();
        ReadingResult readingResult = ReadingResultMapper.INSTANCE.create(readingResultDto);
        readingResult.setUser(user);
        if(readingResult.getType()== TypeReading.READING_WITH_QUIZ){
            ReadingText readingText = readingTextRepository.findById(readingResultDto.getIdFastReadingText()).
                    orElseThrow(()->
                            new ResourceNotFoundException(
                                    "Nie odnaleziono tekstu dla id:"+ readingResultDto.getIdFastReadingText()));
            readingResult.setText(readingText);
        }
        readingResultRepository.save(readingResult);
    }


    /**
     * Method to get all user results
     *
     * @return List of ReadingResultDto
     */
    public List<ReadingResultDto> getAllResults(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<ReadingResult> readingResults = readingResultRepository.findAllByUser(user);
        return ReadingResultMapper.INSTANCE.toDto(readingResults);
    }

    /**
     * Method to get all user results of one type
     *
     * @param type is TypeFastReading
     * @return is List of ReadingResultDto
     */
    public List<ReadingResultDto> getAllResultsByType(TypeReading type){
        User user = authenticationUtils.getUserFromAuthentication();
        List<ReadingResult> readingResults = readingResultRepository.findAllByUserAndAndType(user,type);
        return ReadingResultMapper.INSTANCE.toDto(readingResults);
    }


    /**
     * Method to get all texts for fast reading quiz
     *
     * @return is List of ReadingTextDto
     */
    public List<ReadingTextDto> getAllTexts(){
        List<ReadingText> readingTexts = readingTextRepository.findAll();
        return ReadingTextMapper.INSTANCE.toDto(readingTexts);
    }

    /**
     * Method to get all texts from one level
     *
     * @param level is Level
     * @return is List of ReadingDto
     */
    public List<ReadingTextDto> getAllTextsByLevel(Level level){
        List <ReadingText> readingTexts = readingTextRepository.findAllByLevel(level);
        return ReadingTextMapper.INSTANCE.toDto(readingTexts);
    }

    /**
     * Method to get details of text
     *
     * @param id is Long
     * @return is ReadingDto
     */
    public ReadingTextDto getText(Long id){
        ReadingText readingText = readingTextRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Nie znaleziono quizu dla id:"+id));
        return ReadingTextMapper.INSTANCE.toDto(readingText);
    }

    /**
     * Method to createNewText
     *
     * @param readingTextDto is ReadingTextDto
     */
    public void createNewText(ReadingTextDto readingTextDto){
        if(readingTextRepository.existsByTitle(readingTextDto.getTitle())){
            throw new AlreadyExistsException("Tekst o tytule:"+ readingTextDto.getTitle()+" już istnieje!");
        }
        ReadingText readingText = ReadingTextMapper.INSTANCE.fromDto(readingTextDto);
        readingTextRepository.save(readingText);
        if(!readingTextDto.getQuestions().isEmpty()){
            List<ReadingQuestion> questions = readingTextDto.getQuestions()
                    .stream().map(
                            questionDto ->{
                                ReadingQuestion question =
                                        ReadingQuestionMapper.INSTANCE.fromDto(questionDto);
                                question.setReadingText(readingText);
                                return question;
                            }).toList();
            readingQuestionRepository.saveAll(questions);
        }
    }

    /**
     * Method to add question to quiz
     *
     * @param id is Long
     * @param readingQuestionDto is ReadingDto
     */
    public void addQuestionToText(Long id, ReadingQuestionDto readingQuestionDto){
        ReadingText readingText = readingTextRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Nie znaleziono quizu dla id:"+id));
        ReadingQuestion readingQuestion = ReadingQuestionMapper.INSTANCE.fromDto(readingQuestionDto);
        readingQuestion.setReadingText(readingText);
        readingQuestionRepository.save(readingQuestion);
    }

    /**
     * Method to get random text
     *
     * @return is FastReadingTextDto
     */
    public ReadingTextDto drawText(){
        List<ReadingText> textPool = readingTextRepository.findAll();
        Random random = new Random();
        ReadingText readingText = textPool.get(random.nextInt(textPool.size()));
        ReadingTextDto readingTextDto = ReadingTextMapper.INSTANCE.toDto(readingText);
        List<ReadingQuestion> readingQuestions =
                readingQuestionRepository.findAllByReadingTextIdReadingText(readingText.getIdReadingText());
        readingTextDto.setQuestions(ReadingQuestionMapper.INSTANCE.toDto(readingQuestions));
        return readingTextDto;
    }

    /**
     * Method to get random text from one level
     *
     * @param level is String
     * @return is FastReadingTextDto
     */
    public ReadingTextDto drawTextByLevel(String level){
        Level levelEnum= LevelMapper.getLevelFromString(level);
        List<ReadingText> textPool = readingTextRepository.findAllByLevel(levelEnum);
        Random random = new Random();
        ReadingText readingText = textPool.get(random.nextInt(textPool.size()));
        return ReadingTextMapper.INSTANCE.toDto(readingText);
    }

    /**
     * Method to create list of random numbers for Schubert Table
     *
     * @param level1 is String within Enum Level range
     * @return is List of Long
     */
    public List<Long> getSchultzNumbers(String level1){
        Level level = LevelMapper.getLevelFromString(level1);
        int numberLimit;
        switch (level){
            case EASY -> numberLimit = 16;
            case MEDIUM -> numberLimit = 36;
            case ADVANCED -> numberLimit = 100;
            default -> numberLimit = 2;
        }
         return new Random().longs(1,numberLimit+1)
                .distinct()
                .limit(numberLimit)
                .boxed()
                 .collect(Collectors.toList());
    }
}
