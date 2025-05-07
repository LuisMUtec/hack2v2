package com.example.hack2v2.dto.response;

import com.example.hack2v2.model.enums.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private Long empresaId;
    private String empresaNombre;
    private String nombreUsuario;
    private String email;
    private RolEnum rol;

}