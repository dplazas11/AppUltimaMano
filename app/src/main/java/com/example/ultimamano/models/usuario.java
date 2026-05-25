package com.example.ultimamano.models;

public class usuario {


    private String nombre;

    private String email;

    private String contrasena;

    private int id_rol;

    public usuario() {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }



    @Override
    public String toString() {
        return "Usuario{" +
                ", nombre='" + nombre + '\'' +
                ", correo='" + email + '\'' +
                ", id_rol=" + id_rol +
                '}';
    }
}
