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
        User user = userRepository.findUserByLogin(username).orElseThrow(()->new UsernameNotFoundException("User not found for:"+username));
        return new UserDetailsImpl(user);
    }
    public ResponseWithToken logInUser(LoginDto loginDto, AuthenticationManager authenticationManager) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUserName(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String userName = loginDto.getUserName();
        User user = userRepository.findUserByLogin(userName).orElseThrow(
                ()-> new UsernameNotFoundException("User not found for:"+userName));
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
        User user = userRepository.findUserByLogin(userName)
                .orElseThrow(()->new UsernameNotFoundException("User not found for username: "+userName));
        validationCodeRepository.findValidationCodeByCodeAndUser(code,user).orElseThrow(
                ()->new AuthenticationFailedException("Wrong code provided for user: "+user.getIdUser()));
        user.setIsEmailConfirmed(true);
        log.info("Email for user "+userName+" confirmed");
        userRepository.save(user);
    }


}
