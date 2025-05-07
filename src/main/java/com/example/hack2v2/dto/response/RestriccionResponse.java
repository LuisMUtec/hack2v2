package com.example.hack2v2.dto.response;

import com.example.hack2v2.model.entities.Restriccion;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class RestriccionResponse {
    private Long id;
    private Long modeloId;
    private String modeloNombre;
    private Integer limiteSolicitudes;
    private Integer limiteTokens;
    private String periodoReinicio;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public RestriccionResponse(Restriccion r) {
        this.id                 = r.getId();
        this.modeloId           = r.getModelo().getId();
        this.modeloNombre       = r.getModelo().getNombre();
        this.limiteSolicitudes  = r.getLimiteSolicitudes();
        this.limiteTokens       = r.getLimiteTokens();
        this.periodoReinicio    = r.getPeriodoReinicio();
        this.activo             = r.isActivo();
        this.fechaCreacion      = r.getFechaCreacion();
        this.fechaActualizacion = r.getFechaActualizacion();
    }
}
