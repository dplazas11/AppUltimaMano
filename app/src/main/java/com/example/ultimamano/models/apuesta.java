package com.example.ultimamano.models;

import com.google.gson.annotations.SerializedName;

public class apuesta {

    @SerializedName("id_usuario")
    private int idUsuario;

    @SerializedName("id_juego")
    private int idJuego;

    @SerializedName("monto_apostado")
    private double montoApostado;

    @SerializedName("monto_resultado")
    private double montoResultado;

    private String fecha;

    public apuesta() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public double getMontoApostado() {
        return montoApostado;
    }

    public void setMontoApostado(double montoApostado) {
        this.montoApostado = montoApostado;
    }

    public double getMontoResultado() {
        return montoResultado;
    }

    public void setMontoResultado(double montoResultado) {
        this.montoResultado = montoResultado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}