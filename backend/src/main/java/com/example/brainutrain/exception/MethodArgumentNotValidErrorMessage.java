package com.example.brainutrain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MethodArgumentNotValidErrorMessage {

    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    public void addFieldError(String objectName,String path,String message){
        FieldError fieldError = new FieldError(objectName,path,message);
        this.fieldErrors.add(fieldError);
    }


}
