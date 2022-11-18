package com.example.brainutrain.controller;

import com.example.brainutrain.dto.LoginDto;
import com.example.brainutrain.dto.RegisterDto;
import com.example.brainutrain.dto.request.CodeRequest;
import com.example.brainutrain.dto.response.ResponseWithToken;
import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.dto.request.NewEmailRequest;
import com.example.brainutrain.dto.request.NewLoginRequest;
import com.example.brainutrain.dto.request.NewPasswordRequest;
import com.example.brainutrain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/emailIsTaken/{email}")
    public ResponseEntity<Boolean> checkIfEmailIsTaken(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkIfEmailIsAlreadyTaken(email));
    }

    @GetMapping("/loginIsTaken/{login}")
    public ResponseEntity<Boolean> checkIfLoginIsTaken(@PathVariable String login){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkIfLoginIsAlreadyTaken(login));
    }

    @PutMapping("/confirmEmail/{id}")
    public ResponseEntity<Void> confirmEmail(@PathVariable Long id,@Valid @RequestBody CodeRequest codeRequest){
            userService.validateEmailWithCode(id,codeRequest.getCode());
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,@Valid @RequestBody NewPasswordRequest newPasswordRequest){
        userService.changeUserPassword(id,newPasswordRequest,encoder,authenticationManager);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/changeLogin/{id}")
    public ResponseEntity<ResponseWithToken> changeLogin(@PathVariable Long id,@Valid @RequestBody NewLoginRequest newLoginRequest){
        return ResponseEntity.ok(userService.changeUserLogin(id,newLoginRequest));
    }

    @PutMapping("/changeEmail/{id}")
    public ResponseEntity <UserDto> changeEmail(@PathVariable Long id, @Valid @RequestBody NewEmailRequest newEmailRequest){
        return ResponseEntity.ok(userService.changeUserEmail(id, newEmailRequest));
    }

    @PutMapping("/changeSetting/{id}")
    public ResponseEntity<SettingDto> changeSetting(@PathVariable Long id,@Valid @RequestBody SettingDto settingDto){
        return ResponseEntity.ok(userService.changeUserSetting(id,settingDto));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserAccount(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
