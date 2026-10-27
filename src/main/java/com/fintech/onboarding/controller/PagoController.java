package com.fintech.onboarding.controller;

import com.fintech.onboarding.model.request.PagoServicioRequest;
import com.fintech.onboarding.model.response.GenericResponse;
import com.fintech.onboarding.model.response.PagoServicioResponse;
import com.fintech.onboarding.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/sendTx")
    public ResponseEntity<GenericResponse<PagoServicioResponse>> enviarTransaccion(@Valid @RequestBody PagoServicioRequest request) {
        PagoServicioResponse resultado = pagoService.procesarPagoServicio(request);
        GenericResponse<PagoServicioResponse> response = new GenericResponse<>(0, "Transacción enviada a Gestopago exitosamente", resultado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}