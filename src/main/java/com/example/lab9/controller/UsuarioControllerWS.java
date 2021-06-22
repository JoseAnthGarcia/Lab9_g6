package com.example.lab9.controller;

import com.example.lab9.entity.Usuario;
import com.example.lab9.repository.UsuarioReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioControllerWS {

    @Autowired
    UsuarioReposity usuarioReposity;

    @GetMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerUsuarios(){
        List<Usuario> listaUsuarios = usuarioReposity.findAll();
        return new ResponseEntity(listaUsuarios, HttpStatus.OK);
    }

    @PostMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity crearUusario(@RequestBody Usuario usuario,
                                       @RequestParam(value = "fetchCorreo", required = false) boolean fetchCorreo){
        HashMap<String, String> responseMap = new HashMap<>();
        usuarioReposity.save(usuario);
        responseMap.put("estado", "creado");
        if(fetchCorreo){
            responseMap.put("correo", usuario.getCorreo());
        }
        return new ResponseEntity(responseMap, HttpStatus.OK);
    }
    

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity capturarExcepcion(HttpServletRequest httpServletRequest){

        HashMap<String, String> responseMap = new HashMap<>();
        if(httpServletRequest.getMethod().equals("POST") || httpServletRequest.getMethod().equals("PUT")){
            responseMap.put("msg", "Debe enviar un usuario");
            responseMap.put("estado", "error");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }
}
