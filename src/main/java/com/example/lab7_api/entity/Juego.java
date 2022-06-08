package com.example.lab7_api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "juegos")
public class Juego implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idjuego", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 448)
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "image", length = 400)
    private String image;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "idgenero")
    private Genero genero;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "idplataforma")
    private Plataforma plataforma;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "iddistribuidora")
    private Distribuidora distribuidora;

}