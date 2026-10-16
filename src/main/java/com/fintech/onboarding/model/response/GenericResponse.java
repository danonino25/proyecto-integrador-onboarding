package com.fintech.onboarding.model.response;

public class GenericResponse<T> {

    private int codigo;
    private String mensaje;
    private T datos;

    public GenericResponse() {}

    public GenericResponse(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public GenericResponse(int codigo, String mensaje, T datos) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.datos = datos;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public T getDatos() { return datos; }
    public void setDatos(T datos) { this.datos = datos; }
}