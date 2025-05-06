package com.example.hack2v2.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimiteRequest {
    
    @NotNull(message = "El modelo es obligatorio")
    private Long modeloId;
    
    @NotNull(message = "El límite de solicitudes es obligatorio")
    @Min(value = 1, message = "El límite de solicitudes debe ser al menos 1")
    private Integer limiteSolicitudes;
    
    @NotNull(message = "El límite de tokens es obligatorio")
    @Min(value = 1, message = "El límite de tokens debe ser al menos 1")
    private Integer limiteTokens;
    
    @NotNull(message = "El periodo de reinicio es obligatorio")
    private String periodoReinicio; // "1h", "1d", "7d"
}