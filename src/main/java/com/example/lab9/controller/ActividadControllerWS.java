package com.example.lab9.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActividadControllerWS {

    @GetMapping(value = "/actividad",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listaActividades(){
        return new ResponseEntity("hola", HttpStatus.OK);
    }
}
