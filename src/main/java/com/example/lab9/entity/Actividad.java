package com.example.lab9.entity;

import javax.persistence.*;

@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idactividad;
    @Column(name="nombreActividad")
    private String nombreactidad;
    private String descripcion;
    @Column(nullable = false)
    private int idproyecto;
    @Column(nullable = false)
    private String usuario_owner;
    private float peso;
    private boolean estado;

    public int getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(int idactividad) {
        this.idactividad = idactividad;
    }

    public String getNombreactidad() {
        return nombreactidad;
    }

    public void setNombreactidad(String nombreactidad) {
        this.nombreactidad = nombreactidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getUsuario_owner() {
        return usuario_owner;
    }

    public void setUsuario_owner(String usuario_owner) {
        this.usuario_owner = usuario_owner;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
