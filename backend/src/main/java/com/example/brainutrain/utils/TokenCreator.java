package com.example.brainutrain.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class TokenCreator {

    private static final String PREFIX="Bearer";

    @Value("${jwt.expirationTime}")
    private Long expirationTime;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String createUserToken(String userName){
        return JWT.create()
                .withSubject(userName)
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

