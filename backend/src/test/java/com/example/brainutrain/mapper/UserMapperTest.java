package com.example.brainutrain.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.dto.request.RegisterRequest;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMapperTest {

    private UserMapper userMapper;

    private User user;

    private Role roleUser;
    private Role roleAdmin;
    private Set<Role> roles = new HashSet<>();

    @BeforeEach
    public void init(){
        roleUser = new Role(1L, RoleName.USER);
        roleAdmin = new Role(2L,RoleName.ADMIN);
        roles.add(roleUser);
        user = new User(1L,"login","email@com.pl","pass",false,true,roles);
        userMapper=UserMapper.INSTANCE;
    }

    @Test
    public void INSTANCE_getUserMapperInstance(){
        assertInstanceOf(UserMapper.class,UserMapper.INSTANCE);
    }

    @Test
    public void toDto_givenEntity_getDto(){
        UserDto userDto = userMapper.toDto(user);
        assertAll(
                ()->assertEquals(userDto.getIdUser(),user.getIdUser()),
                ()->assertEquals(userDto.getLogin(),user.getLogin()),
                ()->assertEquals(userDto.getEmail(),user.getEmail()),
                ()->assertEquals(userDto.getRoles().size(),user.getRoles().size())
        );
    }

    @Test
    public void toDto_givenCollection_getListOfDto(){
        User user1 = new User(1L,"login","email@com.pl","pass",false,true,roles);
        List<User> users = List.of(user,user1);
        List<UserDto> userDtoList = UserMapper.INSTANCE.toDto(users);
        assertAll(
                ()->assertEquals(users.size(),userDtoList.size()),
                ()->assertEquals(users.get(0).getIdUser(),userDtoList.get(0).getIdUser()),
                ()->assertEquals(users.get(1).getIdUser(),userDtoList.get(1).getIdUser())
        );
    }

    @Test
    public void fromDto_givenRegisterRequest_getUser(){
        RegisterRequest registerRequest = new RegisterRequest(user.getLogin(),user.getEmail(),user.getPassword());
        User user1 = userMapper.fromDto(registerRequest);
        assertAll(
                ()->assertEquals(user1.getLogin(),registerRequest.getLogin()),
                ()->assertEquals(user1.getEmail(),registerRequest.getEmail()),
                ()->assertEquals(user1.getPassword(),registerRequest.getPassword())
        );
    }
}
