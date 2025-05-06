package com.example.hack2v2.service.company;

import com.example.hack2v2.dto.request.LimiteRequest;
import com.example.hack2v2.dto.response.ConsumoResponse;
import com.example.hack2v2.dto.response.LimiteResponse;

import java.util.List;

public interface LimiteService {
    LimiteResponse asignarLimite(Long usuarioId, LimiteRequest request, Long empresaId);
    List<LimiteResponse> listarLimitesPorUsuario(Long usuarioId, Long empresaId);
    ConsumoResponse obtenerConsumoUsuario(Long usuarioId, Long empresaId);
    boolean validarYActualizarConsumo(Long usuarioId, Long modeloId, int tokens);
    void reiniciarLimitesVencidos(Long empresaId);
}