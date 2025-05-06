package com.example.hack2v2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class EmpresaRequest {

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    private String nombre;


    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
    private String ruc;

    private AdministradorRequest administrador;

    @Data
    public static class AdministradorRequest {
        @NotBlank private String nombre;
        @NotBlank @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
                message = "Email no válido")
        private String email;
        @NotBlank private String password;   // llegará en texto plano; tu servicio la hashea
    }
}
