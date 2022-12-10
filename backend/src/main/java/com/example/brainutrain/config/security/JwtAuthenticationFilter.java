package com.example.brainutrain.config.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.brainutrain.exception.AuthenticationFailedException;
import com.example.brainutrain.exception.message.ErrorMessage;
import com.example.brainutrain.utils.TokenCreator;
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
    private final TokenCreator tokenCreator;
    private static final String HEADER="Authorization";
    private static final String PREFIX="Bearer";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, TokenCreator tokenCreator) {
        super(authenticationManager);
        this.userService=userService;
        this.tokenCreator = tokenCreator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException{
        try {
            UsernamePasswordAuthenticationToken passwordAuthenticationToken = authenticateUser(httpServletRequest);
            SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }catch(Exception exception){
            int statusCode;
            if( exception instanceof IOException){
                logger.info(exception.getClass());
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            }else {
                logger.info(exception.getClass());
                statusCode = HttpStatus.UNAUTHORIZED.value();
            }
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(statusCode);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            httpServletResponse.getWriter().print(objectMapper.writeValueAsString(
                    new ErrorMessage(
                    statusCode, LocalDateTime.now(),
                    exception.getMessage(),httpServletRequest.getPathInfo())));
        }
    }


    private UsernamePasswordAuthenticationToken authenticateUser(HttpServletRequest request) throws AuthenticationException, TokenExpiredException {
        String token = request.getHeader(HEADER);
        if(token !=null && token.startsWith(PREFIX)){
            String login = tokenCreator.getLoginFromToken(token);
            if(login!=null){
                UserDetailsImpl userDetails =(UserDetailsImpl) userService.loadUserByUsername(login);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                if(!userDetails.isEnabled()){
                    usernamePasswordAuthenticationToken.setAuthenticated(false);
                }
                return  usernamePasswordAuthenticationToken;
            }
        }
            throw new AuthenticationFailedException("Nie podano tokena, albo podano bez odpowiedniego prefiksa dla żądania : " +
                    ""+request.getRequestURI());
    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/api/auth/register")  ||
                request.getRequestURI().equals("/api/auth/login")  ||
                request.getRequestURI().contains("/api/auth/emailIsTaken/")  ||
                request.getRequestURI().contains("/api/auth/loginIsTaken/")  ||
                request.getRequestURI().contains("/api/auth/passwordRecovery/") ||
                request.getRequestURI().contains("/api/fast_reading/text/guest") ||
                request.getRequestURI().equals("/docs/swagger-ui/index.html");
    }

}
