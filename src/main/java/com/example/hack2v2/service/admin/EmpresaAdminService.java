package com.example.hack2v2.service.admin;

import com.example.hack2v2.dto.request.EmpresaRequest;
import com.example.hack2v2.dto.response.EmpresaResponse;
import com.example.hack2v2.dto.response.ConsumoResponse;

import java.util.List;

public interface EmpresaAdminService {

    EmpresaResponse crear(EmpresaRequest request);

    List<EmpresaResponse> listarTodas();

    EmpresaResponse obtener(Long id);

    EmpresaResponse actualizar(Long id, EmpresaRequest request);

    void cambiarEstado(Long id);

    List<ConsumoResponse> consumoEmpresa(Long empresaId);
}
