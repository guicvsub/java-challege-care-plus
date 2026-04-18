package com.fiap.begin_projetct.service;

import com.fiap.begin_projetct.model.Paciente;
import com.fiap.begin_projetct.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {
    
    private final PacienteRepository pacienteRepository;
    
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }
    
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }
    
    public Optional<Paciente> buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf);
    }
    
    public Paciente salvar(Paciente paciente) {
        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new RuntimeException("Já existe um paciente com este CPF");
        }
        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            throw new RuntimeException("Já existe um paciente com este e-mail");
        }
        return pacienteRepository.save(paciente);
    }
    
    public Paciente atualizar(Long id, Paciente paciente) {
        return pacienteRepository.findById(id)
                .map(pacienteExistente -> {
                    paciente.setId(id);
                    return pacienteRepository.save(paciente);
                })
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }
    
    public void deletar(Long id) {
        pacienteRepository.findById(id)
                .ifPresentOrElse(pacienteRepository::delete,
                        () -> { throw new RuntimeException("Paciente não encontrado"); });
    }
}
