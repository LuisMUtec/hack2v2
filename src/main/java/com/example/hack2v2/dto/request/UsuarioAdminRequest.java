package com.example.hack2v2.dto.request;

import com.example.hack2v2.model.enums.RolEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAdminRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreUsuario;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    private Long empresaId;

    @NotNull(message = "El rol es obligatorio")
    private RolEnum rol = RolEnum.ROLE_COMPANY_ADMIN;



}