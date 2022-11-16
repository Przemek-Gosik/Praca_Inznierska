package com.example.brainutrain.controller;

import com.example.brainutrain.dto.LoginDto;
import com.example.brainutrain.dto.RegisterDto;
import com.example.brainutrain.dto.ResponseWithToken;
import com.example.brainutrain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
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
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<ResponseWithToken> register(@Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(registerDto,encoder));
    }

    @GetMapping("/login")
    public ResponseEntity<ResponseWithToken> login(LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.logInUser(loginDto,authenticationManager));
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
