package com.spec.api_sugflora.security;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter{

    public Authentication attempAuthentication (
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {
        return null;
    }

}
