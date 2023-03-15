package com.example.brainutrain.controller;

import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/admin",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/user/give-role/{id}")
    public ResponseEntity<UserDto> giveUserAdminRole(@PathVariable Long id){
        return ResponseEntity.ok(userService.giveUserAdminRole(id));
    }

    @PatchMapping("/user/take-role/{id}")
    public ResponseEntity<UserDto> takeUserAdminRole(@PathVariable Long id){
        return ResponseEntity.ok(userService.takeUserAdminRole(id));
    }





}
