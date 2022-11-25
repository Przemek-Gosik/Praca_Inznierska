package com.example.brainutrain.service;

import com.example.brainutrain.config.utils.AuthenticationUtils;
import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeFastReading;
import com.example.brainutrain.dto.FastReadingQuestionDto;
import com.example.brainutrain.dto.FastReadingResultDto;
import com.example.brainutrain.dto.FastReadingTextDto;
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
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FastReadingService {

    private final FastReadingResultRepository fastReadingResultRepository;
    private final FastReadingTextRepository fastReadingTextRepository;
    private final FastReadingQuestionRepository fastReadingQuestionRepository;
    private final AuthenticationUtils authenticationUtils;

    public void createFastReadingResult(FastReadingResultDto fastReadingResultDto){
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
        FastReadingText fastReadingText = FastReadingTextMapper.INSTANCE.fromDto(fastReadingTextDto);
        if(!fastReadingText.getQuestions().isEmpty()){
            fastReadingQuestionRepository.saveAll(fastReadingText.getQuestions());
        }
        fastReadingTextRepository.save(fastReadingText);
    }

    public void addQuestionToText(Long id, FastReadingQuestionDto fastReadingQuestionDto){
        FastReadingText fastReadingText = fastReadingTextRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Nie znaleziono quizu dla id:"+id));
        FastReadingQuestion fastReadingQuestion = FastReadingQuestionMapper.INSTANCE.fromDto(fastReadingQuestionDto);
        fastReadingQuestionRepository.save(fastReadingQuestion);
        fastReadingText.getQuestions().add(fastReadingQuestion);
        fastReadingTextRepository.save(fastReadingText);
    }

}
