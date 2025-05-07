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
public class UsuarioRequest {

    @NotBlank
    private String nombreUsuario;
    @Email
    private String email;
    @NotBlank
    private String contrasena;
    @NotNull
    private RolEnum rol;


}