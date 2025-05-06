package com.example.hack2v2.entities;

import com.example.sparkyai.model.enums.EstadoEmpresaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(unique = true, nullable = false)
    private String ruc;
    
    @Column(nullable = false)
    private LocalDateTime fechaAfiliacion;
    
    @Enumerated(EnumType.STRING)
    private EstadoEmpresaEnum estado;
    
    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Administrador administrador;
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Usuario> usuarios = new ArrayList<>();
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Restriccion> restricciones = new ArrayList<>();
}