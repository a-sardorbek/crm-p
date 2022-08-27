package com.system.crm.filter;

import com.system.crm.constant.SecurityConstant;
import com.system.crm.jwt.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD)){
            response.setStatus(HttpStatus.OK.value());
        }else{
            String authorizationRequestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationRequestHeader == null || !authorizationRequestHeader.startsWith(SecurityConstant.TOKEN_PREFIX)){
                filterChain.doFilter(request,response);
                return;
            }
            String token = authorizationRequestHeader.substring(SecurityConstant.TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubjectFromToken(token);
            if(jwtTokenProvider.isValidToken(username,token)){
                List<GrantedAuthority> authorities =jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username,authorities,request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request,response);

    }
}
