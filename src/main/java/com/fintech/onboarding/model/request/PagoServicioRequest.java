package com.fintech.onboarding.model.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PagoServicioRequest {

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;

    @NotBlank(message = "El PIN es obligatorio")
    @Size(min = 4, max = 4, message = "El PIN debe ser de 4 dígitos")
    private String pin;

    @NotNull(message = "El idProducto es obligatorio")
    private Integer idProducto;

    @NotNull(message = "El idServicio es obligatorio")
    private Integer idServicio;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 10, max = 20, message = "El teléfono debe tener entre 10 y 20 caracteres")
    private String telefono;

    @NotBlank(message = "La referencia es obligatoria")
    @Size(min = 1, max = 100, message = "La referencia debe tener entre 1 y 100 caracteres")
    private String referencia;

    @NotNull(message = "El monto de pago es obligatorio")
    @DecimalMin(value = "1.00", message = "El monto mínimo a pagar es $1.00")
    private BigDecimal montoPago;

    @NotBlank(message = "La unidad es obligatoria")
    @Size(min = 3, max = 25, message = "La unidad debe tener entre 3 y 25 caracteres")
    private String unidad;

    // Getters y Setters
    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public BigDecimal getMontoPago() { return montoPago; }
    public void setMontoPago(BigDecimal montoPago) { this.montoPago = montoPago; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
}
