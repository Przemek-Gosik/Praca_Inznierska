package com.example.brainutrain.service;

import com.example.brainutrain.mapper.enum_mapper.LevelMapper;
import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeFastReading;
import com.example.brainutrain.dto.FastReadingQuestionDto;
import com.example.brainutrain.dto.FastReadingResultDto;
import com.example.brainutrain.dto.FastReadingTextDto;
import com.example.brainutrain.exception.AlreadyExistsException;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.mapper.FastReadingQuestionMapper;
import com.example.brainutrain.mapper.FastReadingResultMapper;
import com.example.brainutrain.mapper.FastReadingTextMapper;
import com.example.brainutrain.model.FastReadingQuestion;
import com.example.brainutrain.model.FastReadingResult;
import com.example.brainutrain.model.FastReadingText;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.FastReadingQuestionRepository;
import com.example.brainutrain.repository.FastReadingResultRepository;
import com.example.brainutrain.repository.FastReadingTextRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
@Slf4j
public class FastReadingService {

    private final FastReadingResultRepository fastReadingResultRepository;
    private final FastReadingTextRepository fastReadingTextRepository;
    private final FastReadingQuestionRepository fastReadingQuestionRepository;
    private final AuthenticationUtils authenticationUtils;

    public void createResult(FastReadingResultDto fastReadingResultDto){
        User user = authenticationUtils.getUserFromAuthentication();
        FastReadingResult fastReadingResult = FastReadingResultMapper.INSTANCE.create(fastReadingResultDto);
        fastReadingResult.setUser(user);
        if(fastReadingResult.getType()==TypeFastReading.READING_WITH_QUIZ){
            FastReadingText fastReadingText = fastReadingTextRepository.findById(fastReadingResultDto.getIdFastReadingText()).
                    orElseThrow(()->
                            new ResourceNotFoundException(
                                    "Nie odnaleziono tekstu dla id:"+fastReadingResultDto.getIdFastReadingText()));
            fastReadingResult.setText(fastReadingText);
        }
        fastReadingResultRepository.save(fastReadingResult);
    }

    public List<FastReadingResultDto> getAllResults(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<FastReadingResult> fastReadingResults = fastReadingResultRepository.findAllByUser(user);
        return FastReadingResultMapper.INSTANCE.toDto(fastReadingResults);
    }

    public List<FastReadingResultDto> getAllResultsByType(TypeFastReading type){
        User user = authenticationUtils.getUserFromAuthentication();
        List<FastReadingResult> fastReadingResults = fastReadingResultRepository.findAllByUserAndAndType(user,type);
        return FastReadingResultMapper.INSTANCE.toDto(fastReadingResults);
    }

    public List<FastReadingTextDto> getAllTexts(){
        List<FastReadingText> fastReadingTexts = fastReadingTextRepository.findAll();
        return FastReadingTextMapper.INSTANCE.toDto(fastReadingTexts);
    }

    public List<FastReadingTextDto> getAllTextsByLevel(Level level){
        List <FastReadingText> fastReadingTexts = fastReadingTextRepository.findAllByLevel(level);
        return FastReadingTextMapper.INSTANCE.toDto(fastReadingTexts);
    }

    public FastReadingTextDto getText(Long id){
        FastReadingText fastReadingText = fastReadingTextRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Nie znaleziono quizu dla id:"+id));
        return FastReadingTextMapper.INSTANCE.toDto(fastReadingText);
    }

    public void createNewText(FastReadingTextDto fastReadingTextDto){
        if(fastReadingTextRepository.existsByTitle(fastReadingTextDto.getTitle())){
            throw new AlreadyExistsException("Tekst o tytule:"+fastReadingTextDto.getTitle()+" już istnieje!");
        }
        FastReadingText fastReadingText = FastReadingTextMapper.INSTANCE.fromDto(fastReadingTextDto);
        fastReadingTextRepository.save(fastReadingText);
        if(!fastReadingTextDto.getQuestions().isEmpty()){
            List<FastReadingQuestion> questions = fastReadingTextDto.getQuestions()
                    .stream().map(
                            questionDto ->{
                                FastReadingQuestion question =
                                        FastReadingQuestionMapper.INSTANCE.fromDto(questionDto);
                                question.setFastReadingText(fastReadingText);
                                return question;
                            }).toList();
            fastReadingQuestionRepository.saveAll(questions);
        }
    }

    public void addQuestionToText(Long id, FastReadingQuestionDto fastReadingQuestionDto){
        FastReadingText fastReadingText = fastReadingTextRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Nie znaleziono quizu dla id:"+id));
        FastReadingQuestion fastReadingQuestion = FastReadingQuestionMapper.INSTANCE.fromDto(fastReadingQuestionDto);
        fastReadingQuestion.setFastReadingText(fastReadingText);
        fastReadingQuestionRepository.save(fastReadingQuestion);
    }

    public FastReadingTextDto drawText(){
        List<FastReadingText> textPool = fastReadingTextRepository.findAll();
        Random random = new Random();
        FastReadingText fastReadingText = textPool.get(random.nextInt(textPool.size()));
        FastReadingTextDto fastReadingTextDto=FastReadingTextMapper.INSTANCE.toDto(fastReadingText);
        List<FastReadingQuestion> fastReadingQuestions =
                fastReadingQuestionRepository.findAllByFastReadingTextIdFastReadingText(fastReadingText.getIdFastReadingText());
        fastReadingTextDto.setQuestions(FastReadingQuestionMapper.INSTANCE.toDto(fastReadingQuestions));
        return fastReadingTextDto;
    }

    public FastReadingTextDto drawTextByLevel(String level){
        Level levelEnum= LevelMapper.getLevelFromString(level);
        List<FastReadingText> textPool = fastReadingTextRepository.findAllByLevel(levelEnum);
        Random random = new Random();
        FastReadingText fastReadingText = textPool.get(random.nextInt(textPool.size()));
        return FastReadingTextMapper.INSTANCE.toDto(fastReadingText);
    }
}
