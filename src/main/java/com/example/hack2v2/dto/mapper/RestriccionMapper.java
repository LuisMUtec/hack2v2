package com.example.hack2v2.dto.mapper;

import com.example.hack2v2.dto.request.RestriccionRequest;
import com.example.hack2v2.dto.response.RestriccionResponse;
import com.example.hack2v2.model.entities.Restriccion;

public class RestriccionMapper {

    /** Convierte entidad → DTO de respuesta */
    public static RestriccionResponse toResponse(Restriccion r) {
        return new RestriccionResponse(r);
    }

    /** Crea una nueva entidad a partir del DTO de petición */
    public static Restriccion toEntity(RestriccionRequest req) {
        Restriccion r = new Restriccion();
        r.setLimiteSolicitudes(req.getLimiteSolicitudes());
        r.setLimiteTokens(req.getLimiteTokens());
        r.setPeriodoReinicio(req.getPeriodoReinicio());
        return r;
    }

    /** Actualiza sólo los campos editables de la entidad existente */
    public static void updateFromDto(RestriccionRequest req, Restriccion r) {
        r.setLimiteSolicitudes(req.getLimiteSolicitudes());
        r.setLimiteTokens(req.getLimiteTokens());
        r.setPeriodoReinicio(req.getPeriodoReinicio());
    }
}
