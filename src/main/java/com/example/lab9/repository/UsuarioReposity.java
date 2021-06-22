package com.example.lab9.repository;

import com.example.lab9.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioReposity extends JpaRepository<Usuario, String> {

    @Query(value = "SELECT * FROM usuarios where idarea= ?1",nativeQuery = true)
    List<Usuario> usuariosPorArea(int idArea);

}
