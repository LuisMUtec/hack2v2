package com.example.hack2v2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "limites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Limite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private ModeloIA modelo;
    
    @Column(nullable = false)
    private Integer limiteSolicitudes;
    
    @Column(nullable = false)
    private Integer limiteTokens;
    
    @Column(nullable = false)
    private String periodoReinicio; // formato: "1h", "1d", "7d", etc.
    
    private Integer solicitudesUsadas = 0;
    
    private Integer tokensUsados = 0;
    
    private LocalDateTime ultimoReinicio;
    
    private LocalDateTime proximoReinicio;
}