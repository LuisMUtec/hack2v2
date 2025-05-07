package com.example.hack2v2.service.company;

import com.example.hack2v2.dto.request.UsuarioRequest;
import com.example.hack2v2.dto.response.ConsumoResponse;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.model.entities.Empresa;

import java.util.List;

public interface UsuarioEmpresaService {
    UsuarioResponse crear(UsuarioRequest request);
    List<UsuarioResponse> listarTodos();
    UsuarioResponse obtenerPorId(Long id);
    UsuarioResponse actualizar(Long id, UsuarioRequest request);
    List<ConsumoResponse> consumoUsuario(Long id);
}
