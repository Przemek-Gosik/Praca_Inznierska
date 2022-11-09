package com.example.brainutrain.config.security;

import com.example.brainutrain.service.TokenService;
import com.example.brainutrain.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException{
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = authenticateUser(httpServletRequest);
        if(passwordAuthenticationToken == null){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        try {
            SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
        }catch (AuthenticationException e){
            logger.warn(e.getMessage());
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private UsernamePasswordAuthenticationToken authenticateUser(HttpServletRequest request){
        String token = request.getHeader(HEADER);
        if(token !=null && token.startsWith(PREFIX)){
            String login = tokenService.getLoginFromToken(token);
            if(login!=null){
                UserDetailsImpl userDetails =(UserDetailsImpl) userService.loadUserByUsername(login);
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
            }
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/register") ||
                request.getRequestURI().equals("/login") ||
                request.getRequestURI().equals("/docs/swagger-ui/index.html");
    }


}
