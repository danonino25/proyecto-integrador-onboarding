package com.fintech.onboarding.model.response;

import java.math.BigDecimal;

public class PagoServicioResponse {

    private String numeroCuenta;
    private String upc;
    private String horaLocal;
    private BigDecimal montoPago;
    private BigDecimal saldoRestante;
    private String estadoTransaccion;

    public PagoServicioResponse(String numeroCuenta, String upc, String horaLocal, BigDecimal montoPago, BigDecimal saldoRestante, String estadoTransaccion) {
        this.numeroCuenta = numeroCuenta;
        this.upc = upc;
        this.horaLocal = horaLocal;
        this.montoPago = montoPago;
        this.saldoRestante = saldoRestante;
        this.estadoTransaccion = estadoTransaccion;
    }

    // Getters
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getUpc() { return upc; }
    public String getHoraLocal() { return horaLocal; }
    public BigDecimal getMontoPago() { return montoPago; }
    public BigDecimal getSaldoRestante() { return saldoRestante; }
    public String getEstadoTransaccion() { return estadoTransaccion; }
}
