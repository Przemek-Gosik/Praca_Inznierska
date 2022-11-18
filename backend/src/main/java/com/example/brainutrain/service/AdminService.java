package com.example.brainutrain.service;

import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.mapper.UserMapper;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    public List<UserDto> getAllUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getPrincipal().toString());
        List<User> users = userRepository.findUsersByLoginIsNotLike(authentication.getPrincipal().toString());
        return UserMapper.INSTANCE.toDto(users);
    }

    public void deleteUser(Long userId){
        User user = userRepository.findUserByIdUser(userId).orElseThrow(
                ()-> new ResourceNotFoundException("No user found for id: "+userId)
        );
        user.setIsActive(false);
        userRepository.save(user);
        log.info("User by id "+userId+" deactivated");
    }
}
