package com.example.hack2v2.service;

import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Solicitud;
import com.example.hack2v2.model.entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SolicitudService {
    void guardar(Solicitud solicitud);
    List<Solicitud> obtenerPorUsuario(Usuario usuario);
    Integer totalTokensPorUsuarioYModelo(Usuario usuario, ModeloIA modelo);
}
