package com.example.brainutrain.service;

import com.example.brainutrain.config.security.UserDetailsImpl;
import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Purpose;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.constants.Theme;
import com.example.brainutrain.dto.request.RegisterRequest;
import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.dto.request.CodeRequest;
import com.example.brainutrain.dto.request.EmailRequest;
import com.example.brainutrain.dto.request.LoginRequest;
import com.example.brainutrain.dto.request.NewLoginRequest;
import com.example.brainutrain.dto.request.NewPasswordRequest;
import com.example.brainutrain.dto.response.ResponseWithPassword;
import com.example.brainutrain.dto.response.ResponseWithToken;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.exception.UserNotFoundException;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.Setting;
import com.example.brainutrain.model.User;
import com.example.brainutrain.model.ValidationCode;
import com.example.brainutrain.repository.RoleRepository;
import com.example.brainutrain.repository.SettingRepository;
import com.example.brainutrain.repository.UserRepository;
import com.example.brainutrain.repository.ValidationCodeRepository;
import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.utils.EmailSender;
import com.example.brainutrain.utils.StringGenerator;
import com.example.brainutrain.utils.TokenCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private SettingRepository settingRepository;

    @Mock
    private ValidationCodeRepository validationCodeRepository;

    @Mock
    private TokenCreator tokenCreator;

    @Mock
    private EmailSender emailSender;

    @Mock
    private StringGenerator stringGenerator;

    @Mock
    private AuthenticationUtils utils;
    @InjectMocks
    private UserService userService;

    private User user1;
    private UserDetailsImpl userDetails1;
    private Setting setting1;
    private ValidationCode validationCode1;
    private ValidationCode validationCode2;
    private Role roleUser;
    private Role roleAdmin;
    private Set<Role> roles = new HashSet<>();
    private String token;

    @BeforeEach
    public void init(){
        roleUser = new Role(1L, RoleName.USER);
        roleAdmin = new Role(2L,RoleName.ADMIN);
        roles.add(roleUser);
        user1 = new User(1L,"login","email@com.pl","pass",false,true,roles);
        setting1 = new Setting(1L, FontSize.MEDIUM, Theme.DAY,user1);
        userDetails1 = new UserDetailsImpl(user1);
        validationCode1=new ValidationCode(1L,"12345", Purpose.EMAIL_VERIFICATION,false,user1);
        validationCode2=new ValidationCode(2L,"54321",Purpose.PASSWORD_REMINDER,false,user1);
        token="token";
    }

    @Test
    public void loadUserByUserName_GivenValidUserName_GetUserDetails(){
        String username="login";
        when(userRepository.findUserByLogin(username)).thenReturn(Optional.of(user1));
        assertEquals(userDetails1.getUsername(),userService.loadUserByUsername(username).getUsername());
    }

    @Test
    public void loadUserByUserName_GivenInValidUserName_ThrowsUserNameNotFoundException(){
        String username="login2";
        when(userRepository.findUserByLogin(username)).thenReturn(Optional.ofNullable(null));
        assertThrows(UsernameNotFoundException.class,()->userService.loadUserByUsername(username));
    }

    @Test
    public void logInUser_GivenValidLoginRequest_GetResponseWithToken(){
        AuthenticationManager manager = Mockito.mock(AuthenticationManager.class);
        String username="login";
        String password="pass";
        LoginRequest loginRequest = new LoginRequest(username,password);
        when(userRepository.findUserByLogin(username)).thenReturn(Optional.of(user1));
        when(settingRepository.findSettingByUserIdUser(user1.getIdUser())).thenReturn(Optional.of(setting1));
        when(tokenCreator.createUserToken(username)).thenReturn(token);
        ResponseWithToken response = userService.logInUser(loginRequest,manager);
        assertAll(
                ()->assertEquals(token,response.getToken()),
                ()->assertEquals(setting1.getIdSetting(),response.getSettingDto().getIdSetting()),
                ()->assertEquals(user1.getEmail(),response.getUserDto().getEmail())
        );
    }

    @Test
    public void logInUser_GivenInvalidLogin_ThrowUserNameNotFoundException(){

        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        AuthenticationManager manager = Mockito.mock(AuthenticationManager.class);
        String username="login1";
        String password="pass";
        LoginRequest loginRequest = new LoginRequest(username,password);
        when(userRepository.findUserByLogin(username)).thenReturn(Optional.ofNullable(null));

        assertThrows(UsernameNotFoundException.class,()->userService.logInUser(loginRequest,manager));
    }

    @Test
    public void logInUser_GivenInvalidPassword_ThrowBadCredentialsException(){
        AuthenticationManager manager = Mockito.mock(AuthenticationManager.class);
        String username="login";
        String password="AAAAAA";
        LoginRequest loginRequest = new LoginRequest(username,password);
        when(manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(),loginRequest.getPassword())))
                .thenThrow(new BadCredentialsException("test"));
        assertThrows(BadCredentialsException.class,()->userService.logInUser(loginRequest,manager));
    }

    @Test
    public void checkIfLoginIsTaken_GivenExistingLogin_GetTrue(){
        String login ="login";
        when(userRepository.existsByLogin(login)).thenReturn(true);
        assertTrue(userService.checkIfLoginIsAlreadyTaken(login));
    }

    @Test
    public void checkIfLoginIsTaken_GivenNotExistingLogin_GetFalse(){
        String login ="login2";
        when(userRepository.existsByLogin(login)).thenReturn(false);
        assertFalse(userService.checkIfLoginIsAlreadyTaken(login));
    }

    @Test
    public void checkIfEmailIsTaken_GivenExistingEmail_GetTrue(){
        String email ="login@email.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);
        assertTrue(userService.checkIfEmailIsAlreadyTaken(email));
    }

    @Test
    public void checkIfLoginIsTaken_GivenNotExistingEmail_GetFalse(){
        String email ="login2@email.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        assertFalse(userService.checkIfEmailIsAlreadyTaken(email));
    }

    @Test
    public void createUser_GivenValidData_GetResponseWithToken(){
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        RegisterRequest registerRequest = new RegisterRequest(user1.getLogin(),user1.getEmail(),user1.getPassword(),user1.getPassword());
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByLogin(registerRequest.getLogin())).thenReturn(false);
        when(roleRepository.findByRoleName(RoleName.USER)).thenReturn(roleUser);
        when(settingRepository.save(any())).thenReturn(setting1);
        when(tokenCreator.createUserToken(registerRequest.getLogin())).thenReturn(token);
        ResponseWithToken response = userService.createUser(registerRequest,encoder);
        assertAll(
                ()->assertEquals(user1.getLogin(),response.getUserDto().getLogin()),
                ()->assertEquals(token,response.getToken())
        );
    }

    @Test
    public void createUser_GivenAlreadyExistingEmail_ThrowIllegalArgumentException(){
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        RegisterRequest registerRequest = new RegisterRequest(user1.getLogin(),user1.getEmail(),user1.getPassword(),user1.getPassword());
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);
        when(userRepository.existsByLogin(registerRequest.getLogin())).thenReturn(false);
        assertThrows(IllegalArgumentException.class,()->userService.createUser(registerRequest,encoder));
    }

    @Test
    public void createUser_GivenAlreadyExistingLogin_ThrowIllegalArgumentException(){
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        RegisterRequest registerRequest = new RegisterRequest(user1.getLogin(),user1.getEmail(),user1.getPassword(),user1.getPassword());
        when(userRepository.existsByLogin(registerRequest.getLogin())).thenReturn(true);
        assertThrows(IllegalArgumentException.class,()->userService.createUser(registerRequest,encoder));
    }

    @Test
    public void validateEmailWithCode_GivenValidCode_UserEmailsIsConfirmed(){
        String code ="12345";
        user1.setIsEmailConfirmed(false);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(validationCodeRepository.findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user1,Purpose.EMAIL_VERIFICATION))
                .thenReturn(Optional.of(validationCode1));
        userService.validateEmailWithCode(code);
        assertAll(
                ()->assertEquals(true,user1.getIsEmailConfirmed()),
                ()->assertEquals(true,validationCode1.isWasUsed())
        );
    }

    @Test
    public void validateEmailWithCode_GivenInvalidCode_ThrowAuthenticationFailedException(){
        String code ="12344";
        user1.setIsEmailConfirmed(false);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(validationCodeRepository.findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user1,Purpose.EMAIL_VERIFICATION))
                .thenReturn(Optional.of(validationCode1));
        assertThatThrownBy(()->userService.validateEmailWithCode(code))
                .isInstanceOf(AuthenticationFailedException.class)
                .hasMessage("Podano zły kod dla użytkownika o loginie: "+user1.getLogin());
    }

    @Test
    public void validateEmailWithCode_GivenCodeNotMeetingCriteria_ThrowResourceNotFoundException(){
        String code ="12345";
        validationCode1.setWasUsed(true);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(validationCodeRepository.findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user1,Purpose.EMAIL_VERIFICATION))
                .thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class,
                ()->userService.validateEmailWithCode(code));
    }

    @Test
    public void changeUserPassword_GivenValidPasswordRequest_ChangeUserPassword(){
        String newPassword = "New password";
        NewPasswordRequest passwordRequest = new NewPasswordRequest(user1.getPassword(),newPassword);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        AuthenticationManager manager = Mockito.mock(AuthenticationManager.class);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(encoder.encode(passwordRequest.getNewPassword())).thenReturn(passwordRequest.getNewPassword());
        userService.changeUserPassword(passwordRequest,encoder,manager);
        assertEquals(newPassword,user1.getPassword());
    }

    @Test
    public void changeUserPassword_GivenNewPasswordThatEqualsOld_ThrowIllegalArgumentException(){
        String newPassword="pass";
        NewPasswordRequest passwordRequest = new NewPasswordRequest(user1.getPassword(),newPassword);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        AuthenticationManager manager = Mockito.mock(AuthenticationManager.class);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        assertThrows(IllegalArgumentException.class,
                ()->userService.changeUserPassword(passwordRequest,encoder,manager));
    }

    @Test
    public void changeUserLogin_GivenNewLogin_GetResponseWithToken(){
        String newLogin="login2";
        NewLoginRequest loginRequest = new NewLoginRequest(newLogin);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(settingRepository.findSettingByUserIdUser(user1.getIdUser())).thenReturn(Optional.of(setting1));
        when(tokenCreator.createUserToken(newLogin)).thenReturn(token);
        ResponseWithToken response = userService.changeUserLogin(loginRequest);
        assertAll(
                ()->assertEquals(newLogin,response.getUserDto().getLogin()),
                ()->assertEquals(token,response.getToken()),
                ()->assertEquals(setting1.getIdSetting(),response.getSettingDto().getIdSetting())
        );
    }

    @Test
    public void changeUserLogin_GivenNewLoginEqualsOld_ThrowIllegalArgumentException(){
        String newLogin="login";
        NewLoginRequest loginRequest = new NewLoginRequest(newLogin);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        assertThrows(IllegalArgumentException.class,
                ()->userService.changeUserLogin(loginRequest));
    }

    @Test
    public void changeUserLogin_GivenNewLoginEqualsOld_ThrowResourceNotFoundException(){
        String newLogin="login2";
        NewLoginRequest loginRequest = new NewLoginRequest(newLogin);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(settingRepository.findSettingByUserIdUser(user1.getIdUser())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class,
                ()->userService.changeUserLogin(loginRequest));
    }

    @Test
    public void changeUserEmail_GivenNewValidEmail_GetUserDto(){
        String newEmail = "email@email.com";
        EmailRequest emailRequest = new EmailRequest(newEmail);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        UserDto userDto = userService.changeUserEmail(emailRequest);
        assertAll(
                ()->assertEquals(newEmail,userDto.getEmail())
        );
    }

    @Test
    public void changeUserEmail_GivenNewEmailEqualsOld_ThrowIllegalArgumentException(){
        String newEmail = user1.getEmail();
        EmailRequest emailRequest = new EmailRequest(newEmail);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        assertThrows(IllegalArgumentException.class,()->userService.changeUserEmail(emailRequest));
    }

    @Test
    public void changeUserSetting_GivenValidSettingDto_ChangeSetting(){
        SettingDto settingDto = new SettingDto(setting1.getIdSetting(),FontSize.SMALL,Theme.CONTRAST);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(settingRepository.findSettingByUserIdUser(user1.getIdUser())).thenReturn(Optional.of(setting1));
        SettingDto settingDto1 = userService.changeUserSetting(settingDto);
        assertAll(
                ()->assertEquals(setting1.getIdSetting(),settingDto1.getIdSetting()),
                ()->assertEquals(settingDto.getFontSize(),setting1.getFontSize())
        );
    }

    @Test
    public void changeUserSetting_SettingNotFound_ThrowResourceNotFoundException(){
        SettingDto settingDto = new SettingDto(setting1.getIdSetting(),FontSize.SMALL,Theme.CONTRAST);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(settingRepository.findSettingByUserIdUser(user1.getIdUser())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class,()->userService.changeUserSetting(settingDto));
    }

    @Test
    public void sendEmailWithPasswordRecovery_GivenValidEmailRequest_GetNewValidationCode(){
        EmailRequest emailRequest = new EmailRequest(user1.getEmail());
        when(userRepository.findUsersByEmail(emailRequest.getEmail())).thenReturn(Optional.of(user1));
        userService.sendEmailForPasswordRecovery(emailRequest);
        verify(validationCodeRepository,times(1)).save(any(ValidationCode.class));
    }

    @Test
    public void sendEmailWithPasswordRecovery_GivenNotExistingEmail_ThrowResourceNotFoundException(){
        EmailRequest emailRequest = new EmailRequest("email");
        when(userRepository.findUsersByEmail(emailRequest.getEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class,()->userService.sendEmailForPasswordRecovery(emailRequest));
    }

    @Test
    public void createNewPassword_GivenValidEmailAndRequest_GetNewUserPassword(){
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        String email = user1.getEmail();
        String code = "54321";
        String newPassword = "new password";
        CodeRequest codeRequest = new CodeRequest(code);
        when(userRepository.findUsersByEmail(email)).thenReturn(Optional.of(user1));
        when(validationCodeRepository.
                findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user1,Purpose.PASSWORD_REMINDER))
                .thenReturn(Optional.of(validationCode2));
        when(stringGenerator.generatePassword()).thenReturn(newPassword);
        when(encoder.encode(newPassword)).thenReturn(newPassword);
        ResponseWithPassword response = userService.createNewPassword(email,codeRequest,encoder);
        assertEquals(newPassword,user1.getPassword());
        assertEquals(newPassword,response.getNewPassword());
    }

    @Test
    public void createNewPassword_GivenInvalidCode_ThrowAuthenticationFailedException(){
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        String email = user1.getEmail();
        String code = "54322";
        CodeRequest codeRequest = new CodeRequest(code);
        when(userRepository.findUsersByEmail(email)).thenReturn(Optional.of(user1));
        when(validationCodeRepository.
                findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user1,Purpose.PASSWORD_REMINDER))
                .thenReturn(Optional.of(validationCode2));
        assertThrows(AuthenticationFailedException.class,
                ()->userService.createNewPassword(email,codeRequest,encoder));
    }

    @Test
    public void createNewPassword_GivenInvalidCode_ThrowResourceNotFoundException(){
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        String email = "email@email.pl";
        String code = "54321";
        String newPassword = "new password";
        CodeRequest codeRequest = new CodeRequest(code);
        when(userRepository.findUsersByEmail(email)).thenReturn(Optional.ofNullable(user1));
        when(validationCodeRepository.
                findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user1,Purpose.PASSWORD_REMINDER))
                .thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class,
                ()->userService.createNewPassword(email,codeRequest,encoder));
    }

    @Test
    public void deleteUserAccount_GivenValidUser_UserIsNotActive(){
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        userService.deleteUserAccount();
        assertEquals(false,user1.getIsActive());
    }

    @Test
    public void getAllUsers_GivenUser_GetUsersList(){
        User user2 = new User
                (2L,"login2","email2@email.com",
                        "password",true,true,roles);
        User user3 = new User(
                3L,"login3","email3@email.com",
                "password",true,true,roles);
        List<User>users = List.of(user2,user3);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(userRepository.findUsersByLoginIsNotLike(user1.getLogin())).thenReturn(users);
        List<UserDto> userDtos = userService.getAllUsers();
        assertEquals(users.size(),userDtos.size());
    }

    @Test
    public void deleteUserById_GivenValidId_SetUserInActive(){
        Long id = user1.getIdUser();
        when(userRepository.findUserByIdUser(id)).thenReturn(Optional.of(user1));
        userService.deleteUserById(id);
        assertFalse(user1.getIsActive());
    }

    @Test
    public void deleteUserById_GivenInvalidId_ThrowUserNotFoundException(){
        Long id = 4L;
        when(userRepository.findUserByIdUser(id)).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->userService.deleteUserById(id));
    }

    @Test
    public void getUserById_GivenInvalidId_ThrowUserNotFoundException(){
        Long id = 2L;
        when(userRepository.findUserByIdUser(id)).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->userService.getUserById(id));
    }

}
