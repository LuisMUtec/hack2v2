package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.Administrador;
import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmpresa(Empresa empresa);
    Optional<Administrador> findByUsuario(Usuario usuario);
}