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

    @PutMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarUsuario(@RequestBody Usuario usuario){

        Optional<Usuario> usuarioOpt = usuarioReposity.findById(usuario.getCorreo());
        HashMap<String, String> responseMap = new HashMap<>();
        if(usuarioOpt.isPresent()){
            usuarioReposity.save(usuario);
            responseMap.put("estado", "actualizado");
            return new ResponseEntity(responseMap, HttpStatus.OK);
        }else{
            responseMap.put("estado", "El correo del usuario a actualizar no existe");
            responseMap.put("msg", "error");
            return new ResponseEntity(responseMap, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarUsuario(@RequestParam(value = "correo", required = false) String correo){
        HashMap<String, String> responseMap = new HashMap<>();
        if(correo!=null){
            Optional<Usuario> usuarioOpt = usuarioReposity.findById(correo);
            if(usuarioOpt.isPresent()){
                usuarioReposity.deleteById(correo);
                responseMap.put("estado", "Borrado exitoso");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            }else{
                responseMap.put("msg", "no se encontro el usuario con correo: "+correo);
                responseMap.put("estado", "error");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            }
        }else{
            responseMap.put("msg", "Debe ingresar un correo");
            responseMap.put("estado", "error");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
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
