package com.example.brainutrain.service;

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
        log.info("Nowy rezultat gry utworzony o id : "+ memorizing.getIdMemorizing());
    }

    public List<MemorizingDto> getAllUserResults(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<Memorizing> memorizings = memorizingRepository.findAllByUserIdUser(user.getIdUser());
        return MemorizingMapper.INSTANCE.toDto(memorizings);
    }

    public List<MemorizingDto> getAllUserResultsByType(String type){
        TypeMemory typeMemory = TypeMemoryMapper.getTypeFromString(type);
        User user = authenticationUtils.getUserFromAuthentication();
        List<Memorizing> memorizingList = memorizingRepository
                .findAllByUserIdUserAndTypeOrderByStartTime(user.getIdUser(),typeMemory);
        return MemorizingMapper.INSTANCE.toDto(memorizingList);
    }

    public MemorizingDto getResultById(Long id){
        User user = authenticationUtils.getUserFromAuthentication();
        Memorizing memorizing = memorizingRepository.findMemorizingByIdMemorizing(id).orElseThrow(
                ()->new ResourceNotFoundException(
                        "Result not found for id: "+id
                               )
        );
        if(memorizing.getUser().getIdUser()==user.getIdUser()){
            throw new AccessDeniedException(
                    "Uzytkownik o id: "+user.getIdUser()+" nie ma dostępu do wyniku o id: "+memorizing.getIdMemorizing());
        }
        return MemorizingMapper.INSTANCE.toDto(memorizing);
    }
}
