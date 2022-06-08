package com.example.lab7_api.controller;

import com.example.lab7_api.entity.Juego;
import com.example.lab7_api.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class JuegoController {

    @Autowired
    JuegoRepository juegoRepository;

    @GetMapping(value = "/juegos")
    public List<Juego> listarProductos() {
        return juegoRepository.findAll();
    }

}
