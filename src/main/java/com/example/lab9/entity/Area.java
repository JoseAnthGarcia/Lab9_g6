package com.example.lab9.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "areas")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idarea")
    private int idarea;
    @Column(name="nombreArea")
    private String nombrearea;

    public int getIdarea() {
        return idarea;
    }

    public void setIdarea(int idarea) {
        this.idarea = idarea;
    }

    public String getNombrearea() {
        return nombrearea;
    }

    public void setNombrearea(String nombreArea) {
        this.nombrearea = nombreArea;
    }

}
