package com.fintech.onboarding.service;

import com.fintech.onboarding.exception.NegocioException;
import com.fintech.onboarding.exception.RecursoNoEncontradoException;
import com.fintech.onboarding.model.entity.Cuenta;
import com.fintech.onboarding.model.request.PagoServicioRequest;
import com.fintech.onboarding.model.response.PagoServicioResponse;
import com.fintech.onboarding.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class PagoService {

    private final CuentaRepository cuentaRepository;

    @Value("${gestopago.bearer-token:TOKEN_GESTOPAGO_SECURE_12345}")
    private String bearerToken;

    public PagoService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public PagoServicioResponse procesarPagoServicio(PagoServicioRequest request) {
        // 1. Validar que la cuenta exista
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(request.getNumeroCuenta())
                .orElseThrow(() -> new RecursoNoEncontradoException("La cuenta especificada no existe"));

        // 2. Regla de Negocio: Validar cuenta activa
        if (!Boolean.TRUE.equals(cuenta.getActiva())) {
            throw new NegocioException("La cuenta bancaria se encuentra inactiva");
        }

        // 3. Regla de Negocio: Validar PIN de seguridad
        if (!cuenta.getPin().equals(request.getPin())) {
            throw new NegocioException("El PIN de seguridad introducido es incorrecto");
        }

        // 4. Regla de Negocio: Verificar saldo suficiente
        if (cuenta.getSaldo().compareTo(request.getMontoPago()) < 0) {
            throw new NegocioException("Saldo insuficiente en la cuenta para realizar el pago");
        }

        // 5. Aplicar descuento de dinero en cuenta
        cuenta.setSaldo(cuenta.getSaldo().subtract(request.getMontoPago()));
        cuentaRepository.save(cuenta);

        // 6. Generar UPC con random string
        String upc = generarRandomUpc(10);

        // 7. Formatear horaLocal: yyyyMMddHHmmss
        String horaLocal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // Retornar la respuesta integrada de Gestopago
        return new PagoServicioResponse(
                cuenta.getNumeroCuenta(),
                upc,
                horaLocal,
                request.getMontoPago(),
                cuenta.getSaldo(),
                "PAGO_APROBADO_GESTOPAGO"
        );
    }

    private String generarRandomUpc(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
