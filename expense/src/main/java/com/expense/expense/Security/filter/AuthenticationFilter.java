package com.expense.expense.Security.filter;

import java.io.IOException;
import java.util.Collection;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.expense.expense.Security.CustomPrincipal;
import com.expense.expense.entity.UserEntity;
import com.expense.expense.repository.UserRepository;
import com.expense.expense.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;
    private UserRepository userRepository;

    public AuthenticationFilter(JwtUtils jwtUtils, UserRepository userRepository){
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(jwtToken != null){
            jwtToken = jwtToken.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            String username = jwtUtils.extractUsername(decodedJWT);
            String stringAuthorities = jwtUtils.getClaim(decodedJWT, "authorities").asString();
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
            UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
            CustomPrincipal customPrincipal = new CustomPrincipal(user.getId(),user.getUsername());
            Authentication authentication = new UsernamePasswordAuthenticationToken(customPrincipal, null, authorities);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
    
}
