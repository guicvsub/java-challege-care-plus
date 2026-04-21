package com.fiap.begin_projetct.service;

import com.fiap.begin_projetct.model.Food;
import com.fiap.begin_projetct.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    
    private final FoodRepository foodRepository;
    private final NutritionService nutritionService;
    
    public List<Food> listarTodos() {
        return foodRepository.findAll();
    }
    
    public Optional<Food> buscarPorId(Long id) {
        return foodRepository.findById(id);
    }
    
    public Optional<Food> buscarPorNome(String name) {
        return foodRepository.findByName(name);
    }
    
    public Food salvar(Food food) {
        nutritionService.validateFoodNutrients(food);
        
        if (foodRepository.existsByName(food.getName())) {
            throw new IllegalArgumentException("Alimento com nome '" + food.getName() + "' já existe");
        }
        
        return foodRepository.save(food);
    }
    
    public Food atualizar(Long id, Food food) {
        Optional<Food> foodExistente = foodRepository.findById(id);
        if (foodExistente.isEmpty()) {
            throw new IllegalArgumentException("Alimento não encontrado com ID: " + id);
        }
        
        nutritionService.validateFoodNutrients(food);
        
        Food foodAtualizado = foodExistente.get();
        
        if (!foodAtualizado.getName().equals(food.getName()) && 
            foodRepository.existsByName(food.getName())) {
            throw new IllegalArgumentException("Alimento com nome '" + food.getName() + "' já existe");
        }
        
        foodAtualizado.setName(food.getName());
        foodAtualizado.setCaloriesPer100g(food.getCaloriesPer100g());
        foodAtualizado.setProteins(food.getProteins());
        foodAtualizado.setCarbs(food.getCarbs());
        foodAtualizado.setFats(food.getFats());
        foodAtualizado.setFiber(food.getFiber());
        foodAtualizado.setSodium(food.getSodium());
        foodAtualizado.setSugar(food.getSugar());
        foodAtualizado.setServingSize(food.getServingSize());
        foodAtualizado.setServingUnit(food.getServingUnit());
        
        return foodRepository.save(foodAtualizado);
    }
    
    public void deletar(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new IllegalArgumentException("Alimento não encontrado com ID: " + id);
        }
        foodRepository.deleteById(id);
    }
    
    public List<Food> buscarPorNomeContaining(String nome) {
        return foodRepository.findByNameContainingIgnoreCase(nome);
    }
}
