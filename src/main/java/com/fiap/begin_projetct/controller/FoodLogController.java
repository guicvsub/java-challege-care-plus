package com.fiap.begin_projetct.controller;

import com.fiap.begin_projetct.model.FoodLog;
import com.fiap.begin_projetct.service.FoodLogService;
import com.fiap.begin_projetct.service.CreateMealRequest;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/food-logs")
@RequiredArgsConstructor
@Tag(name = "Logs de Consumo", description = "API para gerenciamento de logs de consumo alimentar")
public class FoodLogController {
    
    private final FoodLogService foodLogService;
    
    @GetMapping
    @Operation(summary = "Listar todos os logs de consumo", description = "Retorna uma lista com todos os logs de consumo cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de logs retornada com sucesso")
    public ResponseEntity<List<FoodLog>> listarTodos() {
        List<FoodLog> logs = foodLogService.listarTodos();
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar log por ID", description = "Retorna um log específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Log encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Log não encontrado")
    })
    public ResponseEntity<FoodLog> buscarPorId(@PathVariable Long id) {
        Optional<FoodLog> log = foodLogService.buscarPorId(id);
        return log.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Buscar logs por paciente", description = "Retorna todos os logs de consumo de um paciente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso")
    })
    public ResponseEntity<List<FoodLog>> buscarPorPaciente(@PathVariable Long pacienteId) {
        List<FoodLog> logs = foodLogService.getConsumptionHistory(pacienteId);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/paciente/{pacienteId}/periodo")
    @Operation(summary = "Buscar logs por paciente e período", description = "Retorna os logs de consumo de um paciente em um período específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso")
    })
    public ResponseEntity<List<FoodLog>> buscarPorPacienteEPeriodo(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<FoodLog> logs = foodLogService.getConsumptionByDate(pacienteId, dataInicio, dataFim);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/paciente/{pacienteId}/data")
    @Operation(summary = "Buscar logs por paciente e data específica", description = "Retorna os logs de consumo de um paciente em uma data específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso")
    })
    public ResponseEntity<List<FoodLog>> buscarPorPacienteEData(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data) {
        List<FoodLog> logs = foodLogService.getConsumptionBySpecificDate(pacienteId, data);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/paciente/{pacienteId}/planejados")
    @Operation(summary = "Buscar consumo planejado por paciente", description = "Retorna todos os logs de consumo planejado de um paciente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso")
    })
    public ResponseEntity<List<FoodLog>> buscarConsumoPlanejadoPorPaciente(@PathVariable Long pacienteId) {
        List<FoodLog> logs = foodLogService.getPlannedConsumptionByPatient(pacienteId);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/paciente/{pacienteId}/calorias")
    @Operation(summary = "Obter total de calorias consumidas", description = "Retorna o total de calorias consumidas por um paciente em uma data específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total de calorias calculado com sucesso")
    })
    public ResponseEntity<Integer> getTotalCalorias(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data) {
        Integer totalCalorias = foodLogService.getTotalCaloriesByPatientAndDate(pacienteId, data);
        return ResponseEntity.ok(totalCalorias);
    }
    
    @PostMapping("/paciente/{pacienteId}/preset")
    @Operation(summary = "Registrar consumo baseado em preset", description = "Registra o consumo de um alimento baseado em uma refeição planejada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Consumo registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<FoodLog> registrarConsumoPreset(
            @PathVariable Long pacienteId,
            @RequestParam Long mealId,
            @RequestParam Long foodId,
            @RequestParam Double quantity) {
        FoodLog log = foodLogService.logConsumptionFromPreset(pacienteId, mealId, foodId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(log);
    }
    
    @PostMapping("/paciente/{pacienteId}/custom")
    @Operation(summary = "Registrar consumo customizado", description = "Registra o consumo de uma refeição customizada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Consumo registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<FoodLog> registrarConsumoCustomizado(
            @PathVariable Long pacienteId,
            @Valid @RequestBody CreateMealRequest mealRequest) {
        FoodLog log = foodLogService.logCustomConsumption(pacienteId, mealRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(log);
    }
    
    @PostMapping
    @Operation(summary = "Criar novo log de consumo", description = "Cadastra um novo log de consumo no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Log criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<FoodLog> criar(@Valid @RequestBody FoodLog foodLog) {
        FoodLog novoLog = foodLogService.salvar(foodLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLog);
    }
    
    @PutMapping("/{id}/quantidade")
    @Operation(summary = "Atualizar quantidade consumida", description = "Atualiza a quantidade de um log de consumo existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quantidade atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Log não encontrado")
    })
    public ResponseEntity<FoodLog> atualizarQuantidade(
            @PathVariable Long id,
            @RequestParam Double newQuantity) {
        FoodLog logAtualizado = foodLogService.updateConsumption(id, newQuantity);
        return ResponseEntity.ok(logAtualizado);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar log de consumo", description = "Atualiza os dados de um log de consumo existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Log atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Log não encontrado")
    })
    public ResponseEntity<FoodLog> atualizar(@PathVariable Long id, @Valid @RequestBody FoodLog foodLog) {
        if (!foodLogService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        foodLog.setId(id);
        FoodLog logAtualizado = foodLogService.salvar(foodLog);
        return ResponseEntity.ok(logAtualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar log de consumo", description = "Remove um log de consumo do sistema pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Log deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Log não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        foodLogService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
