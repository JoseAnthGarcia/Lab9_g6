package com.example.lab9.controller;

import com.example.lab9.entity.Usuario;
import com.example.lab9.repository.UsuarioReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioControllerWS {

    @Autowired
    UsuarioReposity usuarioReposity;

    @GetMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerUsuarios(){
        List<Usuario> listaUsuarios = usuarioReposity.findAll();
        return new ResponseEntity(listaUsuarios, )
    }
}
