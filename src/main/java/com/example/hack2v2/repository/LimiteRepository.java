package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.Limite;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LimiteRepository extends JpaRepository<Limite, Long> {
    Optional<Limite> findByUsuarioAndModelo(Usuario usuario, ModeloIA modelo);
    List<Limite> findByUsuario(Usuario usuario);
    List<Limite> findByUsuario_Empresa_Id(Long empresaId);
}
