package com.example.lab7_api.repository;

import com.example.lab7_api.dto.JuegosUserDto;
import com.example.lab7_api.entity.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Integer> {
    @Query(value = "Select  j.idjuego, j.nombre, j.descripcion, g.nombre as genero, j.image as imageURL from juegos j " +
            "inner join juegosxusuario ju  on j.idjuego=ju.idjuego " +
            "inner join usuarios u on ju.idusuario=u.idusuario " +
            "inner join generos g on g.idgenero=j.idgenero Where u.idusuario= ?1",nativeQuery = true)
    List<JuegosUserDto> obtenerJuegosPorUser(int idusuario);

    @Query(value ="select * from juegos " +
            "where idjuego NOT IN (select idjuego from juegosxusuario where idusuario = :idusuario) " +
            "order by nombre desc", nativeQuery = true)
    List<Juego> obtenerJuegosNoComprados(int idusuario);

    List<Juego> findAllByOrderByPrecioAsc();
}
