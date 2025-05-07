package com.example.hack2v2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponse {
    private Long id;
    private String nombre;
    private String ruc;
    private LocalDate fechaAfiliacion;
    private boolean activa;
    private UsuarioResponse administrador;
}