package com.example.brainutrain.controller;

import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.service.AdminService;
import com.example.brainutrain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/admin",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@PreAuthorize("hasAuthority('USER')")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(adminService.getAllUsers());
    }



}
