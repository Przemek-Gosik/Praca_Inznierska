package com.example.brainutrain.service;

import com.example.brainutrain.config.security.UserDetailsImpl;
import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.constants.Theme;
import com.example.brainutrain.dto.LoginDto;
import com.example.brainutrain.dto.RegisterDto;
import com.example.brainutrain.dto.ResponseWithToken;
import com.example.brainutrain.dto.SettingDto;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.dto.request.NewLoginRequest;
import com.example.brainutrain.dto.request.NewPasswordRequest;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.exception.ResourceNotFoundException;
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
    private final TokenService tokenService;
    private final EmailService emailService;

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
        Setting setting = settingRepository.findByUserIdUser(user.getIdUser()).orElseThrow(
                ()->new ResourceNotFoundException("Settings not found for user "+user.getIdUser()));
        String token = tokenService.createUserToken(userName);
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
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        User newUser=UserMapper.INSTANCE.fromDto(registerDto);
        Role userRole = createUserRoleIfNotExist();
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);
        newUser.setIsEmailConfirmed(false);
        String code=generateCode(userRepository.save(newUser));
        log.info("created user"+newUser.getLogin());
        emailService.sendEmailWithCode(code, newUser.getEmail(), newUser.getLogin());
        String token = tokenService.createUserToken(newUser.getLogin());
        Setting newSetting = createUserSettings(newUser);
        SettingDto settingDto= SettingMapper.INSTANCE.toDto(newSetting);
        UserDto userDto=UserMapper.INSTANCE.toDto(newUser);
        return new ResponseWithToken(userDto,settingDto,token);
    }

    private Setting createUserSettings(User user){
        return settingRepository.save(new Setting(FontSize.MEDIUM, Theme.DAY,user));
    }

    private Role createUserRoleIfNotExist(){
        Role role = roleRepository.findByRoleName(RoleName.USER);
        if(role == null){
            Role newRole = new Role(RoleName.USER);
            return roleRepository.save(newRole);
        }else {
            return role;
        }
    }

    public String generateCode(User user){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<5;i++){
            int randNumber=random.nextInt(10);
            stringBuilder.append(randNumber);
        }
        String code = stringBuilder.toString();
        ValidationCode validationCode = new ValidationCode();
        validationCode.setCode(code);
        validationCode.setUser(user);
        validationCodeRepository.save(validationCode);
        return validationCode.getCode();
    }

    public void validateEmailWithCode(String code){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getPrincipal().toString();
        User user = findUser(userName);
        validationCodeRepository.findValidationCodeByCodeAndUser(code,user).orElseThrow(
                ()->new AuthenticationFailedException("Wrong code provided for user: "+user.getIdUser()));
        user.setIsEmailConfirmed(true);
        log.info("Email for user "+userName+" confirmed");
        userRepository.save(user);
    }

    public void changeUserPassword(NewPasswordRequest newPasswordRequest,PasswordEncoder passwordEncoder
            ,AuthenticationManager authenticationManager){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User user = findUser(authentication.getPrincipal().toString());
        if(!newPasswordRequest.getUserName().equals(user.getLogin())){
            throw new AuthenticationFailedException("No permissions to change password for user:"+newPasswordRequest.getUserName());
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(newPasswordRequest.getUserName(),newPasswordRequest.getOldPassword()));
        if(newPasswordRequest.getOldPassword().equals(newPasswordRequest.getNewPassword())){
            throw new IllegalArgumentException("New password can not be the same as old for user:"+user.getIdUser());
        }
        user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPassword()));
        userRepository.save(user);
        log.info("New password set for user "+user.getIdUser());
    }

    public ResponseWithToken changeUserLogin(NewLoginRequest newLoginRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = findUser(authentication.getPrincipal().toString());
        if(!authentication.getPrincipal().toString().equals(user.getLogin())){
            throw new AuthenticationFailedException("No permissions to change login for user");
        }
        if(newLoginRequest.getNewLogin().equals(newLoginRequest.getOldLogin())){
            throw new IllegalArgumentException("New login can not be the same as old for user:"+user.getIdUser());
        }
        user.setLogin(newLoginRequest.getNewLogin());
        userRepository.save(user);
        Setting setting = settingRepository.findByUserIdUser(user.getIdUser()).orElseThrow(
                ()-> new ResourceNotFoundException("Settings not found for user:"+user.getIdUser())
        );
        String token = tokenService.createUserToken(user.getLogin());
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        SettingDto settingDto = SettingMapper.INSTANCE.toDto(setting);
        return new ResponseWithToken(userDto,settingDto,token);
    }
}
