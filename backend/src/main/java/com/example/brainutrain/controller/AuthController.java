package com.example.brainutrain.controller;

import com.example.brainutrain.dto.LoginDto;
import com.example.brainutrain.dto.RegisterDto;
import com.example.brainutrain.dto.ResponseWithToken;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.model.User;
import com.example.brainutrain.service.EmailService;
import com.example.brainutrain.service.TokenService;
import com.example.brainutrain.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<ResponseWithToken> register(@Valid @RequestBody RegisterDto registerDto) {
        if(userService.checkIfEmailIsAlreadyTaken(registerDto.getEmail()) || userService.checkIfLoginIsAlreadyTaken(registerDto.getLogin())){
            String message="Email or login are already taken";
            logger.warn(message);
            throw new AuthenticationFailedException(message);
        }
        registerDto.setPassword(encoder.encode(registerDto.getPassword()));
        UserDto user=userService.createUser(registerDto);
        String token = tokenService.createUserToken(user.getLogin());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWithToken(user,token));
    }

    @GetMapping("/login")
    public ResponseEntity<ResponseWithToken> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUserName(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDto userDto = userService.findByLogin(loginDto.getUserName());
        String token = tokenService.createUserToken(userDto.getLogin());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWithToken(userDto,token));
    }

    @GetMapping("/emailIsTaken")
    public ResponseEntity<Boolean> checkIfEmailIsTaken(String email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkIfEmailIsAlreadyTaken(email));
    }

    @GetMapping("/loginIsTaken")
    public ResponseEntity<Boolean> checkIfLoginIsTaken(String login){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkIfLoginIsAlreadyTaken(login));
    }

    @PutMapping("/confirmEmail")
    public ResponseEntity<Void> confirmEmail(String code){
            userService.validateEmailWithCode(code);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
