package com.example.hack2v2.dto.mapper;

import com.example.hack2v2.dto.request.LimiteRequest;
import com.example.hack2v2.dto.response.LimiteResponse;
import com.example.hack2v2.model.entities.Limite;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LimiteMapper {

    public Limite toEntity(LimiteRequest request, Usuario usuario, ModeloIA modelo) {
        Limite limite = new Limite();
        limite.setUsuario(usuario);
        limite.setModelo(modelo);
        limite.setLimiteSolicitudes(request.getLimiteSolicitudes());
        limite.setLimiteTokens(request.getLimiteTokens());
        limite.setPeriodoReinicio(request.getPeriodoReinicio());
        limite.setSolicitudesUsadas(0);
        limite.setTokensUsados(0);
        
        LocalDateTime ahora = LocalDateTime.now();
        limite.setUltimoReinicio(ahora);
        limite.setProximoReinicio(calcularProximoReinicio(ahora, request.getPeriodoReinicio()));
        
        return limite;
    }

    public LimiteResponse toResponse(Limite limite) {
        return LimiteResponse.builder()
                .id(limite.getId())
                .usuarioId(limite.getUsuario().getId())
                .usuarioNombre(limite.getUsuario().getNombre() + " " + limite.getUsuario().getApellido())
                .modeloId(limite.getModelo().getId())
                .modeloNombre(limite.getModelo().getNombre())
                .limiteSolicitudes(limite.getLimiteSolicitudes())
                .limiteTokens(limite.getLimiteTokens())
                .periodoReinicio(limite.getPeriodoReinicio())
                .solicitudesUsadas(limite.getSolicitudesUsadas())
                .tokensUsados(limite.getTokensUsados())
                .ultimoReinicio(limite.getUltimoReinicio())
                .proximoReinicio(limite.getProximoReinicio())
                .build();
    }
    
    private LocalDateTime calcularProximoReinicio(LocalDateTime desde, String periodoReinicio) {
        // Parsear el periodo de reinicio (1h, 1d, 7d)
        char unidad = periodoReinicio.charAt(periodoReinicio.length() - 1);
        int cantidad = Integer.parseInt(periodoReinicio.substring(0, periodoReinicio.length() - 1));
        
        return switch (unidad) {
            case 'h' -> desde.plusHours(cantidad);
            case 'd' -> desde.plusDays(cantidad);
            case 'w' -> desde.plusWeeks(cantidad);
            default -> desde.plusHours(1); // Default a 1 hora si no se reconoce la unidad
        };
    }
}