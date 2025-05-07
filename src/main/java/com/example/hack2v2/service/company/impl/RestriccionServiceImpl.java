package com.example.hack2v2.service.company.impl;

import com.example.hack2v2.dto.request.RestriccionRequest;
import com.example.hack2v2.dto.response.RestriccionResponse;
import com.example.hack2v2.exception.ResourceNotFoundException;
import com.example.hack2v2.dto.mapper.RestriccionMapper;
import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Restriccion;
import com.example.hack2v2.repository.ModeloIARepository;
import com.example.hack2v2.repository.RestriccionRepository;
import com.example.hack2v2.service.company.RestriccionService;
import com.example.hack2v2.service.company.UsuarioEmpresaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RestriccionServiceImpl implements RestriccionService {

    private final RestriccionRepository restriccionRepo;
    private final ModeloIARepository modeloRepo;
    private final UsuarioEmpresaService usuarioEmpresaService;

    @Override
    public RestriccionResponse crear(RestriccionRequest req) {
        // 1) Obtengo la empresa del contexto
        Empresa empresa = usuarioEmpresaService.obtenerEmpresaDelUsuarioActual();
        // 2) Valido que el modelo exista
        ModeloIA modelo = modeloRepo.findById(req.getModeloId())
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado: " + req.getModeloId()));
        // 3) Mapeo DTO→Entidad y completo empresa/modelo
        Restriccion r = RestriccionMapper.toEntity(req);
        r.setEmpresa(empresa);
        r.setModelo(modelo);
        // 4) Guardo y devuelvo DTO
        Restriccion saved = restriccionRepo.save(r);
        return RestriccionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestriccionResponse> listarTodas() {
        Empresa empresa = usuarioEmpresaService.obtenerEmpresaDelUsuarioActual();
        return restriccionRepo.findByEmpresa(empresa).stream()
                .map(RestriccionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RestriccionResponse actualizar(Long id, RestriccionRequest req) {
        // 1) Cargo la restricción y verifico que exista
        Restriccion r = restriccionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restricción no encontrada: " + id));
        // 2) Verifico que pertenezca a mi empresa
        Long empId = usuarioEmpresaService.obtenerEmpresaIdDelUsuarioActual();
        if (!r.getEmpresa().getId().equals(empId)) {
            throw new ResourceNotFoundException("Restricción no pertenece a tu empresa: " + id);
        }
        // 3) Valido el nuevo modelo
        ModeloIA modelo = modeloRepo.findById(req.getModeloId())
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado: " + req.getModeloId()));
        // 4) Actualizo los campos con el mapper
        RestriccionMapper.updateFromDto(req, r);
        r.setModelo(modelo);
        // 5) Salvo y devuelvo DTO
        Restriccion updated = restriccionRepo.save(r);
        return RestriccionMapper.toResponse(updated);
    }

    @Override
    public void eliminar(Long id) {
        Restriccion r = restriccionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restricción no encontrada: " + id));
        Long empId = usuarioEmpresaService.obtenerEmpresaIdDelUsuarioActual();
        if (!r.getEmpresa().getId().equals(empId)) {
            throw new ResourceNotFoundException("Restricción no pertenece a tu empresa: " + id);
        }
        restriccionRepo.delete(r);
    }
}
