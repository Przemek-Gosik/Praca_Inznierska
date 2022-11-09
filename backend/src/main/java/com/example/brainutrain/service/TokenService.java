package com.example.brainutrain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.brainutrain.config.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class TokenService {

    private static final String PREFIX="Bearer";

    @Value("${jwt.expirationTime}")
    private Long expirationTime;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String createUserToken(Authentication authentication){
        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
                .sign(Algorithm.HMAC512(jwtSecret));
    }

    public  String getLoginFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token.replace(PREFIX, ""))
                .getSubject();
    }
}

