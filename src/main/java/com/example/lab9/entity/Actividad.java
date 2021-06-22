package com.example.lab9.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    private int idactividad;
    private String nombreactidad;
    private String descripcion;
    private int idproyecto;
}
