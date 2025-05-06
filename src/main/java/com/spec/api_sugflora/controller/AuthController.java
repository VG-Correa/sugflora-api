package com.spec.api_sugflora.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spec.api_sugflora.dto.LoginRequest;
import com.spec.api_sugflora.security.JwtUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // Use construtor para injeção de dependência
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Configura o contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Gera o token JWT
            String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

            // Retorna o token na resposta
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Credenciais inválidas"));
        }
    }

}
