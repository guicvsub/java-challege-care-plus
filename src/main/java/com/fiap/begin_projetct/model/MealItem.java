package com.fiap.begin_projetct.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
    
    @Column(nullable = false)
    private Double quantity; // in grams
    
    @Column(name = "calories", nullable = false)
    private Integer calories;
    
    @Column(name = "proteins", nullable = false)
    private Double proteins;
    
    @Column(name = "carbs", nullable = false)
    private Double carbs;
    
    @Column(name = "fats", nullable = false)
    private Double fats;
    
    @Column(name = "fiber", nullable = false)
    private Double fiber;
    
    @Column(name = "sodium", nullable = false)
    private Double sodium;
    
    @Column(name = "sugar", nullable = false)
    private Double sugar;
    
    @PrePersist
    @PreUpdate
    protected void calculateNutrients() {
        if (food != null && quantity != null) {
            // Calculate nutrients based on quantity (grams)
            double factor = quantity / 100.0; // convert to per-100g basis
            
            calories = (int) (food.getCaloriesPer100g() * factor);
            proteins = food.getProteins() * factor;
            carbs = food.getCarbs() * factor;
            fats = food.getFats() * factor;
            fiber = food.getFiber() * factor;
            sodium = food.getSodium() * factor;
            sugar = food.getSugar() * factor;
        }
    }
}
