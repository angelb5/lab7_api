package com.example.lab7_api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "plataformas")
public class Plataforma implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idplataforma")
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    //TODO Reverse Engineering! Migrate other columns to the entity
}