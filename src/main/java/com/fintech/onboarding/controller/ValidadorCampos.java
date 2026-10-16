package com.fintech.onboarding.controller;

import com.fintech.onboarding.exception.NegocioException;
import com.fintech.onboarding.exception.RecursoNoEncontradoException;
import com.fintech.onboarding.model.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidadorCampos {

    /**
     * Captura las fallas de Bean Validation enviadas desde los DTOs anotados con @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Map<String, String>>> dtoValidation(MethodArgumentNotValidException ex) {
        GenericResponse<Map<String, String>> genericResponse = new GenericResponse<>();
        Map<String, String> listError = new HashMap<>();
        
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            listError.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        genericResponse.setCodigo(1);
        genericResponse.setMensaje("Datos no validos: " + listError);
        genericResponse.setDatos(listError);
        
        return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura excepciones de negocio personalizadas para recursos no encontrados (HTTP 404).
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<GenericResponse<Void>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        GenericResponse<Void> response = new GenericResponse<>(1, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Captura excepciones de negocio personalizadas como correos duplicados (HTTP 400/409).
     */
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<GenericResponse<Void>> handleNegocioException(NegocioException ex) {
        GenericResponse<Void> response = new GenericResponse<>(1, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Captura cualquier otra excepción no controlada evitando mostrar la traza técnica del servidor (HTTP 500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Void>> handleGeneralException(Exception ex) {
        GenericResponse<Void> response = new GenericResponse<>(1, "Ocurrió un error interno en la solicitud. Intente más tarde.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}