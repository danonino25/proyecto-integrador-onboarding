package com.fintech.onboarding.controller;

import com.fintech.onboarding.model.request.UsuarioBusquedaRequest;
import com.fintech.onboarding.model.request.UsuarioCrearRequest;
import com.fintech.onboarding.model.response.GenericResponse;
import com.fintech.onboarding.model.response.UsuarioResponse;
import com.fintech.onboarding.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/crear")
    public ResponseEntity<GenericResponse<UsuarioResponse>> registrarUsuario(@Valid @RequestBody UsuarioCrearRequest request) {
        UsuarioResponse usuarioCreado = usuarioService.registrarUsuario(request);
        GenericResponse<UsuarioResponse> response = new GenericResponse<>(0, "Usuario creado exitosamente", usuarioCreado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/buscar")
    public ResponseEntity<GenericResponse<List<UsuarioResponse>>> buscarUsuarios(@RequestBody UsuarioBusquedaRequest request) {
        List<UsuarioResponse> resultados = usuarioService.buscarUsuarios(request);
        GenericResponse<List<UsuarioResponse>> response = new GenericResponse<>(0, "Consulta realizada exitosamente", resultados);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}