package com.example.hack2v2.model.entities;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restriccion_id", nullable = false)
    private Restriccion restriccion;

    @Column(name = "max_solicitudes_usuario")
    private Integer maxSolicitudesUsuario;

    @Column(name = "max_tokens_usuario")
    private Integer maxTokensUsuario;
}