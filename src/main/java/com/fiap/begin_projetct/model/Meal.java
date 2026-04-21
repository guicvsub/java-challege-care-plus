package com.fiap.begin_projetct.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Paciente patient;
    
    @Column(name = "meal_date", nullable = false)
    private LocalDateTime mealDate;
    
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MealItem> items;
    
    @Column(name = "total_calories")
    private Integer totalCalories;
    
    @Column(name = "total_proteins")
    private Double totalProteins;
    
    @Column(name = "total_carbs")
    private Double totalCarbs;
    
    @Column(name = "total_fats")
    private Double totalFats;
    
    @Column(name = "is_preset", nullable = false)
    private Boolean isPreset = false;
    
    @Column(name = "preset_name")
    private String presetName;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        calculateTotals();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateTotals();
    }
    
    private void calculateTotals() {
        if (items != null) {
            totalCalories = items.stream()
                .mapToInt(item -> item.getCalories())
                .sum();
            totalProteins = items.stream()
                .mapToDouble(item -> item.getProteins())
                .sum();
            totalCarbs = items.stream()
                .mapToDouble(item -> item.getCarbs())
                .sum();
            totalFats = items.stream()
                .mapToDouble(item -> item.getFats())
                .sum();
        }
    }
}
