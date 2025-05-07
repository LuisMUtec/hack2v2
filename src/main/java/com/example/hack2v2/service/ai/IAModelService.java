package com.example.hack2v2.service.ai;

import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.repository.ModeloIARepository;
import org.springframework.stereotype.Service;

@Service
public class IAModelService {

    private final ModeloIARepository modeloIARepository;

    public IAModelService(ModeloIARepository modeloIARepository) {
        this.modeloIARepository = modeloIARepository;
    }

    public ModeloIA obtenerPorNombre(String nombre) {
        return modeloIARepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado: " + nombre));
    }
}
