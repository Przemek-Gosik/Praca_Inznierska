package com.example.brainutrain.service;

import com.example.brainutrain.config.security.UserDetailsImpl;
import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.constants.Theme;
import com.example.brainutrain.dto.RegisterDto;
import com.example.brainutrain.dto.UserDto;
import com.example.brainutrain.exception.AuthenticationFailedException;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final EmailService emailService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username).orElseThrow(()->new UsernameNotFoundException("User not found for:"+username));
        return new UserDetailsImpl(user);
    }
    @Transactional
    public UserDto findByLogin(String login) throws UsernameNotFoundException{
        User user = userRepository.findUserByLogin(login).orElseThrow(()-> new UsernameNotFoundException("User not found for:"+login));
        return UserMapper.INSTANCE.toDto(user);
    }

    public boolean checkIfLoginIsAlreadyTaken(String login){
        return userRepository.existsByLogin(login);
    }

    public boolean checkIfEmailIsAlreadyTaken(String email){
        return userRepository.existsByEmail(email);
    }

    public UserDto createUser(RegisterDto registerDto){
        User newUser=UserMapper.INSTANCE.fromDto(registerDto);
        log.info("created user"+newUser.getLogin());
        Role userRole = createUserRoleIfNotExist();
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);
        newUser.setIsEmailConfirmed(false);
        newUser.setSetting(settingRepository.save(createUserSettings()));
        String code=generateCode(userRepository.save(newUser));
        emailService.sendEmailWithCode(code, newUser.getEmail(), newUser.getLogin());
        return UserMapper.INSTANCE.toDto(newUser);
    }

    private Setting createUserSettings(){
        return new Setting(FontSize.MEDIUM, Theme.DAY);
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
                .orElseThrow(()->new UsernameNotFoundException("User not found for username:"+userName));
        validationCodeRepository.findValidationCodeByCodeAndUser(code,user).orElseThrow(()->new AuthenticationFailedException("Wrong code provided!"));
        user.setIsEmailConfirmed(true);
        log.info("Email for user "+userName+" confirmed");
        userRepository.save(user);
    }

}
