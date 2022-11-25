package com.example.brainutrain.service;

import com.example.brainutrain.config.utils.AuthenticationUtils;
import com.example.brainutrain.constants.TypeFastReading;
import com.example.brainutrain.dto.FastReadingResultDto;
import com.example.brainutrain.mapper.FastReadingResultMapper;
import com.example.brainutrain.model.FastReadingResult;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.FastReadingResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FastReadingService {

    private final FastReadingResultRepository fastReadingResultRepository;
    private final AuthenticationUtils authenticationUtils;

    public void createFastReadingResult(FastReadingResultDto fastReadingResultDto){
        User user = authenticationUtils.getUserFromAuthentication();
        FastReadingResult fastReadingResult = FastReadingResultMapper.INSTANCE.create(fastReadingResultDto);
        fastReadingResult.setUser(user);
        fastReadingResultRepository.save(fastReadingResult);
    }

    public List<FastReadingResultDto> getAllFastReadingResult(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<FastReadingResult> fastReadingResults = fastReadingResultRepository.findAllByUser(user);
        return FastReadingResultMapper.INSTANCE.toDto(fastReadingResults);
    }

    public List<FastReadingResultDto> getAllFastReadingResultByType(TypeFastReading type){
        User user = authenticationUtils.getUserFromAuthentication();
        List<FastReadingResult> fastReadingResults = fastReadingResultRepository.findAllByUserAndAndType(user,type);
        return FastReadingResultMapper.INSTANCE.toDto(fastReadingResults);
    }


}
