package com.example.brainutrain.service;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.dto.response.ResponseWithNumbers;
import com.example.brainutrain.mapper.enum_mapper.LevelMapper;
import com.example.brainutrain.mapper.enum_mapper.TypeMemoryMapper;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Service for memorizing module
 */
@AllArgsConstructor
@Service
@Slf4j
public class MemorizingService {

    private final MemorizingRepository memorizingRepository;

    private final AuthenticationUtils authenticationUtils;

    /**
     * Method to save memorizing game result
     *
     * @param memorizingDto is MemorizingDto
     */
    public void saveMemorizing(MemorizingDto memorizingDto){
        User user = authenticationUtils.getUserFromAuthentication();
        Memorizing memorizing = MemorizingMapper.INSTANCE.fromDto(memorizingDto);
        memorizing.setStartTime(Timestamp.valueOf(memorizingDto.getStartTime()));
        memorizing.setUser(user);
        memorizingRepository.save(memorizing);
        log.info("Nowy rezultat gry utworzony o id : "+ memorizing.getIdMemorizing());
    }

    /**
     * Method to get all user results
     *
     * @return is List of MemorizingDto
     */
    public List<MemorizingDto> getAllUserResults(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<Memorizing> memorizings = memorizingRepository.findAllByUserIdUser(user.getIdUser());
        return MemorizingMapper.INSTANCE.toDto(memorizings);
    }

    /**
     * Method to get all user results of one type
     *
     * @param type is String
     * @return is List of MemorizingDto
     */
    public List<MemorizingDto> getAllUserResultsByType(String type){
        TypeMemory typeMemory = TypeMemoryMapper.getTypeFromString(type);
        User user = authenticationUtils.getUserFromAuthentication();
        List<Memorizing> memorizingList = memorizingRepository
                .findAllByUserIdUserAndTypeOrderByStartTime(user.getIdUser(),typeMemory);
        return MemorizingMapper.INSTANCE.toDto(memorizingList);
    }

    /**
     * Method to get result details
     *
     * @param id is Long
     * @return is MemorizingDto
     */
    public MemorizingDto getResultById(Long id){
        User user = authenticationUtils.getUserFromAuthentication();
        Memorizing memorizing = memorizingRepository.findMemorizingByIdMemorizing(id).orElseThrow(
                ()->new ResourceNotFoundException(
                        "Nie odnaleziono wyniku gry na zapamiętywanie dla id : "+id
                               )
        );
        if(! memorizing.getUser().getIdUser().equals(user.getIdUser()) ){
            throw new AccessDeniedException(
                    "Uzytkownik o id: "+user.getIdUser()+" nie ma dostępu do wyniku o id: "+memorizing.getIdMemorizing());
        }
        return MemorizingMapper.INSTANCE.toDto(memorizing);
    }

    public ResponseWithNumbers generateRandomNumbers(String level){
        Level level1 = LevelMapper.getLevelFromString(level);
        int numberLimit;
        switch (level1){
            case EASY -> numberLimit=5;
            case MEDIUM -> numberLimit=10;
            case ADVANCED -> numberLimit=15;
            default -> numberLimit=1;
        }
        List<Integer> randomNumbers = new Random().ints(1, 16)
                .distinct()
                .limit(numberLimit)
                .boxed().collect(Collectors.toList());
        List <Integer> randomNumbersShuffled = new ArrayList<>(randomNumbers);
        Collections.shuffle(randomNumbersShuffled);
        return new ResponseWithNumbers(randomNumbers,randomNumbersShuffled);
    }
}
