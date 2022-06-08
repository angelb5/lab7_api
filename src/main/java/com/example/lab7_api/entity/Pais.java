package com.example.lab7_api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "paises")
public class Pais implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpais", nullable = false)
    private Integer id;

    @Column(name = "iso", length = 2)
    private String iso;

    @Column(name = "nombre", length = 80)
    private String nombre;

    //TODO Reverse Engineering! Migrate other columns to the entity
}