package com.example.brainutrain.service;

import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.constants.TypeMemory;
import com.example.brainutrain.dto.MemorizingDto;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.mapper.MemorizingMapper;
import com.example.brainutrain.model.Memorizing;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.MemorizingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class MemorizingService {

    private final MemorizingRepository memorizingRepository;

    private final AuthenticationUtils authenticationUtils;

    public void saveMemorizing(MemorizingDto memorizingDto){
        User user = authenticationUtils.getUserFromAuthentication();
        Memorizing memorizing = MemorizingMapper.INSTANCE.fromDto(memorizingDto);
        memorizing.setStartTime(Timestamp.valueOf(memorizingDto.getStartTime()));
        memorizing.setUser(user);
        memorizingRepository.save(memorizing);
        log.info("New memorizing result created with id: "+ memorizing.getIdMemorizing());
    }

    public List<MemorizingDto> getAllUserResultsByType(TypeMemory type){
        User user = authenticationUtils.getUserFromAuthentication();
        List<Memorizing> memorizingList = memorizingRepository
                .findAllByUserIdUserAndTypeOrderByStartTime(user.getIdUser(),type);
        return MemorizingMapper.INSTANCE.toDto(memorizingList);
    }

    public MemorizingDto getUserResult(Long id){
        User user = authenticationUtils.getUserFromAuthentication();
        Memorizing memorizing = memorizingRepository.findMemorizingByIdMemorizing(id).orElseThrow(
                ()->new ResourceNotFoundException(
                        "Result not found for id: "+id
                               )
        );
        return MemorizingMapper.INSTANCE.toDto(memorizing);
    }
}
