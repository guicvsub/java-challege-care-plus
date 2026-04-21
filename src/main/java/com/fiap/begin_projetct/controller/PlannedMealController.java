package com.fiap.begin_projetct.controller;

import com.fiap.begin_projetct.model.PlannedMeal;
import com.fiap.begin_projetct.service.PlannedMealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/planned-meals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Refeições Planejadas", description = "API para gerenciamento de refeições planejadas")
public class PlannedMealController {
    
    private final PlannedMealService plannedMealService;
    
    @GetMapping
    @Operation(summary = "Listar todas as refeições planejadas", description = "Retorna uma lista com todas as refeições planejadas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de refeições planejadas retornada com sucesso")
    public ResponseEntity<List<PlannedMeal>> listarTodos() {
        List<PlannedMeal> plannedMeals = plannedMealService.listarTodos();
        return ResponseEntity.ok(plannedMeals);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar refeição planejada por ID", description = "Retorna uma refeição planejada específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Refeição planejada encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Refeição planejada não encontrada")
    })
    public ResponseEntity<PlannedMeal> buscarPorId(@PathVariable Long id) {
        Optional<PlannedMeal> plannedMeal = plannedMealService.buscarPorId(id);
        return plannedMeal.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/plano/{planoId}")
    @Operation(summary = "Buscar refeições planejadas por plano alimentar", description = "Retorna todas as refeições planejadas de um plano alimentar específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Refeições planejadas encontradas com sucesso")
    })
    public ResponseEntity<List<PlannedMeal>> buscarPorPlanoAlimentar(@PathVariable Long planoId) {
        List<PlannedMeal> plannedMeals = plannedMealService.buscarPorPlanoAlimentar(planoId);
        return ResponseEntity.ok(plannedMeals);
    }
    
    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Buscar refeições planejadas por paciente", description = "Retorna todas as refeições planejadas de um paciente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Refeições planejadas encontradas com sucesso")
    })
    public ResponseEntity<List<PlannedMeal>> buscarPorPaciente(@PathVariable Long pacienteId) {
        List<PlannedMeal> plannedMeals = plannedMealService.buscarPorPaciente(pacienteId);
        return ResponseEntity.ok(plannedMeals);
    }
    
    @GetMapping("/paciente/{pacienteId}/data")
    @Operation(summary = "Buscar refeições planejadas por paciente e data", description = "Retorna as refeições planejadas de um paciente em uma data específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Refeições planejadas encontradas com sucesso")
    })
    public ResponseEntity<List<PlannedMeal>> buscarPorPacienteEData(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<PlannedMeal> plannedMeals = plannedMealService.buscarPorPacienteEData(pacienteId, data);
        return ResponseEntity.ok(plannedMeals);
    }
    
    @GetMapping("/paciente/{pacienteId}/periodo")
    @Operation(summary = "Buscar refeições planejadas por paciente e período", description = "Retorna as refeições planejadas de um paciente em um período específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Refeições planejadas encontradas com sucesso")
    })
    public ResponseEntity<List<PlannedMeal>> buscarPorPacienteEPeriodo(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<PlannedMeal> plannedMeals = plannedMealService.buscarPorPacienteEPeriodo(pacienteId, dataInicio, dataFim);
        return ResponseEntity.ok(plannedMeals);
    }
    
    @GetMapping("/tipo/{tipoRefeicao}")
    @Operation(summary = "Buscar refeições planejadas por tipo", description = "Retorna todas as refeições planejadas de um tipo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Refeições planejadas encontradas com sucesso")
    })
    public ResponseEntity<List<PlannedMeal>> buscarPorTipoRefeicao(@PathVariable String tipoRefeicao) {
        List<PlannedMeal> plannedMeals = plannedMealService.buscarPorTipoRefeicao(tipoRefeicao);
        return ResponseEntity.ok(plannedMeals);
    }
    
    @PostMapping
    @Operation(summary = "Criar nova refeição planejada", description = "Cadastra uma nova refeição planejada no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Refeição planejada criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<PlannedMeal> criar(@Valid @RequestBody PlannedMeal plannedMeal) {
        PlannedMeal novaPlannedMeal = plannedMealService.salvar(plannedMeal);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPlannedMeal);
    }
    
    @PostMapping("/simples")
    @Operation(summary = "Criar refeição planejada com IDs", description = "Cadastra uma nova refeição planejada usando IDs de plano alimentar e refeição")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Refeição planejada criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<PlannedMeal> criarComIds(@RequestBody Map<String, Object> request) {
        Long planoId = ((Number) request.get("planoId")).longValue();
        Long refeicaoId = ((Number) request.get("refeicaoId")).longValue();
        LocalDate dataPlanejada = LocalDate.parse(request.get("dataPlanejada").toString());
        String tipoRefeicao = request.get("tipoRefeicao").toString();
        
        PlannedMeal novaPlannedMeal = plannedMealService.criarRefeicaoPlanejada(
            planoId, refeicaoId, dataPlanejada, tipoRefeicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPlannedMeal);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar refeição planejada", description = "Atualiza os dados de uma refeição planejada existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Refeição planejada atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Refeição planejada não encontrada")
    })
    public ResponseEntity<PlannedMeal> atualizar(@PathVariable Long id, @Valid @RequestBody PlannedMeal plannedMeal) {
        PlannedMeal plannedMealAtualizada = plannedMealService.atualizar(id, plannedMeal);
        return ResponseEntity.ok(plannedMealAtualizada);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar refeição planejada", description = "Remove uma refeição planejada do sistema pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Refeição planejada deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Refeição planejada não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        plannedMealService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/plano/{planoId}")
    @Operation(summary = "Deletar refeições planejadas por plano alimentar", description = "Remove todas as refeições planejadas de um plano alimentar específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Refeições planejadas deletadas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Plano alimentar não encontrado")
    })
    public ResponseEntity<Void> deletarPorPlanoAlimentar(@PathVariable Long planoId) {
        plannedMealService.deletarPorPlanoAlimentar(planoId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/contar/plano/{planoId}")
    @Operation(summary = "Contar refeições planejadas por plano alimentar", description = "Retorna o número de refeições planejadas em um plano alimentar específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    })
    public ResponseEntity<Map<String, Long>> contarPorPlanoAlimentar(@PathVariable Long planoId) {
        Long count = plannedMealService.contarPorPlanoAlimentar(planoId);
        return ResponseEntity.ok(Map.of("count", count));
    }
    
    @GetMapping("/contar/paciente/{pacienteId}")
    @Operation(summary = "Contar refeições planejadas por paciente", description = "Retorna o número de refeições planejadas de um paciente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    })
    public ResponseEntity<Map<String, Long>> contarPorPaciente(@PathVariable Long pacienteId) {
        Long count = plannedMealService.contarPorPaciente(pacienteId);
        return ResponseEntity.ok(Map.of("count", count));
    }
}
