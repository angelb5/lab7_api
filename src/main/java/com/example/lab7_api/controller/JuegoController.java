package com.example.lab7_api.controller;

import com.example.lab7_api.dto.JuegosUserDto;
import com.example.lab7_api.entity.Distribuidora;
import com.example.lab7_api.entity.Juego;
import com.example.lab7_api.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class JuegoController {

    @Autowired
    JuegoRepository juegoRepository;

    @GetMapping(value = "/juegos")
    public List<Juego> listarJuegos() {
        return juegoRepository.findAll();
    }

    @GetMapping(value = "/juegos/precio", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=ufg-8")
    public List<Juego> listarJuegosPrecio() {
        return juegoRepository.findAll();
    }

    @GetMapping(value = "/juegos/usuario/{id}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=ufg-8")
    public List<JuegosUserDto> listarJuegosUser(@PathVariable("id") String idStr){
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return new ArrayList<JuegosUserDto>();
        }
        return juegoRepository.obtenerJuegosPorUser(id);
    }

    @GetMapping(value = "/juegos/nocomprados/{id}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=ufg-8")
    public List<Juego> listarJuegosNoComprados(@PathVariable("id") String idStr){
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return new ArrayList<Juego>();
        }
        return juegoRepository.obtenerJuegosNoComprados(id);
    }

    @GetMapping(value = "/juegos/{id}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=ufg-8")
    public ResponseEntity<HashMap<String,Object>> obtenerJuegoPorId(@PathVariable("id") String idStr){
        HashMap<String,Object> responseJson = new HashMap<>();
        try{
            int id = Integer.parseInt(idStr);
            Optional<Juego> optionalJuego = juegoRepository.findById(id);
            if(optionalJuego.isPresent()){
                responseJson.put("resultado","exito");
                responseJson.put("juego",optionalJuego.get());
                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("resultado","error");
                responseJson.put("msg","Juego no encontrado");
            }
        } catch (NumberFormatException e){
            responseJson.put("resultado","error");
            responseJson.put("msg","El ID debe ser un número entero positivo");
        }
        return ResponseEntity.badRequest().body(responseJson);
    }

    @PostMapping(value = "/juegos", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=ufg-8")
    public ResponseEntity<HashMap<String,Object>> guardarJuego(
            @RequestBody Juego juego,
            @RequestParam(value="fetchId", required = false) boolean fetchId
    ){
        HashMap<String,Object> responseMap = new HashMap<>();
        juegoRepository.save(juego);
        if(fetchId){
            responseMap.put("id",juego.getId());
        }
        responseMap.put("estado","creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionExcepcion(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT")){
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar el juego con el debido formato");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

    @PutMapping(value = "/juegos", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=ufg-8")
    public ResponseEntity<HashMap<String,Object>> actualizarJuego(
            @RequestBody Juego juego
    ){
        HashMap<String,Object> responseMap = new HashMap<>();
        if(juego.getId() != null && juego.getId()>0){
            Optional<Juego> optionalJuego = juegoRepository.findById(juego.getId());
            if(optionalJuego.isPresent()){
                juegoRepository.save(juego);
                responseMap.put("estado","actualizado");
                return ResponseEntity.ok(responseMap);
            } else{
                responseMap.put("estado","error");
                responseMap.put("msg","El juego a actualizar no existe");
                return ResponseEntity.badRequest().body(responseMap);
            }
        } else {
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un ID");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @DeleteMapping(value = "/juegos/{id}")
    public ResponseEntity<HashMap<String,Object>> borrarJuego(
            @PathVariable("id") String idStr
    ){
        HashMap<String,Object> responseMap = new HashMap<>();
        try {
            int id = Integer.parseInt(idStr);
            if(juegoRepository.existsById(id)){
                juegoRepository.deleteById(id);
                responseMap.put("estado","borrado");
                return ResponseEntity.ok(responseMap);
            } else{
                responseMap.put("estado","error");
                responseMap.put("msg","no se encontró el juego con id: " + id);
            }
        } catch (NumberFormatException e){
            responseMap.put("estado","error");
            responseMap.put("msg","El ID tiene que ser un número entero");

        }
        return ResponseEntity.badRequest().body(responseMap);
    }



}


