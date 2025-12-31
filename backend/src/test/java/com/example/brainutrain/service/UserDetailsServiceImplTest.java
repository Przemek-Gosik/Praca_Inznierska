package com.example.brainutrain.service;

import com.example.brainutrain.config.security.UserDetailsImpl;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user1;
    private UserDetailsImpl userDetails1;
    private Role roleUser;
    private Set<Role> roles = new HashSet<>();

    @BeforeEach
    public void init() {
        roleUser = new Role(1L, RoleName.USER);
        roles.add(roleUser);
        user1 = new User(1L, "login", "email@com.pl", "pass", false, true, roles);
        userDetails1 = new UserDetailsImpl(user1);
    }

    @Test
    public void loadUserByUserName_GivenValidUserName_GetUserDetails() {
        String username = "login";
        when(userRepository.findUserByLogin(username)).thenReturn(Optional.of(user1));
        UserDetails result = userDetailsService.loadUserByUsername(username);
        assertEquals(userDetails1.getUsername(), result.getUsername());
    }

    @Test
    public void loadUserByUserName_GivenInvalidUserName_ThrowsUserNameNotFoundException() {
        String username = "login2";
        when(userRepository.findUserByLogin(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}
