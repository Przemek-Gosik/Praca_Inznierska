package com.example.brainutrain.config.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.exception.ErrorMessage;
import com.example.brainutrain.service.TokenService;
import com.example.brainutrain.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final UserService userService;
    private final TokenService tokenService;
    private static final String HEADER="Authorization";
    private static final String PREFIX="Bearer";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,UserService userService,TokenService tokenService) {
        super(authenticationManager);
        this.userService=userService;
        this.tokenService=tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException{
        try {
            UsernamePasswordAuthenticationToken passwordAuthenticationToken = authenticateUser(httpServletRequest);
            SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }catch(Exception exception){
            logger.warn(exception.getMessage());
            int statusCode = HttpStatus.FORBIDDEN.value();
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(statusCode);
            ObjectMapper objectMapper = new ObjectMapper();
            httpServletResponse.getWriter().print(objectMapper.writeValueAsString(
                    new ErrorMessage(
                    statusCode, LocalDateTime.now(),
                    exception.getMessage(),httpServletRequest.getPathInfo())));

        }
    }

    private UsernamePasswordAuthenticationToken authenticateUser(HttpServletRequest request) throws AuthenticationException, TokenExpiredException {
        String token = request.getHeader(HEADER);
        if(token !=null && token.startsWith(PREFIX)){
            String login = tokenService.getLoginFromToken(token);
            if(login!=null){
                UserDetailsImpl userDetails =(UserDetailsImpl) userService.loadUserByUsername(login);
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
            }
        }
            throw new AuthenticationFailedException("No token provided or token is without right prefix");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/api/auth/register")  ||
                request.getRequestURI().equals("/api/auth/login")  ||
                request.getRequestURI().equals("/api//emailIsTaken")  ||
                request.getRequestURI().equals("/api/auth/loginIsTaken")  ||
                request.getRequestURI().equals("/docs/swagger-ui/index.html");
    }

}
