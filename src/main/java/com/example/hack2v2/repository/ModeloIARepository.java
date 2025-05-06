package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.enums.TipoModeloEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloIARepository extends JpaRepository<ModeloIA, Long> {
    List<ModeloIA> findByTipo(TipoModeloEnum tipo);
    List<ModeloIA> findByActivoTrue();
    List<ModeloIA> findByProveedorAndActivoTrue(String proveedor);
}