package com.example.hack2v2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoResponse {
    private Long usuarioId;
    private String usuarioNombre;
    private Integer totalTokensConsumidos;
    private Integer totalSolicitudesRealizadas;
    private List<ConsumoModeloDTO> consumoPorModelo;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsumoModeloDTO {
        private Long modeloId;
        private String modeloNombre;
        private Integer tokensConsumidos;
        private Integer solicitudesRealizadas;
        private Integer limiteTokens;
        private Integer limiteSolicitudes;
        private String periodoReinicio;
        private LocalDateTime proximoReinicio;
    }
}