package com.example.hack2v2.model.entities;

import com.example.hack2v2.model.enums.RolEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private RolEnum rol;
    
    private boolean activo = true;
    
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Limite> limites = new ArrayList<>();
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Solicitud> solicitudes = new ArrayList<>();
}