package com.example.lab9.controller;

import com.example.lab9.entity.Area;
import com.example.lab9.entity.AreaDto;
import com.example.lab9.entity.Usuario;
import com.example.lab9.repository.AreaRepository;
import com.example.lab9.repository.UsuarioReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class AreaController {

    @Autowired
    AreaRepository areaRepository;
    @Autowired
    UsuarioReposity usuarioReposity;

    @GetMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarAreas() {
        return new ResponseEntity(areaRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping(value = "/area/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity areaId(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Area> opt = areaRepository.findById(id);
            if (opt.isPresent()) {
                responseMap.put("estado", "ok");
                responseMap.put("area", opt.get());
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el area con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity crearArea(
            @RequestBody Area area,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        areaRepository.save(area);
        if (fetchId) {
            responseMap.put("id", area.getIdarea());
        }
        responseMap.put("estado", "creado");
        if(area==null){
            responseMap.put("msg", "Debe enviar un area");
            responseMap.put("estado","error");
        }
        return new ResponseEntity(responseMap, HttpStatus.CREATED);

    }
    @GetMapping(value = "/areaConUsuarios/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity areaConUsuarios(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Area> opt = areaRepository.findById(id);
            if (opt.isPresent()) {
                Area area = opt.get();
                List<Usuario> listaUsuarios = usuarioReposity.usuariosPorArea(id);
                AreaDto areaDto = new AreaDto();
                areaDto.setIdarea(area.getIdarea());
                areaDto.setNombrearea(area.getNombrearea());
                areaDto.setListaUsuarios(listaUsuarios);
                responseMap.put("area", areaDto);
                responseMap.put("estado", "ok");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el area con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editarArea(@RequestBody Area area) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (area.getIdarea() > 0) {
            Optional<Area> opt = areaRepository.findById(area.getIdarea());
            if (opt.isPresent()) {
                areaRepository.save(area);
                responseMap.put("estado", "actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "El area a actualizar no existe");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarArea(@RequestParam("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            if (areaRepository.existsById(id)) {
                areaRepository.deleteById(id);
                responseMap.put("estado", "borrado exitoso");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el producto con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }


}
