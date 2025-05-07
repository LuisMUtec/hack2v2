package com.example.hack2v2.dto.response;

import com.example.hack2v2.model.entities.Empresa;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmpresaResponse {

    private Long id;
    private String nombre;
    private String ruc;
    private String estado;
    private LocalDateTime fechaAfiliacion;
    private AdministradorDto administrador;

    public EmpresaResponse(Empresa e) {
        this.id              = e.getId();
        this.nombre          = e.getNombre();
        this.ruc             = e.getRuc();
        this.estado          = e.getEstado().name();
        this.fechaAfiliacion = e.getFechaAfiliacion();
        if (e.getAdministrador() != null) {
            this.administrador = new AdministradorDto(e.getAdministrador().getId(),
                    e.getAdministrador().getNombre(),
                    e.getAdministrador().getEmail());
        }
    }

    @Data
    public static class AdministradorDto {
        private final Long id;
        private final String nombre;
        private final String email;
    }
}
