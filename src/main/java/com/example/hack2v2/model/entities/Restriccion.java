package com.example.hack2v2.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "restriccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Limite> limites;
}