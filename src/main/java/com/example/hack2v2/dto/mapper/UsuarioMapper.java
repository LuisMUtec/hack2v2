package com.example.hack2v2.dto.mapper;

import com.example.hack2v2.dto.request.UsuarioRequest;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.model.enums.RolEnum;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre UsuarioRequest/UsuarioResponse y la entidad Usuario.
 */
@Component
public class UsuarioMapper {

    /**
     * Convierte un DTO de petición a la entidad Usuario.
     * La asociación de Empresa se establece en el servicio.
     */
    public Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setEmail(request.getEmail());
        usuario.setContrasena(request.getContrasena()); // se encriptará en el servicio
        // Asignar rol; por defecto ROLE_USER si no se especifica
        if (request.getRol() != null) {
            usuario.setRol(request.getRol().name());
        } else {
            usuario.setRol(RolEnum.ROLE_USER.name());
        }
        return usuario;
    }

    /**
     * Convierte la entidad Usuario a un DTO de respuesta.
     */
    public UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .email(usuario.getCorreo())
                .empresaId(usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null)
                .build();
    }

    /**
     * Actualiza la entidad con los campos no nulos del DTO.
     */
    public void updateEntityFromRequest(UsuarioRequest request, Usuario usuario) {
        if (request.getNombreUsuario() != null) {
            usuario.setNombreUsuario(request.getNombreUsuario());
        }
        if (request.getCorreo() != null) {
            usuario.setCorreo(request.getCorreo());
        }
        if (request.getContrasena() != null && !request.getContrasena().isBlank()) {
            usuario.setContrasena(request.getContrasena());
        }
        if (request.getRol() != null) {
            usuario.setRol(request.getRol().name());
        }
    }
}
