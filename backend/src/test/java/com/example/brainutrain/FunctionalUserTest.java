package com.example.brainutrain;

import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Purpose;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.constants.Theme;
import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.dto.request.CodeRequest;
import com.example.brainutrain.dto.request.EmailRequest;
import com.example.brainutrain.dto.request.LoginRequest;
import com.example.brainutrain.dto.request.RegisterRequest;
import com.example.brainutrain.dto.response.ResponseWithPassword;
import com.example.brainutrain.dto.response.ResponseWithToken;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.User;
import com.example.brainutrain.model.ValidationCode;
import com.example.brainutrain.repository.RoleRepository;
import com.example.brainutrain.repository.UserRepository;
import com.example.brainutrain.repository.ValidationCodeRepository;
import com.example.brainutrain.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.patch;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations = "classpath:application-Test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BackendApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@Slf4j
public class FunctionalUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    private ObjectMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ValidationCodeRepository codeRepository;

    @BeforeEach
    public void init(){
        mapper = new ObjectMapper();
    }


    @Test
    @Rollback
    public void registerUser_givenValidData_thenGetResponseWithToken() throws Exception{
        RegisterRequest request = new RegisterRequest("login","login@email.com","password","password");

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.userDto.login",is(request.getLogin())))
                .andExpect(jsonPath("$.userDto.email",is(request.getEmail())))
                .andExpect(jsonPath("$.userDto.roles.[0].roleName",is(RoleName.USER.toString())));

        assertInstanceOf(ResponseWithToken.class,getObjectFromJsonResponse(response.andReturn()));

        userRepository.deleteAll();
    }

    @Test
    @Rollback
    public void logInUser_thenGetToken_thenChangeSetting() throws Exception {
        String login = "login1";
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(login,"login1@email.com",password,password);
        userService.createUser(registerRequest,encoder);
        User user = userRepository.findUserByLogin(login).orElseThrow(
                ()-> new UsernameNotFoundException("error")
        );
        user.setIsEmailConfirmed(true);
        userRepository.save(user);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new LoginRequest(login,password))));

        resultActions.andExpect(status().isOk());

        ResponseWithToken responseWithToken = getObjectFromJsonResponse(resultActions.andReturn());
        String token = responseWithToken.getToken();
        SettingDto settingDto = new SettingDto(null, FontSize.MEDIUM, Theme.CONTRAST);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/auth/changeSetting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token)
                        .content(mapper.writeValueAsString(settingDto)))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void logInUser_thenGetToken_thenDeleteAccount() throws Exception{
        String login = "login2";
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(login,"login2@email.com",password,password);
        userService.createUser(registerRequest,encoder);
        User user = userRepository.findUserByLogin(login).orElseThrow(
                ()-> new UsernameNotFoundException("error")
        );
        user.setIsEmailConfirmed(true);
        userRepository.save(user);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new LoginRequest(login,password))));

        resultActions.andExpect(status().isOk());

        ResponseWithToken responseWithToken = getObjectFromJsonResponse(resultActions.andReturn());
        String token = responseWithToken.getToken();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/auth/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token)
                        )
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new LoginRequest(login,password))))
                .andExpect(status().isForbidden());
    }

    @Test
    @Rollback
    public void getUser_forgotPassword_getNewPasswordAndLogIn() throws Exception{
        //before
        String login = "login2";
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(login,"login2@email.com",password,password);
        userService.createUser(registerRequest,encoder);
        User user = userRepository.findUserByLogin(login).orElseThrow(
                ()-> new UsernameNotFoundException("error")
        );
        user.setIsEmailConfirmed(true);
        userRepository.save(user);
        EmailRequest emailRequest = new EmailRequest(user.getEmail());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/passwordRecovery/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(emailRequest)))
                .andExpect(status().isOk());

        //Then
        ValidationCode code = codeRepository.findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user, Purpose.PASSWORD_REMINDER)
                .orElseThrow(()->new ResourceNotFoundException("error"));

        ResultActions resultActions= mockMvc.perform(MockMvcRequestBuilders.patch("/api/auth/passwordRecovery/code/"+user.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CodeRequest(code.getCode()))));

        resultActions.andExpect(status().isOk());
        ResponseWithPassword response = getResponseWithPassword(resultActions.andReturn());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new LoginRequest(user.getLogin(),response.getNewPassword()))))
                .andExpect(status().isOk());


    }



    private ResponseWithToken getObjectFromJsonResponse(MvcResult result){
        try{
            String responseToString = result.getResponse().getContentAsString();
            log.info(responseToString);
            return mapper.readValue(responseToString,ResponseWithToken.class);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private ResponseWithPassword getResponseWithPassword(MvcResult result){
        try{
            String responseToString = result.getResponse().getContentAsString();
            return mapper.readValue(responseToString,ResponseWithPassword.class);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


}


