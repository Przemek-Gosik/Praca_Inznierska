package com.example.brainutrain.service;

import com.example.brainutrain.config.security.UserDetailsImpl;
import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Purpose;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.constants.Theme;
import com.example.brainutrain.dto.LoginDto;
import com.example.brainutrain.dto.RegisterDto;
import com.example.brainutrain.dto.request.CodeRequest;
import com.example.brainutrain.dto.request.EmailRequest;
import com.example.brainutrain.dto.response.ResponseWithPassword;
import com.example.brainutrain.dto.response.ResponseWithToken;
import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.dto.request.NewEmailRequest;
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
import java.util.Random;
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

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUser(username);
        return new UserDetailsImpl(user);
    }
    private User findUser(String username)throws UsernameNotFoundException{
        return userRepository.findUserByLogin(username).orElseThrow(()->new UsernameNotFoundException("User not found for:"+username));
    }
    public ResponseWithToken logInUser(LoginDto loginDto, AuthenticationManager authenticationManager) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUserName(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String userName = loginDto.getUserName();
        User user = findUser(userName);
        Setting setting = settingRepository.findSettingByUserIdUser(user.getIdUser()).orElseThrow(
                ()->new ResourceNotFoundException("Settings not found for user "+user.getIdUser()));
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

    public ResponseWithToken createUser(RegisterDto registerDto,PasswordEncoder passwordEncoder){
        if(checkIfLoginIsAlreadyTaken(registerDto.getLogin())){
            throw new IllegalArgumentException("Login already taken for: "+registerDto.getLogin());
        }
        if(checkIfEmailIsAlreadyTaken(registerDto.getEmail())){
            throw new IllegalArgumentException("Email already taken for: "+registerDto.getEmail());
        }
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        User newUser=UserMapper.INSTANCE.fromDto(registerDto);
        Role userRole = getUserRoleOrCreateIfNotExist();
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);
        newUser.setIsEmailConfirmed(false);
        newUser.setIsActive(true);
        userRepository.save(newUser);
        log.info("created user"+newUser.getLogin());
        ValidationCode validationCode = new ValidationCode(Purpose.EMAIL_VERIFICATION,newUser);
        validationCode.setCode(generateCode());
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

    private String generateCode(){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<5;i++){
            int randNumber=random.nextInt(10);
            stringBuilder.append(randNumber);
        }
        return stringBuilder.toString();
    }

    public void validateEmailWithCode(String code){
        User user = authenticationUtils.getUserFromAuthentication();
        ValidationCode validationCode =validationCodeRepository.
                findValidationCodeByUserAndPurposeAndWasUsedIsFalse(user,Purpose.EMAIL_VERIFICATION).
                orElseThrow(()->new ResourceNotFoundException("No code for user by id: "+user.getIdUser())
        );
        if(validationCode.getCode().equals(code)) {
            user.setIsEmailConfirmed(true);
            log.info("Email for user " + user.getIdUser() + " confirmed");
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
            throw new IllegalArgumentException("New password can not be the same as old for user:"+user.getIdUser());
        }
        user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPassword()));
        userRepository.save(user);
        log.info("New password set for user "+user.getIdUser());
    }

    public ResponseWithToken changeUserLogin(NewLoginRequest newLoginRequest){
        User user = authenticationUtils.getUserFromAuthentication();
        if(newLoginRequest.getNewLogin().equals(newLoginRequest.getOldLogin())){
            throw new IllegalArgumentException("New login can not be the same as old for user:"+user.getIdUser());
        }
        user.setLogin(newLoginRequest.getNewLogin());
        userRepository.save(user);
        Setting setting = settingRepository.findSettingByUserIdUser(user.getIdUser()).orElseThrow(
                ()-> new ResourceNotFoundException("Settings not found for user:"+user.getIdUser())
        );
        String token = tokenCreator.createUserToken(user.getLogin());
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        SettingDto settingDto = SettingMapper.INSTANCE.toDto(setting);
        return new ResponseWithToken(userDto,settingDto,token);
    }

    public UserDto changeUserEmail(NewEmailRequest newEmailRequest){
        User user = authenticationUtils.getUserFromAuthentication();
        if(newEmailRequest.getNewEmail().equals(newEmailRequest.getOldEmail())){
            throw new IllegalArgumentException("New email address can not be the same as old for user: "+user.getIdUser());
        }
        user.setEmail(newEmailRequest.getNewEmail());
        user.setIsEmailConfirmed(false);
        userRepository.save(user);
        ValidationCode validationCode = new ValidationCode(Purpose.EMAIL_VERIFICATION,user);
        validationCode.setCode(generateCode());
        validationCodeRepository.save(validationCode);
        emailSender.sendEmailWithCode(validationCode.getCode(), user.getEmail(), user.getLogin());
        return UserMapper.INSTANCE.toDto(user);
    }

    public SettingDto changeUserSetting(SettingDto settingDto){
        User user = authenticationUtils.getUserFromAuthentication();
        Setting setting = settingRepository.findSettingByUserIdUser(user.getIdUser()).orElseThrow(
                ()->new ResourceNotFoundException("Setting not found for idUser: "+user.getIdUser()));
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
        validationCode.setCode(generateCode());
        emailSender.sendEmailWithCode(validationCode.getCode(),emailRequest.getEmail(),user.getLogin());
        validationCodeRepository.save(validationCode);
    }

    public ResponseWithPassword createNewPassword(String email, CodeRequest codeRequest, PasswordEncoder passwordEncoder){
        User user = userRepository.findUsersByEmail(email).orElseThrow(
                ()->new ResourceNotFoundException("User not found for email: "+email)
        );
        ValidationCode validationCode = validationCodeRepository.findValidationCodeByUserAndPurposeAndWasUsedIsFalse(
                user,Purpose.PASSWORD_REMINDER).orElseThrow(
                ()->new ResourceNotFoundException("Nie znaleziono kodu dla uzytkownika o emailu: "+user.getEmail())
        );
        if(codeRequest.getCode().equals(validationCode.getCode())){
            String newPassword = generatePassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            validationCode.setWasUsed(true);
            log.info("New password set for user by Id: "+user.getIdUser());
            return new ResponseWithPassword(newPassword);
        }else{
            throw new AuthenticationFailedException("Podano zły kod dla użytkownika o emailu: "+user.getEmail());
        }
    }

    private String generatePassword(){
        final int LENGTH_LIMIT=8;
        final int MIN_CHAR = 48;
        final int MAX_CHAR = 122;
        Random random = new Random();
        return random.ints(MIN_CHAR,MAX_CHAR+1)
                .filter(c->(c<=57 || c>=63) && (c<=90 || c>=97))
                .limit(LENGTH_LIMIT)
                .collect(StringBuilder::new,StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    public void deleteUserAccount(){
        User user = authenticationUtils.getUserFromAuthentication();
        user.setIsActive(false);
        userRepository.save(user);
        log.info("User by id "+user.getIdUser()+" deactivated");
    }

    public List<UserDto> getAllUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getPrincipal().toString());
        List<User> users = userRepository.findUsersByLoginIsNotLike(authentication.getPrincipal().toString());
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
                ()-> new UserNotFoundException(id)
        );
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
        user.getRoles().remove(adminRole);
        roleRepository.save(adminRole);
        userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }

}
