package com.example.hack2v2.service.company.impl;

import com.example.hack2v2.dto.mapper.UsuarioMapper;
import com.example.hack2v2.dto.request.UsuarioRequest;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.dto.response.ConsumoResponse;
import com.example.hack2v2.exception.BadRequestException;
import com.example.hack2v2.exception.ResourceNotFoundException;
import com.example.hack2v2.exception.UnauthorizedException;
import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.repository.EmpresaRepository;
import com.example.hack2v2.repository.UsuarioRepository;
import com.example.hack2v2.repository.SolicitudRepository;
import com.example.hack2v2.service.company.UsuarioEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioEmpresaServiceImpl implements UsuarioEmpresaService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final SolicitudRepository solicitudRepository;

    @Override
    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {
        Long empresaId = obtenerEmpresaIdDelUsuarioActual();
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));

        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new BadRequestException("El correo ya está en uso");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setEmpresa(empresa);
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        Long empresaId = obtenerEmpresaIdDelUsuarioActual();
        return usuarioRepository.findByEmpresaId(empresaId).stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        Long empresaId = obtenerEmpresaIdDelUsuarioActual();
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("Sin permiso");
        }
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        Long empresaId = obtenerEmpresaIdDelUsuarioActual();
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("Sin permiso");
        }

        if (request.getCorreo() != null && !request.getCorreo().equals(usuario.getCorreo())) {
            usuarioRepository.findByCorreo(request.getCorreo())
                    .filter(u -> !u.getId().equals(id))
                    .ifPresent(u -> { throw new BadRequestException("Correo en uso"); });
            usuario.setCorreo(request.getCorreo());
        }

        usuarioMapper.updateEntityFromRequest(request, usuario);
        if (request.getContrasena() != null && !request.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }

        Usuario updated = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsumoResponse> consumoUsuario(Long userId) {
        Long empresaId = obtenerEmpresaIdDelUsuarioActual();
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("Sin permiso");
        }
        return solicitudRepository.sumarPorModeloYUsuario(userId);
    }

    /** Obtiene el ID de la empresa asociada al usuario autenticado. */
    private Long obtenerEmpresaIdDelUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UnauthorizedException("Usuario no autenticado"));
        return usuario.getEmpresa().getId();
    }
}
