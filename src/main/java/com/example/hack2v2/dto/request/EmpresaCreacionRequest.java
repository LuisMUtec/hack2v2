package com.example.hack2v2.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaCreacionRequest {
    
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El RUC es obligatorio")
    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
    private String ruc;
    
    @NotNull(message = "La fecha de afiliación es obligatoria")
    private LocalDate fechaAfiliacion;
    
    @Valid
    @NotNull(message = "Los datos del administrador son obligatorios")
    private UsuarioAdminRequest administrador;

    public LocalDateTime getfechaAfiliacion() {
        return fechaAfiliacion.atStartOfDay();
    }
}