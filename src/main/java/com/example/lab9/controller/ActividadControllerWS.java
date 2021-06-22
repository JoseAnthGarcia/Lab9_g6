package com.example.lab9.controller;

import com.example.lab9.entity.Actividad;
import com.example.lab9.entity.Area;
import com.example.lab9.entity.Usuario;
import com.example.lab9.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class ActividadControllerWS {

    @Autowired
    ActividadRepository actividadRepository;

    @GetMapping(value = "/actividad", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listaActividades() {
        return new ResponseEntity(actividadRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/actividad", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity crearUusario(@RequestBody Actividad actividad,
                                       @RequestParam(value = "fetchId") boolean fetchId) {
        HashMap<String, Object> responseMap = new HashMap<>();
        actividadRepository.save(actividad);
        responseMap.put("estado", "creado");
        if (fetchId) {
            responseMap.put("id", actividad.getIdactividad());
        }
        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @PutMapping(value = "/actividad", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editarActividad(@RequestBody Actividad actividad) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (actividad.getIdactividad() > 0) {
            Optional<Actividad> opt = actividadRepository.findById(actividad.getIdactividad());
            if (opt.isPresent()) {
                actividadRepository.save(actividad);
                responseMap.put("estado", "actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "La actividad a actualizar no existe");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/actividad", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarAcitvidad(@RequestParam("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            if (actividadRepository.existsById(id)) {
                actividadRepository.deleteById(id);
                responseMap.put("estado", "borrado exitoso");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró la actividad con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity capturarExcepcion(HttpServletRequest httpServletRequest){

        HashMap<String, String> responseMap = new HashMap<>();
        if(httpServletRequest.getMethod().equals("POST") || httpServletRequest.getMethod().equals("PUT")){
            responseMap.put("msg", "Debe enviar una actividad");
            responseMap.put("estado", "error");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }



}
