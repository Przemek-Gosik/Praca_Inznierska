package com.example.brainutrain.exception.handler;

import com.example.brainutrain.exception.AlreadyExistsException;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.exception.message.ErrorMessage;
import com.example.brainutrain.exception.message.MethodArgumentNotValidErrorMessage;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
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

    @ExceptionHandler({ResourceNotFoundException.class,
                        UserNotFoundException.class})
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
        log.error(exception.getClass().getName());
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
            BadCredentialsException.class
    })
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleAuthenticationFailedException(Exception exception,
                                                            WebRequest webRequest){
        log.warn(exception.getMessage());
        return new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }

    @ExceptionHandler({AccessDeniedException.class,
            DisabledException.class,
            CredentialsExpiredException.class,
            LockedException.class,
            AccountExpiredException.class
    })
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage handleForbiddenAuthenticationException(Exception exception,
                                                               WebRequest webRequest){
        log.warn(exception.getMessage());
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }
    @ExceptionHandler({IllegalArgumentException.class,
                HttpMessageNotReadableException.class,
                DataIntegrityViolationException.class,
                InvalidDataAccessApiUsageException.class,
                AlreadyExistsException.class} )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgumentException(Exception exception, WebRequest webRequest){
        log.warn(exception.getMessage());
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MethodArgumentNotValidErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        log.warn(methodArgumentNotValidException.getMessage());
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private MethodArgumentNotValidErrorMessage processFieldErrors(List<FieldError> fieldErrors){
        MethodArgumentNotValidErrorMessage errorMessage = new MethodArgumentNotValidErrorMessage();
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setMessage("Not valid arguments passed");
        errorMessage.setTimestamp(LocalDateTime.now());
        for(FieldError fieldError: fieldErrors){
            errorMessage.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorMessage;
    }
}
