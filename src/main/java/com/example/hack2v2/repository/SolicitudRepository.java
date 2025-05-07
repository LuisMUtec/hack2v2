package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Solicitud;
import com.example.hack2v2.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    
    List<Solicitud> findByUsuario(Usuario usuario);
    
    List<Solicitud> findByUsuarioAndFechaHoraBetween(
            Usuario usuario, LocalDateTime inicio, LocalDateTime fin);
    
    List<Solicitud> findByUsuarioAndModeloAndFechaHoraBetween(
            Usuario usuario, ModeloIA modelo, LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT s FROM Solicitud s WHERE s.usuario.id = :usuarioId AND s.modelo.id = :modeloId " +
           "AND s.fechaHora BETWEEN :inicio AND :fin")
    List<Solicitud> findByUsuarioIdAndModeloIdAndFechaHoraBetween(
            Long usuarioId, Long modeloId, LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT COUNT(s) FROM Solicitud s WHERE s.usuario.id = :usuarioId AND s.modelo.id = :modeloId " +
           "AND s.fechaHora BETWEEN :inicio AND :fin")
    Integer countByUsuarioIdAndModeloIdAndFechaHoraBetween(
            Long usuarioId, Long modeloId, LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT COALESCE(SUM(s.tokensConsumidos), 0) FROM Solicitud s WHERE s.usuario.id = :usuarioId " +
           "AND s.modelo.id = :modeloId AND s.fechaHora BETWEEN :inicio AND :fin")
    Integer sumTokensByUsuarioIdAndModeloIdAndFechaHoraBetween(
            Long usuarioId, Long modeloId, LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT s FROM Solicitud s WHERE s.usuario.empresa.id = :empresaId")
    List<Solicitud> findByEmpresaId(Long empresaId);
}