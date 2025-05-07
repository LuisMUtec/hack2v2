package com.example.hack2v2.dto.mapper;

import com.example.hack2v2.dto.request.EmpresaCreacionRequest;
import com.example.hack2v2.dto.response.EmpresaResponse;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.model.enums.RolEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@Component
public class EmpresaMapper {

    private final UsuarioMapper usuarioMapper;
    
    public Empresa toEntity(EmpresaCreacionRequest request) {
        Empresa empresa = new Empresa();
        empresa.setNombre(request.getNombre());
        empresa.setRuc(request.getRuc());
        empresa.setFechaAfiliacion(request.getfechaAfiliacion());
        empresa.setEstado(true);
        return empresa;
    }
    
    public EmpresaResponse toResponse(Empresa empresa) {
        // Buscar el administrador entre los usuarios
        Usuario admin = empresa.getUsuarios().stream()
                .filter(u -> u.getRol() == RolEnum.ROLE_COMPANY_ADMIN)
                .findFirst()
                .orElse(null);
        
        UsuarioResponse adminResponse = admin != null ? 
                usuarioMapper.toResponse(admin) : null;
        
        return EmpresaResponse.builder()
                .id(empresa.getId())
                .nombre(empresa.getNombre())
                .ruc(empresa.getRuc())
                .fechaAfiliacion(LocalDate.from(empresa.getFechaAfiliacion()))
                .activa(empresa.isEstado())
                .administrador(adminResponse)
                .build();
    }
}