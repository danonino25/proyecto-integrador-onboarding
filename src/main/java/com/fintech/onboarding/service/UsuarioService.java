package com.fintech.onboarding.service;

import com.fintech.onboarding.exception.NegocioException;
import com.fintech.onboarding.exception.RecursoNoEncontradoException;
import com.fintech.onboarding.model.entity.Usuario;
import com.fintech.onboarding.model.request.UsuarioBusquedaRequest;
import com.fintech.onboarding.model.request.UsuarioCrearRequest;
import com.fintech.onboarding.model.response.UsuarioResponse;
import com.fintech.onboarding.repository.UsuarioRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse registrarUsuario(UsuarioCrearRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new NegocioException("El correo electrónico ya se encuentra registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapToResponse(usuarioGuardado);
    }

    public List<UsuarioResponse> buscarUsuarios(UsuarioBusquedaRequest request) {
        Usuario usuarioFiltro = new Usuario();
        
        if (request.getId() != null) usuarioFiltro.setId(request.getId());
        if (request.getNombre() != null && !request.getNombre().isBlank()) usuarioFiltro.setNombre(request.getNombre());
        if (request.getEmail() != null && !request.getEmail().isBlank()) usuarioFiltro.setEmail(request.getEmail());
        if (request.getEstatus() != null && !request.getEstatus().isBlank()) usuarioFiltro.setEstatus(request.getEstatus());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Usuario> example = Example.of(usuarioFiltro, matcher);
        List<Usuario> resultados = usuarioRepository.findAll(example);

        if (resultados.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron usuarios con los criterios de búsqueda especificados");
        }

        return resultados.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getEstatus(),
                usuario.getFechaCreacion()
        );
    }
}