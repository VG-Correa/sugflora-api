package com.spec.api_sugflora.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spec.api_sugflora.dto.LoginRequest;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.responses.tokenResponse;
import com.spec.api_sugflora.security.JwtUtil;
import com.spec.speedspring.core.controller.GenericRestController;
import com.spec.speedspring.core.responses.GenericResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth")
public class AuthController extends GenericRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // Use construtor para injeção de dependência
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<GenericResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Iniciando login");
        System.out.println(loginRequest);

        try {
            // Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Configura o contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Gera o token JWT
            String token = jwtUtil.generationToken((UserDetails) authentication.getPrincipal());
            Usuario usuario = (Usuario) authentication.getPrincipal();

            tokenResponse tokenResponse = new tokenResponse();
            tokenResponse.setToken(token);
            tokenResponse.setUsuario(usuario.toDTO());

            System.out.println(tokenResponse);

            // Retorna o token na resposta
            return getResponseOK(null, tokenResponse);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return getResponseException(exception);
        }
    }

}
