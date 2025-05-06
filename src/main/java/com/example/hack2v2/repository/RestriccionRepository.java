package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Restriccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestriccionRepository extends JpaRepository<Restriccion, Long> {
    List<Restriccion> findByEmpresa(Empresa empresa);
    List<Restriccion> findByEmpresaAndActivoTrue(Empresa empresa);
    Optional<Restriccion> findByEmpresaAndModelo(Empresa empresa, ModeloIA modelo);
}