package com.ChazaPOS.Negocio;

public class Usuario {
    private int id;
    private String nombre;
    private String username;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String username, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
