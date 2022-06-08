package com.example.lab7_api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "distribuidoras")
public class Distribuidora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddistribuidora", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "fundacion", nullable = false)
    private Integer fundacion;

    @Column(name = "web", length = 200)
    private String web;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsede")
    private Pais idsede;

}