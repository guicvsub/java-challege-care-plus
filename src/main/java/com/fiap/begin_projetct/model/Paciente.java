package com.fiap.begin_projetct.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String cpf;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String telefone;
    
    @Column(name = "data_nascimento")
    private String dataNascimento;
    
    private String convenio;
}
