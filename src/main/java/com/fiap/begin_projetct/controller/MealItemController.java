package com.fiap.begin_projetct.controller;

import com.fiap.begin_projetct.dto.CriarMealItemRequest;
import com.fiap.begin_projetct.model.MealItem;
import com.fiap.begin_projetct.service.MealItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/meal-items")
@RequiredArgsConstructor
@Tag(name = "Itens de Refeição", description = "API para gerenciamento de itens de refeição")
public class MealItemController {
    
    private final MealItemService mealItemService;
    
    @GetMapping
    @Operation(summary = "Listar todos os itens de refeição", description = "Retorna uma lista com todos os itens de refeição cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso")
    public ResponseEntity<List<MealItem>> listarTodos() {
        List<MealItem> items = mealItemService.listarTodos();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID", description = "Retorna um item específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<MealItem> buscarPorId(@PathVariable Long id) {
        Optional<MealItem> item = mealItemService.buscarPorId(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/refeicao/{refeicaoId}")
    @Operation(summary = "Buscar itens por refeição", description = "Retorna todos os itens de uma refeição específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Itens encontrados com sucesso")
    })
    public ResponseEntity<List<MealItem>> buscarPorRefeicao(@PathVariable Long refeicaoId) {
        List<MealItem> items = mealItemService.buscarPorRefeicao(refeicaoId);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/alimento/{alimentoId}")
    @Operation(summary = "Buscar itens por alimento", description = "Retorna todos os itens que contêm um alimento específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Itens encontrados com sucesso")
    })
    public ResponseEntity<List<MealItem>> buscarPorAlimento(@PathVariable Long alimentoId) {
        List<MealItem> items = mealItemService.buscarPorAlimento(alimentoId);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/refeicao/{refeicaoId}/alimento/{alimentoId}")
    @Operation(summary = "Buscar item específico", description = "Retorna um item específico por refeição e alimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<MealItem> buscarPorRefeicaoEAlimento(
            @PathVariable Long refeicaoId,
            @PathVariable Long alimentoId) {
        Optional<MealItem> item = mealItemService.buscarPorRefeicaoEAlimento(refeicaoId, alimentoId);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Criar novo item de refeição", description = "Cadastra um novo item de refeição no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<MealItem> criar(@Valid @RequestBody MealItem mealItem) {
        MealItem novoItem = mealItemService.salvar(mealItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }
    
    @PostMapping("/simples")
    @Operation(summary = "Criar item de refeição com IDs", description = "Cadastra um novo item de refeição usando IDs de refeição e alimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<MealItem> criarComIds(@Valid @RequestBody CriarMealItemRequest request) {
        MealItem novoItem = mealItemService.criarItem(
            request.getRefeicaoId(), request.getAlimentoId(), request.getQuantidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item de refeição", description = "Atualiza os dados de um item de refeição existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<MealItem> atualizar(@PathVariable Long id, @Valid @RequestBody MealItem mealItem) {
        MealItem itemAtualizado = mealItemService.atualizar(id, mealItem);
        return ResponseEntity.ok(itemAtualizado);
    }
    
    @PutMapping("/{id}/quantidade")
    @Operation(summary = "Atualizar quantidade do item", description = "Atualiza apenas a quantidade de um item de refeição")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quantidade atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Quantidade inválida"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<MealItem> atualizarQuantidade(
            @PathVariable Long id,
            @RequestParam Double quantidade) {
        MealItem itemAtualizado = mealItemService.atualizarQuantidade(id, quantidade);
        return ResponseEntity.ok(itemAtualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item de refeição", description = "Remove um item de refeição do sistema pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mealItemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/refeicao/{refeicaoId}")
    @Operation(summary = "Deletar todos os itens de uma refeição", description = "Remove todos os itens de uma refeição específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Itens deletados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Refeição não encontrada")
    })
    public ResponseEntity<Void> deletarPorRefeicao(@PathVariable Long refeicaoId) {
        mealItemService.deletarPorRefeicao(refeicaoId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/contar/refeicao/{refeicaoId}")
    @Operation(summary = "Contar itens por refeição", description = "Retorna o número de itens em uma refeição específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    })
    public ResponseEntity<Map<String, Long>> contarPorRefeicao(@PathVariable Long refeicaoId) {
        Long count = mealItemService.contarPorRefeicao(refeicaoId);
        return ResponseEntity.ok(Map.of("count", count));
    }
    
    @GetMapping("/contar/alimento/{alimentoId}")
    @Operation(summary = "Contar itens por alimento", description = "Retorna o número de itens que contêm um alimento específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    })
    public ResponseEntity<Map<String, Long>> contarPorAlimento(@PathVariable Long alimentoId) {
        Long count = mealItemService.contarPorAlimento(alimentoId);
        return ResponseEntity.ok(Map.of("count", count));
    }
}
