package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.Solicitud;
import com.example.hack2v2.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByUsuario(Usuario usuario);

    @Query("SELECT SUM(s.tokensConsumidos) FROM Solicitud s WHERE s.usuario.id = :usuarioId AND s.modelo.id = :modeloId")
    Integer contarTokensPorUsuarioYModelo(@Param("usuarioId") Long usuarioId, @Param("modeloId") Long modeloId);
}
