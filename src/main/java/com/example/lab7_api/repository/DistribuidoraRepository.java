package com.example.lab7_api.repository;

import com.example.lab7_api.entity.Distribuidora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistribuidoraRepository extends JpaRepository<Distribuidora,Integer> {

}
