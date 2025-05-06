package com.example.hack2v2.service.company;

import com.example.hack2v2.dto.request.UsuarioRequest;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.model.entities.Empresa;

import java.util.List;

public interface UsuarioEmpresaService {
    UsuarioResponse crearUsuario(UsuarioRequest request, Long empresaId);
    List<UsuarioResponse> listarUsuariosPorEmpresa(Long empresaId);
    UsuarioResponse obtenerUsuario(Long id, Long empresaId);
    UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request, Long empresaId);
    void cambiarEstadoUsuario(Long id, boolean activo, Long empresaId);
    Empresa obtenerEmpresaDelUsuarioActual();
    Long obtenerEmpresaIdDelUsuarioActual();
}