package com.example.brainutrain.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id){
        super("Nie znaleziono uzytkownika dla id: "+id);
    }

}
