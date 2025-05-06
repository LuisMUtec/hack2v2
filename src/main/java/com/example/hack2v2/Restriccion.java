package com.example.sparkyai.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "restricciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restriccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private ModeloIA modelo;

    @Column(nullable = false)
    private Integer limiteSolicitudes;

    @Column(nullable = false)
    private Integer limiteTokens;

    @Column(nullable = false)
    private String periodoReinicio; // formato: "1h", "1d", "7d", etc.

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    private boolean activo = true;
}