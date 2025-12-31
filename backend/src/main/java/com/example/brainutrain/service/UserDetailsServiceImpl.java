package com.example.brainutrain.service;

import com.example.brainutrain.config.security.UserDetailsImpl;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserDetailsService for Spring Security authentication.
 * Separated from UserService to avoid circular dependency with SecurityConfig.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user details by username for Spring Security authentication
     *
     * @param username the login/username to search for
     * @return UserDetails containing user information and authorities
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono uzytkownika dla: " + username));
        return new UserDetailsImpl(user);
    }
}
