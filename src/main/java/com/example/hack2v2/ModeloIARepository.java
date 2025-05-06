package com.example.sparkyai.repository;

import com.example.sparkyai.model.entities.ModeloIA;
import com.example.sparkyai.model.enums.TipoModeloEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloIARepository extends JpaRepository<ModeloIA, Long> {
    List<ModeloIA> findByTipo(TipoModeloEnum tipo);
    List<ModeloIA> findByActivoTrue();
    List<ModeloIA> findByProveedorAndActivoTrue(String proveedor);
}