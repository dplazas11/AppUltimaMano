package com.example.ultimamano.models;

import com.google.gson.annotations.SerializedName;

public class casino {

    @SerializedName("id_usuario")
    private int idUsuario;

    private String direccion;

    @SerializedName("id_ciudad")
    private int idCiudad;

    @SerializedName("id_tipo_casino")
    private int idTipoCasino;

    private usuario usuario;

    private ciudad ciudad;

    private tipo_casino tipo_casino;

    public casino() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdTipoCasino() {
        return idTipoCasino;
    }

    public void setIdTipoCasino(int idTipoCasino) {
        this.idTipoCasino = idTipoCasino;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public tipo_casino getTipo_casino() {
        return tipo_casino;
    }

    public void setTipo_casino(tipo_casino tipo_casino) {
        this.tipo_casino = tipo_casino;
    }
}
