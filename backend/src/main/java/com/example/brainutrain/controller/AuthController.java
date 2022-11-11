package com.example.brainutrain.controller;

import com.example.brainutrain.dto.LoginDto;
import com.example.brainutrain.dto.RegisterDto;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.model.User;
import com.example.brainutrain.service.TokenService;
import com.example.brainutrain.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDto registerDto) {
        if(userService.checkIfEmailIsAlreadyTaken(registerDto.getEmail()) || userService.checkIfLoginIsAlreadyTaken(registerDto.getLogin())){
            String message="Email or login are already taken";
            logger.warn(message);
            throw new AuthenticationFailedException(message);
        }
        registerDto.setPassword(encoder.encode(registerDto.getPassword()));
        User user = userService.createUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(LoginDto loginDto) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getUserName(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenService.createUserToken(authentication);
            return ResponseEntity.ok(token);
    }

    @GetMapping("/dupa")
    public ResponseEntity<String> dupa() {
        return ResponseEntity.ok("Duuupa");
    }
}
