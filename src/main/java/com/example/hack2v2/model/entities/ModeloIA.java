package com.example.hack2v2.model.entities;

import com.example.hack2v2.model.enums.TipoModeloEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un modelo de Inteligencia Artificial disponible en la plataforma.
 * Incluye información sobre el tipo de modelo, proveedor, costos y especificaciones técnicas.
 */
@Entity
@Table(name = "modelos_ia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModeloIA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String proveedor;

    @Column(name = "es_multimodal", nullable = false)
    private Boolean esMultimodal = false;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restriccion> restricciones;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitud> solicitudes;
}