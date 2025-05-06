package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.Empresa;
import com.example.sparkyai.model.entities.ModeloIA;
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
    List<Solicitud> findByUsuarioAndFechaHoraBetween(Usuario usuario, LocalDateTime inicio, LocalDateTime fin);
    
    List<Solicitud> findByModelo(ModeloIA modelo);
    
    @Query("SELECT s FROM Solicitud s WHERE s.usuario.empresa = :empresa")
    List<Solicitud> findByEmpresa(Empresa empresa);
    
    @Query("SELECT s FROM Solicitud s WHERE s.usuario.empresa = :empresa AND s.fechaHora BETWEEN :inicio AND :fin")
    List<Solicitud> findByEmpresaAndFechaHoraBetween(Empresa empresa, LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT SUM(s.tokensConsumidos) FROM Solicitud s WHERE s.usuario = :usuario")
    Integer sumTokensConsumidosByUsuario(Usuario usuario);
    
    @Query("SELECT SUM(s.tokensConsumidos) FROM Solicitud s WHERE s.usuario.empresa = :empresa")
    Integer sumTokensConsumidosByEmpresa(Empresa empresa);
}