package com.example.brainutrain.service;

import com.example.brainutrain.config.security.UserDetailsImpl;
import com.example.brainutrain.dto.request.RegisterRequest;
import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Purpose;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.constants.Theme;
import com.example.brainutrain.dto.request.LoginRequest;
import com.example.brainutrain.dto.request.CodeRequest;
import com.example.brainutrain.dto.request.EmailRequest;
import com.example.brainutrain.dto.response.ResponseWithPassword;
import com.example.brainutrain.dto.response.ResponseWithToken;
import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.dto.request.NewLoginRequest;
import com.example.brainutrain.dto.request.NewPasswordRequest;
import com.example.brainutrain.exception.AlreadyExistsException;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.exception.UserNotFoundException;
import com.example.brainutrain.mapper.SettingMapper;
import com.example.brainutrain.mapper.UserMapper;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.Setting;
import com.example.brainutrain.model.User;
import com.example.brainutrain.model.ValidationCode;
import com.example.brainutrain.repository.RoleRepository;
import com.example.brainutrain.repository.SettingRepository;
import com.example.brainutrain.repository.UserRepository;
import com.example.brainutrain.repository.ValidationCodeRepository;
import com.example.brainutrain.utils.EmailSender;
import com.example.brainutrain.utils.StringGenerator;
import com.example.brainutrain.utils.TokenCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SettingRepository settingRepository;
    private final ValidationCodeRepository validationCodeRepository;
    private final TokenCreator tokenCreator;
    private final EmailSender emailSender;
    private final AuthenticationUtils authenticationUtils;
    private final StringGenerator stringGenerator;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUser(username);
        return new UserDetailsImpl(user);
    }
    private User findUser(String username)throws UsernameNotFoundException{
        return userRepository.findUserByLogin(username).orElseThrow(()->new UsernameNotFoundException("Nie znaleziono użytkownika dla: "+username));
    }
    public ResponseWithToken logInUser(LoginRequest loginRequest, AuthenticationManager authenticationManager) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String userName = loginRequest.getUserName();
        User user = findUser(userName);
        Setting setting = settingRepository.findSettingByUserIdUser(user.getIdUser()).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono ustawień dla użytkownika o id: "+user.getIdUser()));
        String token = tokenCreator.createUserToken(userName);
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        SettingDto settingDto = SettingMapper.INSTANCE.toDto(setting);
        return new ResponseWithToken(userDto,settingDto,token);
    }

    public boolean checkIfLoginIsAlreadyTaken(String login){
        return userRepository.existsByLogin(login);
    }

    public boolean checkIfEmailIsAlreadyTaken(String email){
        return userRepository.existsByEmail(email);
    }

    public ResponseWithToken createUser(RegisterRequest registerRequest, PasswordEncoder passwordEncoder){
        if(checkIfLoginIsAlreadyTaken(registerRequest.getLogin())){
            throw new IllegalArgumentException("Login zajęty dla: "+ registerRequest.getLogin());
        }
        if(checkIfEmailIsAlreadyTaken(registerRequest.getEmail())){
            throw new IllegalArgumentException("Adres email zajęty dla: "+ registerRequest.getEmail());
        }
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User newUser=UserMapper.INSTANCE.fromDto(registerRequest);
        Role userRole = getUserRoleOrCreateIfNotExist();
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);
        newUser.setIsEmailConfirmed(false);
        newUser.setIsActive(true);
        userRepository.save(newUser);
        log.info("Utworzono nowego użytkownika "+newUser.getLogin());
        ValidationCode validationCode = new ValidationCode(Purpose.EMAIL_VERIFICATION,newUser);
        validationCode.setCode(stringGenerator.generateCode());
        validationCodeRepository.save(validationCode);
        emailSender.sendEmailWithCode(validationCode.getCode(), newUser.getEmail(), newUser.getLogin());
        String token = tokenCreator.createUserToken(newUser.getLogin());
        Setting newSetting = createUserSettings(newUser);
        SettingDto settingDto= SettingMapper.INSTANCE.toDto(newSetting);
        UserDto userDto=UserMapper.INSTANCE.toDto(newUser);
        return new ResponseWithToken(userDto,settingDto,token);
    }

    private Setting createUserSettings(User user){
        return settingRepository.save(new Setting(FontSize.MEDIUM, Theme.DAY,user));
    }

    private Role getUserRoleOrCreateIfNotExist(){
        Role role = roleRepository.findByRoleName(RoleName.USER);
        if(role == null){
            Role newRole = new Role(RoleName.USER);
            return roleRepository.save(newRole);
        }else {
            return role;
        }
    }

    public void validateEmailWithCode(String code){
        User user = authenticationUtils.getUserFromAuthentication();
        ValidationCode validationCode =validationCodeRepository.
                findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user,Purpose.EMAIL_VERIFICATION).
                orElseThrow(()->new ResourceNotFoundException("Nie odnaleziono kodu dla użytkownika o id: "+user.getIdUser())
        );
        if(validationCode.getCode().equals(code)) {
            user.setIsEmailConfirmed(true);
            log.info("Email dla użytkownika o id " + user.getIdUser() + " zweryfikowany");
            userRepository.save(user);
            validationCode.setWasUsed(true);
            validationCodeRepository.save(validationCode);
        }else {
            throw new AuthenticationFailedException("Podano zły kod dla użytkownika o loginie: "+user.getLogin());
        }
    }

    public void changeUserPassword(NewPasswordRequest newPasswordRequest,PasswordEncoder passwordEncoder
            ,AuthenticationManager authenticationManager){
        User user = authenticationUtils.getUserFromAuthentication();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(),newPasswordRequest.getOldPassword()));
        if(newPasswordRequest.getOldPassword().equals(newPasswordRequest.getNewPassword())){
            throw new IllegalArgumentException("Nowe hasło nie może być takie same jak stare dla użytkownika o id: "+user.getIdUser());
        }
        user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPassword()));
        userRepository.save(user);
        log.info("Nowe hasło dla użytkownika o id: "+user.getIdUser());
    }

    public ResponseWithToken changeUserLogin(NewLoginRequest newLoginRequest){
        User user = authenticationUtils.getUserFromAuthentication();
        if(newLoginRequest.getNewLogin().equals(user.getLogin())){
            throw new IllegalArgumentException("Nowy login nie może być taki sam jak stary:"+user.getIdUser());
        }
        user.setLogin(newLoginRequest.getNewLogin());
        userRepository.save(user);
        Setting setting = settingRepository.findSettingByUserIdUser(user.getIdUser()).orElseThrow(
                ()-> new ResourceNotFoundException("Nie odnaleziono ustawień dla użytkownika o id: "+user.getIdUser())
        );
        String token = tokenCreator.createUserToken(user.getLogin());
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        SettingDto settingDto = SettingMapper.INSTANCE.toDto(setting);
        return new ResponseWithToken(userDto,settingDto,token);
    }

    public UserDto changeUserEmail(EmailRequest emailRequest){
        User user = authenticationUtils.getUserFromAuthentication();
        if(emailRequest.getEmail().equals(user.getEmail())){
            throw new IllegalArgumentException("Nowy adres email nie może być taki sam jak stary dla użytkownika o id: "+user.getIdUser());
        }
        user.setEmail(emailRequest.getEmail());
        user.setIsEmailConfirmed(false);
        userRepository.save(user);
        ValidationCode validationCode = new ValidationCode(Purpose.EMAIL_VERIFICATION,user);
        validationCode.setCode(stringGenerator.generateCode());
        validationCodeRepository.save(validationCode);
        emailSender.sendEmailWithCode(validationCode.getCode(), user.getEmail(), user.getLogin());
        return UserMapper.INSTANCE.toDto(user);
    }

    public SettingDto changeUserSetting(SettingDto settingDto){
        User user = authenticationUtils.getUserFromAuthentication();
        Setting setting = settingRepository.findSettingByUserIdUser(user.getIdUser()).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono ustawień dla użytkownika o id: "+user.getIdUser()));
        setting.setFontSize(settingDto.getFontSize());
        setting.setTheme(settingDto.getTheme());
        settingRepository.save(setting);
        return SettingMapper.INSTANCE.toDto(setting);
    }
    public void sendEmailForPasswordRecovery(EmailRequest emailRequest){
        User user = userRepository.findUsersByEmail(emailRequest.getEmail()).orElseThrow(
                ()->new ResourceNotFoundException("Nie znaleziono konta dla podanego maila: "+ emailRequest.getEmail())
        );
        ValidationCode validationCode = new ValidationCode(Purpose.PASSWORD_REMINDER,user);
        validationCode.setCode(stringGenerator.generateCode());
        emailSender.sendEmailWithCode(validationCode.getCode(),emailRequest.getEmail(),user.getLogin());
        validationCodeRepository.save(validationCode);
    }

    public ResponseWithPassword createNewPassword(String email, CodeRequest codeRequest, PasswordEncoder passwordEncoder){
        User user = userRepository.findUsersByEmail(email).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono użytkownika dla emaila: "+email)
        );
        ValidationCode validationCode = validationCodeRepository.findValidationCodeByUserAndPurposeAndWasUsedIsFalse(
                user,Purpose.PASSWORD_REMINDER).orElseThrow(
                ()->new ResourceNotFoundException("Nie znaleziono kodu dla uzytkownika o emailu: "+user.getEmail())
        );
        if(codeRequest.getCode().equals(validationCode.getCode())){
            String newPassword = stringGenerator.generatePassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            validationCode.setWasUsed(true);
            log.info("Ustawiono hasło dla użytkownika o id: "+user.getIdUser());
            return new ResponseWithPassword(newPassword);
        }else{
            throw new AuthenticationFailedException("Podano zły kod dla użytkownika o emailu: "+user.getEmail());
        }
    }

    public void deleteUserAccount(){
        User user = authenticationUtils.getUserFromAuthentication();
        user.setIsActive(false);
        userRepository.save(user);
        log.info("Użytkownik o id "+user.getIdUser()+" dezaktywowany");
    }

    public List<UserDto> getAllUsers(){
        User user = authenticationUtils.getUserFromAuthentication();
        List<User> users = userRepository.findUsersByLoginIsNotLike(user.getLogin());
        return UserMapper.INSTANCE.toDto(users);
    }

    public UserDto getUserById(Long id){
        User user = userRepository.findUserByIdUser(id).orElseThrow(
                ()->new UserNotFoundException(id)
        );
        return UserMapper.INSTANCE.toDto(user);
    }
    public void deleteUserById(Long id){
        User user = userRepository.findUserByIdUser(id).orElseThrow(
                ()-> new UserNotFoundException(id));
        user.setIsActive(false);
        userRepository.save(user);
        log.info("User by id "+id+" deactivated");
    }

    public UserDto giveUserAdminRole(Long id){
        User user = userRepository.findUserByIdUser(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
        Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN);
        if(user.getRoles().contains(adminRole)){
            throw new AlreadyExistsException("Użytkownik o id:"+id+"posiada już role administratora");
        }
        user.getRoles().add(adminRole);
        roleRepository.save(adminRole);
        userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    public UserDto takeUserAdminRole(Long id){
        User user = userRepository.findUserByIdUser(id).orElseThrow(
                ()-> new UserNotFoundException(id)
        );
        Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN);
        if(!user.getRoles().contains(adminRole)){
            throw new IllegalArgumentException("Użytkownik nie posiada już uprawnień admina");
        }
        user.getRoles().remove(adminRole);
        roleRepository.save(adminRole);
        userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }

}
