package com.example.brainutrain.service;

import com.example.brainutrain.config.security.UserDetailsImpl;
import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.constants.Theme;
import com.example.brainutrain.dto.RegisterDto;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.Setting;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.RoleRepository;
import com.example.brainutrain.repository.SettingRepository;
import com.example.brainutrain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SettingRepository settingRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username).orElseThrow(()->new UsernameNotFoundException("User not found for:"+username));
        return new UserDetailsImpl(user);
    }

    public boolean checkIfLoginIsAlreadyTaken(String login){
        return userRepository.existsByLogin(login);
    }

    public boolean checkIfEmailIsAlreadyTaken(String email){
        return userRepository.existsByEmail(email);
    }

    public User createUser(RegisterDto registerDto){
        User newUser=userFromDto(registerDto);
        logger.info("created user"+newUser);
        Role userRole = roleRepository.findByRoleName(RoleName.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);
        newUser.setIsEmailConfirmed(false);
        newUser.setSetting(settingRepository.save(createUserSettings()));
        return userRepository.save(newUser);
    }

    private Setting createUserSettings(){
        return new Setting(FontSize.MEDIUM, Theme.DAY);
    }

    private User userFromDto(RegisterDto registerDto){
        User user = new User();
        user.setLogin(registerDto.getLogin());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        return user;
    }
}
