package com.example.hack2v2.service;

import com.example.hack2v2.dto.mapper.EmpresaMapper;
import com.example.hack2v2.dto.request.EmpresaCreacionRequest;
import com.example.hack2v2.dto.request.UsuarioAdminRequest;
import com.example.hack2v2.dto.response.EmpresaResponse;
import com.example.hack2v2.exception.BadRequestException;
import com.example.hack2v2.exception.ResourceAlreadyExistsException;
import com.example.hack2v2.model.entities.Empresa;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.model.enums.RolEnum;
import com.example.hack2v2.repository.EmpresaRepository;
import com.example.hack2v2.repository.UsuarioRepository;
import com.example.hack2v2.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaMapper empresaMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public EmpresaResponse crearEmpresa(EmpresaCreacionRequest request) {
        // Validar que no exista una empresa con el mismo RUC
        if (empresaRepository.existsByRuc(request.getRuc())) {
            throw new ResourceAlreadyExistsException("Ya existe una empresa con el RUC: " + request.getRuc());
        }

        // Validar que no exista un usuario con el mismo correo o nombre de usuario
        UsuarioAdminRequest adminRequest = request.getAdministrador();
        if (usuarioRepository.existsByCorreo(adminRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Ya existe un usuario con el correo: " + adminRequest.getEmail());
        }

        // Crear la empresa
        Empresa empresa = empresaMapper.toEntity(request);

        // Crear el usuario administrador
        Usuario administrador = new Usuario();
        administrador.setNombreUsuario(adminRequest.getNombreUsuario());
        administrador.setEmail(adminRequest.getEmail());
        administrador.setContrasena(passwordEncoder.encode(adminRequest.getPassword()));
        administrador.setRol(RolEnum.ROLE_COMPANY_ADMIN);

        // Asociar empresa y usuario
        empresa.addUsuario(administrador);

        // Guardar la empresa (cascada al usuario)
        empresa = empresaRepository.save(empresa);

        // Convertir a DTO y devolver
        return empresaMapper.toResponse(empresa);
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponse> listarEmpresas() {
        return empresaRepository.findAll().stream()
                .map(empresaMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Implementaciones de otros métodos requeridos por el controlador
}

