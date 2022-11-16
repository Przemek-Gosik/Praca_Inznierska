package com.example.brainutrain.config.utils;

import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PermissionChecker {
    private final UserRepository userRepository;

    public User checkPermission(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByIdUser(id).orElseThrow(
                ()->new ResourceNotFoundException("User not found for id: "+id)
        );
        if(user.getLogin().equals(authentication.getPrincipal().toString())){
            return user;
        }
        throw new AuthenticationFailedException("No permissions to change details for user: "+user.getIdUser());
    }

}
