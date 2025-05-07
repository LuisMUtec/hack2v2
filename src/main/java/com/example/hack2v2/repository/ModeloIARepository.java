package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.ModeloIA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeloIARepository extends JpaRepository<ModeloIA, Long> {
    Optional<ModeloIA> findByNombre(String nombre);
}
