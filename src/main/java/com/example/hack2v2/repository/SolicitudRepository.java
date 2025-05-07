package com.example.hack2v2.repository;

import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Solicitud;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.dto.response.ConsumoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByUsuario(Usuario usuario);
    List<Solicitud> findByUsuarioAndFechaHoraBetween(Usuario usuario, LocalDateTime inicio, LocalDateTime fin);

    List<Solicitud> findByModelo(ModeloIA modelo);

    @Query("SELECT s FROM Solicitud s WHERE s.usuario.empresa = :empresa")
    List<Solicitud> findByEmpresa(@Param("empresa") Empresa empresa);

    @Query("SELECT s FROM Solicitud s WHERE s.usuario.empresa = :empresa AND s.fechaHora BETWEEN :inicio AND :fin")
    List<Solicitud> findByEmpresaAndFechaHoraBetween(@Param("empresa") Empresa empresa,
                                                     @Param("inicio") LocalDateTime inicio,
                                                     @Param("fin") LocalDateTime fin);

    @Query("SELECT SUM(s.tokensConsumidos) FROM Solicitud s WHERE s.usuario = :usuario")
    Integer sumTokensConsumidosByUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT SUM(s.tokensConsumidos) FROM Solicitud s WHERE s.usuario.empresa = :empresa")
    Integer sumTokensConsumidosByEmpresa(@Param("empresa") Empresa empresa);

    /**
     * Agrupa las solicitudes de un usuario por modelo y suma los tokens consumidos.
     */
    @Query("""
        SELECT new com.example.hack2v2.dto.response.ConsumoResponse(
            s.modelo.nombre,
            SUM(s.tokensConsumidos)
        )
        FROM Solicitud s
        WHERE s.usuario.id = :userId
        GROUP BY s.modelo.nombre
    """)
    List<ConsumoResponse> sumarPorModeloYUsuario(@Param("userId") Long userId);

    /**
     * Agrupa las solicitudes de una empresa por modelo y suma los tokens consumidos.
     */
    @Query("""
        SELECT new com.example.hack2v2.dto.response.ConsumoResponse(
            s.modelo.nombre,
            SUM(s.tokensConsumidos)
        )
        FROM Solicitud s
        WHERE s.usuario.empresa.id = :empresaId
        GROUP BY s.modelo.nombre
    """)
    List<ConsumoResponse> sumarPorModeloByEmpresa(@Param("empresaId") Long empresaId);
}
