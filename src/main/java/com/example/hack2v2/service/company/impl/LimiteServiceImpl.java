package com.example.hack2v2.service.company.impl;

import com.example.hack2v2.dto.mapper.LimiteMapper;
import com.example.hack2v2.dto.request.LimiteRequest;
import com.example.hack2v2.dto.response.ConsumoResponse;
import com.example.hack2v2.dto.response.LimiteResponse;
import com.example.hack2v2.exception.BadRequestException;
import com.example.hack2v2.exception.RateLimitExceededException;
import com.example.hack2v2.exception.ResourceNotFoundException;
import com.example.hack2v2.exception.UnauthorizedException;
import com.example.hack2v2.model.entities.Limite;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Solicitud;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.repository.LimiteRepository;
import com.example.hack2v2.repository.ModeloIARepository;
import com.example.hack2v2.repository.SolicitudRepository;
import com.example.hack2v2.repository.UsuarioRepository;
import com.example.hack2v2.service.company.LimiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LimiteServiceImpl implements LimiteService {

    private final LimiteRepository limiteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloIARepository modeloIARepository;
    private final SolicitudRepository solicitudRepository;
    private final LimiteMapper limiteMapper;

    @Override
    @Transactional
    public LimiteResponse asignarLimite(Long usuarioId, LimiteRequest request, Long empresaId) {
        // Verificar que el usuario exista y pertenezca a la empresa
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("No tiene permisos para asignar límites a este usuario");
        }
        
        // Verificar que el modelo exista
        ModeloIA modelo = modeloIARepository.findById(request.getModeloId())
                .orElseThrow(() -> new ResourceNotFoundException("Modelo de IA no encontrado"));
        
        // Buscar si ya existe un límite para este usuario y modelo
        Optional<Limite> limiteExistente = limiteRepository.findByUsuarioAndModelo(usuario, modelo);
        
        Limite limite;
        if (limiteExistente.isPresent()) {
            // Actualizar límite existente
            limite = limiteExistente.get();
            limite.setLimiteSolicitudes(request.getLimiteSolicitudes());
            limite.setLimiteTokens(request.getLimiteTokens());
            limite.setPeriodoReinicio(request.getPeriodoReinicio());
            
            // Reiniciar contadores si las condiciones cambian
            LocalDateTime ahora = LocalDateTime.now();
            limite.setUltimoReinicio(ahora);
            limite.setProximoReinicio(limiteMapper.calcularProximoReinicio(ahora, request.getPeriodoReinicio()));
            limite.setSolicitudesUsadas(0);
            limite.setTokensUsados(0);
        } else {
            // Crear nuevo límite
            limite = limiteMapper.toEntity(request, usuario, modelo);
        }
        
        limite = limiteRepository.save(limite);
        return limiteMapper.toResponse(limite);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LimiteResponse> listarLimitesPorUsuario(Long usuarioId, Long empresaId) {
        // Verificar que el usuario exista y pertenezca a la empresa
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("No tiene permisos para ver los límites de este usuario");
        }
        
        return limiteRepository.findByUsuario(usuario).stream()
                .map(limiteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ConsumoResponse obtenerConsumoUsuario(Long usuarioId, Long empresaId) {
        // Verificar que el usuario exista y pertenezca a la empresa
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("No tiene permisos para ver el consumo de este usuario");
        }
        
        // Obtener todos los límites del usuario
        List<Limite> limites = limiteRepository.findByUsuario(usuario);
        
        // Calcular totales
        int totalTokensConsumidos = limites.stream().mapToInt(Limite::getTokensUsados).sum();
        int totalSolicitudesRealizadas = limites.stream().mapToInt(Limite::getSolicitudesUsadas).sum();
        
        // Calcular consumo por modelo
        List<ConsumoResponse.ConsumoModeloDTO> consumoPorModelo = limites.stream()
                .map(limite -> ConsumoResponse.ConsumoModeloDTO.builder()
                        .modeloId(limite.getModelo().getId())
                        .modeloNombre(limite.getModelo().getNombre())
                        .tokensConsumidos(limite.getTokensUsados())
                        .solicitudesRealizadas(limite.getSolicitudesUsadas())
                        .limiteTokens(limite.getLimiteTokens())
                        .limiteSolicitudes(limite.getLimiteSolicitudes())
                        .periodoReinicio(limite.getPeriodoReinicio())
                        .proximoReinicio(limite.getProximoReinicio())
                        .build())
                .collect(Collectors.toList());
        
        return ConsumoResponse.builder()
                .usuarioId(usuario.getId())
                .usuarioNombre(usuario.getNombre() + " " + usuario.getApellido())
                .totalTokensConsumidos(totalTokensConsumidos)
                .totalSolicitudesRealizadas(totalSolicitudesRealizadas)
                .consumoPorModelo(consumoPorModelo)
                .build();
    }

    @Override
    @Transactional
    public boolean validarYActualizarConsumo(Long usuarioId, Long modeloId, int tokens) {
        // Verificar que el usuario exista
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        // Verificar que el modelo exista
        ModeloIA modelo = modeloIARepository.findById(modeloId)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo de IA no encontrado"));
        
        // Buscar límite para este usuario y modelo
        Limite limite = limiteRepository.findByUsuarioAndModelo(usuario, modelo)
                .orElseThrow(() -> new BadRequestException("El usuario no tiene límites definidos para este modelo"));
        
        // Verificar y actualizar el límite (lógica de rolling window)
        actualizarLimiteRollingWindow(limite);
        
        // Verificar si excede el límite
        if (limite.getTokensUsados() + tokens > limite.getLimiteTokens()) {
            throw new RateLimitExceededException("Se ha excedido el límite de tokens para este modelo");
        }
        
        if (limite.getSolicitudesUsadas() + 1 > limite.getLimiteSolicitudes()) {
            throw new RateLimitExceededException("Se ha excedido el límite de solicitudes para este modelo");
        }
        
        // Actualizar consumo
        limite.setTokensUsados(limite.getTokensUsados() + tokens);
        limite.setSolicitudesUsadas(limite.getSolicitudesUsadas() + 1);
        limiteRepository.save(limite);
        
        return true;
    }

    // Implementación del "rolling window" pattern
    private void actualizarLimiteRollingWindow(Limite limite) {
        LocalDateTime ahora = LocalDateTime.now();
        
        // Si ya pasó el tiempo de reinicio, resetear los contadores
        if (ahora.isAfter(limite.getProximoReinicio())) {
            limite.setTokensUsados(0);
            limite.setSolicitudesUsadas(0);
            limite.setUltimoReinicio(ahora);
            limite.setProximoReinicio(limiteMapper.calcularProximoReinicio(ahora, limite.getPeriodoReinicio()));
            limiteRepository.save(limite);
        }
    }

    @Override
    @Scheduled(fixedRate = 60000) // Ejecutar cada minuto
    @Transactional
    public void reiniciarLimitesVencidos() {
        LocalDateTime ahora = LocalDateTime.now();
        List<Limite> limites = limiteRepository.findAll();
        
        for (Limite limite : limites) {
            if (ahora.isAfter(limite.getProximoReinicio())) {
                limite.setTokensUsados(0);
                limite.setSolicitudesUsadas(0);
                limite.setUltimoReinicio(ahora);
                limite.setProximoReinicio(limiteMapper.calcularProximoReinicio(ahora, limite.getPeriodoReinicio()));
            }
        }
        
        limiteRepository.saveAll(limites);
    }
}