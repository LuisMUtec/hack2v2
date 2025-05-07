package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.model.enums.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByEmpresa(Empresa empresa);
    List<Usuario> findByEmpresaAndRol(Empresa empresa, RolEnum rol);
    boolean existsByCorreo(String correo);
    boolean existsByNombreUsuario(String nombreUsuario);

}