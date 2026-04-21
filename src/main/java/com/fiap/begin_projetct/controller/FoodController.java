package com.fiap.begin_projetct.controller;

import com.fiap.begin_projetct.model.Food;
import com.fiap.begin_projetct.service.FoodService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@Tag(name = "Alimentos", description = "API para gerenciamento de alimentos")
public class FoodController {
    
    private final FoodService foodService;
    
    @GetMapping
    @Operation(summary = "Listar todos os alimentos", description = "Retorna uma lista com todos os alimentos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de alimentos retornada com sucesso")
    public ResponseEntity<List<Food>> listarTodos() {
        List<Food> foods = foodService.listarTodos();
        return ResponseEntity.ok(foods);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar alimento por ID", description = "Retorna um alimento específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alimento encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Alimento não encontrado")
    })
    public ResponseEntity<Food> buscarPorId(@PathVariable Long id) {
        Optional<Food> food = foodService.buscarPorId(id);
        return food.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar alimento por nome", description = "Retorna um alimento específico pelo seu nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alimento encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Alimento não encontrado")
    })
    public ResponseEntity<Food> buscarPorNome(@PathVariable String nome) {
        Optional<Food> food = foodService.buscarPorNome(nome);
        return food.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar alimentos por parte do nome", description = "Retorna alimentos que contenham o texto informado no nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    public ResponseEntity<List<Food>> buscarPorNomeContaining(@RequestParam String nome) {
        List<Food> foods = foodService.buscarPorNomeContaining(nome);
        return ResponseEntity.ok(foods);
    }
    
    @PostMapping
    @Operation(summary = "Criar novo alimento", description = "Cadastra um novo alimento no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Alimento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou alimento já existe")
    })
    public ResponseEntity<Food> criar(@Valid @RequestBody Food food) {
        Food novoFood = foodService.salvar(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFood);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alimento", description = "Atualiza os dados de um alimento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alimento atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Alimento não encontrado")
    })
    public ResponseEntity<Food> atualizar(@PathVariable Long id, @Valid @RequestBody Food food) {
        Food foodAtualizado = foodService.atualizar(id, food);
        return ResponseEntity.ok(foodAtualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar alimento", description = "Remove um alimento do sistema pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Alimento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Alimento não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        foodService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
