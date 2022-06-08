package com.example.lab7_api.controller;

import com.example.lab7_api.entity.Distribuidora;
import com.example.lab7_api.repository.DistribuidoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class DistribuidoraController {
    @Autowired
    DistribuidoraRepository distribuidoraRepository;

    @GetMapping(value = "/distribuidoras")
    public List<Distribuidora> listarDistribuidoras() {
        return distribuidoraRepository.findAll();
    }

    @GetMapping(value = "/distribuidoras/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerDistribuidoraPorId(@PathVariable("id") String idStr){
        HashMap<String,Object> responseJson = new HashMap<>();
        try{
            int id = Integer.parseInt(idStr);
            Optional<Distribuidora> optionalDistribuidora = distribuidoraRepository.findById(id);
            if(optionalDistribuidora.isPresent()){
                responseJson.put("resultado","exito");
                responseJson.put("distribuidora",optionalDistribuidora.get());
                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("resultado","error");
                responseJson.put("msg","Distribuidora no encontrada");
            }
        } catch (NumberFormatException e){
            responseJson.put("resultado","error");
            responseJson.put("msg","El ID debe ser un número entero positivo");
        }
        return ResponseEntity.badRequest().body(responseJson);
    }

    @PostMapping(value = "/distribuidoras")
    public ResponseEntity<HashMap<String,Object>> guardarDistribuidora(
            @RequestBody Distribuidora distribuidora,
            @RequestParam(value="fetchId", required = false) boolean fetchId
    ){
        HashMap<String,Object> responseMap = new HashMap<>();
        distribuidoraRepository.save(distribuidora);
        if(fetchId){
            responseMap.put("id",distribuidora.getId());
        }
        responseMap.put("estado","creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }



    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionExcepcion(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT")){
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar la distribuidora con el debido formato");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

    @PutMapping(value = "/distribuidora")
    public ResponseEntity<HashMap<String,Object>> actualizarDistribuidora(
            @RequestBody Distribuidora distribuidora
    ){
        HashMap<String,Object> responseMap = new HashMap<>();
        if(distribuidora.getId() != null && distribuidora.getId()>0){
            Optional<Distribuidora> optionalDistribuidora = distribuidoraRepository.findById(distribuidora.getId());
            if(optionalDistribuidora.isPresent()){
                distribuidoraRepository.save(distribuidora);
                responseMap.put("estado","actualizado");
                return ResponseEntity.ok(responseMap);
            } else{
                responseMap.put("estado","error");
                responseMap.put("msg","La distribuidora a actualizar no existe");
                return ResponseEntity.badRequest().body(responseMap);
            }
        } else {
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un ID");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @DeleteMapping(value = "/distribuidora/{id}")
    public ResponseEntity<HashMap<String,Object>> borrarDistribuidora(
            @PathVariable("id") String idStr
    ){
        HashMap<String,Object> responseMap = new HashMap<>();
        try {
            int id = Integer.parseInt(idStr);
            if(distribuidoraRepository.existsById(id)){
                distribuidoraRepository.deleteById(id);
                responseMap.put("estado","borrado");
                return ResponseEntity.ok(responseMap);
            } else{
                responseMap.put("estado","error");
                responseMap.put("msg","no se encontró la distribuidora con id: " + id);
            }
        } catch (NumberFormatException e){
            responseMap.put("estado","error");
            responseMap.put("msg","El ID tiene que ser un número entero");

        }
        return ResponseEntity.badRequest().body(responseMap);
    }

}
