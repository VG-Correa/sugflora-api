package com.spec.api_sugflora.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario findByUsername(String username);
}
