package com.fintech.onboarding.repository;

import com.fintech.onboarding.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

    // Requerimiento de paginación con ContainingIgnoreCase
    Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}