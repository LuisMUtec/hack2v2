package com.example.hack2v2.service.company.impl;

import com.example.hack2v2.dto.mapper.UsuarioMapper;
import com.example.hack2v2.dto.request.UsuarioRequest;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.exception.BadRequestException;
import com.example.hack2v2.exception.ResourceNotFoundException;
import com.example.hack2v2.exception.UnauthorizedException;
import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.repository.EmpresaRepository;
import com.example.hack2v2.repository.UsuarioRepository;
import com.example.hack2v2.service.auth.JwtService;
import com.example.hack2v2.service.company.UsuarioEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioEmpresaServiceImpl implements UsuarioEmpresaService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UsuarioResponse crearUsuario(UsuarioRequest request, Long empresaId) {
        // Verificar que la empresa exista
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
        
        // Verificar que el email no esté en uso
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("El email ya está en uso");
        }
        
        // Crear el usuario
        Usuario usuario = usuarioMapper.toEntity(request, empresa);
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuariosPorEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
        
        return usuarioRepository.findByEmpresa(empresa).stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerUsuario(Long id, Long empresaId) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        // Verificar que el usuario pertenezca a la empresa
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("No tiene permisos para acceder a este usuario");
        }
        
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request, Long empresaId) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        // Verificar que el usuario pertenezca a la empresa
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("No tiene permisos para actualizar este usuario");
        }
        
        // Verificar si el nuevo email no está en uso por otro usuario
        if (request.getEmail() != null && !request.getEmail().equals(usuario.getEmail())) {
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(request.getEmail());
            if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(id)) {
                throw new BadRequestException("El email ya está en uso por otro usuario");
            }
        }
        
        // Actualizar los campos
        usuarioMapper.updateEntityFromRequest(request, usuario);
        
        // Actualizar contraseña si se proporciona
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    @Transactional
    public void cambiarEstadoUsuario(Long id, boolean activo, Long empresaId) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        // Verificar que el usuario pertenezca a la empresa
        if (!usuario.getEmpresa().getId().equals(empresaId)) {
            throw new UnauthorizedException("No tiene permisos para modificar este usuario");
        }
        
        // No permitir desactivar al administrador de la empresa
        Optional<Administrador> admin = administradorRepository.findByUsuario(usuario);
        if (admin.isPresent() && !activo) {
            throw new BadRequestException("No se puede desactivar al administrador de la empresa");
        }
        
        usuario.setActivo(activo);
        usuarioRepository.save(usuario);
    }

    @Override
    public Empresa obtenerEmpresaDelUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Usuario no autenticado"));
        
        return usuario.getEmpresa();
    }

    @Override
    public Long obtenerEmpresaIdDelUsuarioActual() {
        return obtenerEmpresaDelUsuarioActual().getId();
    }
}