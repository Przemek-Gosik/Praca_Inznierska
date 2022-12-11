package com.example.brainutrain.controller;

import com.example.brainutrain.dto.request.RegisterRequest;
import com.example.brainutrain.dto.request.LoginRequest;
import com.example.brainutrain.dto.request.CodeRequest;
import com.example.brainutrain.dto.request.EmailRequest;
import com.example.brainutrain.dto.response.ResponseWithPassword;
import com.example.brainutrain.dto.response.ResponseWithToken;
import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.dto.request.NewLoginRequest;
import com.example.brainutrain.dto.request.NewPasswordRequest;
import com.example.brainutrain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<ResponseWithToken> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(registerRequest,encoder));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWithToken> login(LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.logInUser(loginRequest,authenticationManager));
    }

    @GetMapping("/emailIsTaken/{email}")
    public ResponseEntity<Boolean> checkIfEmailIsTaken(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkIfEmailIsAlreadyTaken(email));
    }

    @GetMapping("/loginIsTaken/{login}")
    public ResponseEntity<Boolean> checkIfLoginIsTaken(@PathVariable String login){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkIfLoginIsAlreadyTaken(login));
    }

    @PatchMapping("/confirmEmail")
    public ResponseEntity<Void> confirmEmail(@Valid @RequestBody CodeRequest codeRequest){
            userService.validateEmailWithCode(codeRequest.getCode());
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody NewPasswordRequest newPasswordRequest){
        userService.changeUserPassword(newPasswordRequest,encoder,authenticationManager);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/changeLogin")
    public ResponseEntity<ResponseWithToken> changeLogin(@Valid @RequestBody NewLoginRequest newLoginRequest){
        return ResponseEntity.ok(userService.changeUserLogin(newLoginRequest));
    }

    @PatchMapping("/changeEmail")
    public ResponseEntity <UserDto> changeEmail( @Valid @RequestBody EmailRequest emailRequest){
        return ResponseEntity.ok(userService.changeUserEmail(emailRequest));
    }

    @PatchMapping("/changeSetting")
    public ResponseEntity<SettingDto> changeSetting(@Valid @RequestBody SettingDto settingDto){
        return ResponseEntity.ok(userService.changeUserSetting(settingDto));
    }

    @PostMapping("/passwordRecovery/email")
    public ResponseEntity<Void> recoverPassword(@RequestBody EmailRequest emailRequest){
        userService.sendEmailForPasswordRecovery(emailRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/passwordRecovery/code/{email}")
    public ResponseEntity<ResponseWithPassword> getNewPassword(@PathVariable String email,@Valid @RequestBody CodeRequest codeRequest){
        return ResponseEntity.ok(userService.createNewPassword(email,codeRequest,encoder));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(){
        userService.deleteUserAccount();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
