package com.example.hack2v2.dto.mapper;

import com.example.hack2v2.dto.request.UsuarioRequest;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.model.enums.RolEnum;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request, Empresa empresa) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword()); // Debe ser encriptada antes de guardar
        usuario.setEmpresa(empresa);
        usuario.setRol(RolEnum.ROLE_USER); // Por defecto
        usuario.setActivo(true);
        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .activo(usuario.isActivo())
                .empresaId(usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null)
                .empresaNombre(usuario.getEmpresa() != null ? usuario.getEmpresa().getNombre() : null)
                .build();
    }

    public void updateEntityFromRequest(UsuarioRequest request, Usuario usuario) {
        if (request.getNombre() != null) {
            usuario.setNombre(request.getNombre());
        }
        if (request.getApellido() != null) {
            usuario.setApellido(request.getApellido());
        }
        if (request.getEmail() != null) {
            usuario.setEmail(request.getEmail());
        }
        // La contraseña debe actualizarse con un método específico
        // La empresa no se actualiza aquí porque no debería cambiar
    }
}