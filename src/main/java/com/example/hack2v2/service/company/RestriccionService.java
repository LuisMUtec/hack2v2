package com.example.hack2v2.service.company;

import com.example.hack2v2.dto.request.RestriccionRequest;
import com.example.hack2v2.dto.response.RestriccionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestriccionService {
    RestriccionResponse crear(RestriccionRequest request);
    List<RestriccionResponse> listarTodas();
    RestriccionResponse actualizar(Long id, RestriccionRequest request);
    void eliminar(Long id);
}