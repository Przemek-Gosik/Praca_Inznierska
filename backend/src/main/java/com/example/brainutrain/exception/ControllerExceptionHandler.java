package com.example.brainutrain.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException
                                                        , WebRequest webRequest){
        log.warn(resourceNotFoundException.getMessage());
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false)
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleGlobalException(Exception exception, WebRequest webRequest){
        log.error(exception.getMessage());
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }

    @ExceptionHandler({AuthenticationFailedException.class,
            AuthenticationException.class,
            AccessDeniedException.class,
            BadCredentialsException.class
    })
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage handleAuthenticationFailedException(Exception exception,
                                                            WebRequest webRequest){
        log.warn(exception.getMessage());
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MethodArgumentNotValidErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private MethodArgumentNotValidErrorMessage processFieldErrors(List<FieldError> fieldErrors){
        MethodArgumentNotValidErrorMessage errorMessage = new MethodArgumentNotValidErrorMessage();
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setTimestamp(LocalDateTime.now());
        for(FieldError fieldError: fieldErrors){
            errorMessage.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorMessage;
    }
}
