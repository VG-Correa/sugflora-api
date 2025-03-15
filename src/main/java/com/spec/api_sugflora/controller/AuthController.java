package com.spec.api_sugflora.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spec.api_sugflora.ApiSugfloraApplication;
import com.spec.api_sugflora.dto.LoginRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest LoginRequest) {
        
        try {

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(LoginRequest.getUsername(), LoginRequest.getPassword())
                );
                
            SecurityContextHolder.getContext().setAuthentication(authentication);
                
            UserDetails userDetails = userDetailsService.loadUserByUsername(LoginRequest.getUsername());

            return ResponseEntity.ok().body(Map.of("mensagem", "Usuario logado"));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(Map.of("erro", exception.getMessage()));

        }
    }

}
