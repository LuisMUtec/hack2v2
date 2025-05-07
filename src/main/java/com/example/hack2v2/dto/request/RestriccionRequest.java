package com.example.hack2v2.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Petición para crear o actualizar una restricción.
 */
@Data
public class RestriccionRequest {
    @NotNull(message = "El modelo es obligatorio")
    private Long modeloId;

    @NotNull @Positive(message = "El límite de solicitudes debe ser > 0")
    private Integer limiteSolicitudes;

    @NotNull @Positive(message = "El límite de tokens debe ser > 0")
    private Integer limiteTokens;

    @NotNull(message = "El periodo de reinicio es obligatorio")
    private String periodoReinicio;
}