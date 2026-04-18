package com.fiap.begin_projetct.controller;

import com.fiap.begin_projetct.model.Paciente;
import com.fiap.begin_projetct.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PacienteController {
    
    private final PacienteService pacienteService;
    
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        return paciente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Paciente> buscarPorCpf(@PathVariable String cpf) {
        Optional<Paciente> paciente = pacienteService.buscarPorCpf(cpf);
        return paciente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Paciente> criar(@RequestBody Paciente paciente) {
        try {
            Paciente novoPaciente = pacienteService.salvar(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @RequestBody Paciente paciente) {
        try {
            Paciente pacienteAtualizado = pacienteService.atualizar(id, paciente);
            return ResponseEntity.ok(pacienteAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            pacienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
