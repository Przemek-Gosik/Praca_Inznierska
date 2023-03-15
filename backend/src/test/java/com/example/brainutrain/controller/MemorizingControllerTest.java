package com.example.brainutrain.controller;

import com.example.brainutrain.BackendApplication;
import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.constants.TypeMemory;
import com.example.brainutrain.dto.MemorizingDto;
import com.example.brainutrain.model.Memorizing;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.MemorizingRepository;
import com.example.brainutrain.repository.RoleRepository;
import com.example.brainutrain.repository.UserRepository;
import com.example.brainutrain.utils.TokenCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations = "classpath:application-Test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = BackendApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class MemorizingControllerTest  {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private MemorizingRepository memorizingRepository;

    @Autowired
    private TokenCreator tokenCreator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Role roleUser;

    private User user;

    private String token;

    private Set<Role> roles = new HashSet<>();
    @BeforeEach
    public void init(){
        roleUser = new Role(1L, RoleName.USER);
        roleRepository.saveAndFlush(roleUser);
        roles.add(roleUser);
        user = new User(1L,"login","login@email.com","password",false,true,roles);
        userRepository.saveAndFlush(user);
        token=tokenCreator.createUserToken(user.getLogin());
    }

    @Test
    @Rollback
    public void givenValidMemorizingDto_thenCreateMemorizing_thenStatus201() throws Exception {
        MemorizingDto memorizingDto = new MemorizingDto(1L, TypeMemory.MEMORY, Level.EASY, 10L, LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/memorizing")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,"Bearer"+token)
                .content(objectMapper.writeValueAsString(memorizingDto)))
                .andExpect(status().isCreated());

        List<Memorizing> result = memorizingRepository.findAll();
        User user1=result.get(0).getUser();
        assertThat(result).extracting(Memorizing::getLevel).contains(memorizingDto.getLevel());
        assertThat(result).extracting(Memorizing::getType).contains(memorizingDto.getType());
        assertEquals(user.getIdUser(),user1.getIdUser());

        memorizingRepository.deleteAll();
    }

    @Test
    public void givenMemorizingDtoWithoutType_thenInValidArgumentException_thenStatus400() throws Exception {
        MemorizingDto memorizingDto = new MemorizingDto(null,null,Level.EASY,10L,LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/memorizing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token)
                        .content(objectMapper.writeValueAsString(memorizingDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message",is("Typ testu na zapamiętywanie nie może być pusty!\r\n")))
                .andDo(print());
    }

    @Test
    public void givenMemorizingDtoWithoutLevel_thenInvalidArgumentException_thenStatus400() throws Exception{
        MemorizingDto memorizingDto = new MemorizingDto(null,TypeMemory.MEMORY,null,10L,LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/memorizing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token)
                        .content(objectMapper.writeValueAsString(memorizingDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message",is("Poziom testu na zapamiętywanie nie może być pusty!\r\n")))
                .andDo(print());
    }

    @Test
    public void givenMemorizingDtoWithoutScore_thenInvalidArgumentException_thenStatus400() throws Exception{
        MemorizingDto memorizingDto = new MemorizingDto(null,TypeMemory.MEMORY,Level.EASY,null,LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/memorizing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token)
                        .content(objectMapper.writeValueAsString(memorizingDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message",is("Wynik testu na zapamiętywanie nie może być pusty!\r\n")))
                .andDo(print());
    }


    @Test
    @Rollback
    public void getAllUserResults_userExistsInSystem_thenStatus200() throws Exception {
        Memorizing memorizing1 = createMemorizingTestResult(TypeMemory.MNEMONICS,Level.EASY,5L,LocalDateTime.now(),user);
        Memorizing memorizing2 = createMemorizingTestResult(TypeMemory.MNEMONICS,Level.MEDIUM,6L,LocalDateTime.now(),user);

        mockMvc.perform(get("/api/memorizing")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,"Bearer"+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].score",is(memorizing1.getScore().intValue())))
                .andExpect(jsonPath("$[1].score",is(memorizing2.getScore().intValue())));
    }

    @Test
    public void getAllUserResults_userDoesNotExistsInSystem_thenStatus401() throws Exception{
        String token1 = tokenCreator.createUserToken("User2");

        mockMvc.perform(get("/api/memorizing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token1))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllUserResults_tokenNotGiven_thenStatus401() throws Exception{
        mockMvc.perform(get("/api/memorizing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    public void getResultById_givenValidId_thenStatus200() throws Exception{
        Memorizing memorizing1 = createMemorizingTestResult(TypeMemory.MNEMONICS,Level.EASY,5L,LocalDateTime.now(),user);

        mockMvc.perform(get("/api/memorizing/"+memorizing1.getIdMemorizing())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score",is(memorizing1.getScore().intValue())));
    }

    @Test
    @Rollback
    void getResultById_givenInvalidId_thenStatus404() throws Exception{
        createMemorizingTestResult(TypeMemory.MNEMONICS,Level.EASY,5L,LocalDateTime.now(),user);

        mockMvc.perform(get("/api/memorizing/"+505)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token))
                .andExpect(status().isNotFound());
    }

    @Test
    void getResultById_givenInvalidId_thenStatus403() throws Exception{
        User user1 = new User(2L,"login2","login2@email.com","pass",false,true,roles);
        userRepository.saveAndFlush(user1);
        Memorizing memorizing1 = createMemorizingTestResult(TypeMemory.MNEMONICS,Level.EASY,5L,LocalDateTime.now(),user1);

        mockMvc.perform(get("/api/memorizing/"+memorizing1.getIdMemorizing().intValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token))
                .andExpect(status().isForbidden());
    }

    @Test
    void getResultsByType_givenValidType_thenStatus200() throws Exception{
        Memorizing memorizing1 = createMemorizingTestResult(TypeMemory.MNEMONICS,Level.EASY,5L,LocalDateTime.now(),user);
        Memorizing memorizing2 = createMemorizingTestResult(TypeMemory.MEMORY,Level.MEDIUM,6L,LocalDateTime.now(),user);

        mockMvc.perform(get("/api/memorizing/type/"+"MNEMONICS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMemorizing",is(memorizing1.getIdMemorizing().intValue())))
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    void getResultsByType_givenInValidType_thenStatus400() throws Exception{
        mockMvc.perform(get("/api/memorizing/type/"+"CYFRY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer"+token))
                .andExpect(status().isBadRequest());

    }

    @Transactional
    protected Memorizing createMemorizingTestResult(TypeMemory typeMemory, Level level, Long score, LocalDateTime startTime, User user){
        Memorizing memorizing = new Memorizing(null,typeMemory,level,score, Timestamp.valueOf(startTime),user);
        return memorizingRepository.saveAndFlush(memorizing);
    }
}
