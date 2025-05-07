package com.example.hack2v2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimiteResponse {
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private Long modeloId;
    private String modeloNombre;
    private Integer limiteSolicitudes;
    private Integer limiteTokens;
    private String periodoReinicio;
    private Integer solicitudesUsadas;
    private Integer tokensUsados;
    private LocalDateTime ultimoReinicio;
    private LocalDateTime proximoReinicio;
    private Long restriccionId;
    private Integer maxSolicitudesUsuario;
    private Integer maxTokensUsuario;

}