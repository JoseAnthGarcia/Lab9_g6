package com.example.lab9.repository;

import com.example.lab9.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
    List <Actividad> findByIdproyecto(int idproyecto);
}
