package com.example.hack2v2.dto.response;

import com.example.hack2v2.model.entities.Restriccion;
import lombok.Data;

@Data
public class RestriccionResponse {
    private Long id;
    private Long modeloId;
    private String modeloNombre;
    private Integer limiteSolicitudes;
    private Integer limiteTokens;
    private String periodoReinicio;

    public RestriccionResponse(Restriccion r) {
        this.id                = r.getId();
        this.modeloId          = r.getModelo().getId();
        this.modeloNombre      = r.getModelo().getNombre();
        this.limiteSolicitudes = r.getLimiteSolicitudes();
        this.limiteTokens      = r.getLimiteTokens();
        this.periodoReinicio   = r.getPeriodoReinicio();
    }
}
