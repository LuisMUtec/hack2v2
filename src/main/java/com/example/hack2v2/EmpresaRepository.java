package com.example.sparkyai.repository;

import com.example.hack2v2.entities.Empresa;
import com.example.sparkyai.model.enums.EstadoEmpresaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByRuc(String ruc);
    List<Empresa> findByEstado(EstadoEmpresaEnum estado);
}