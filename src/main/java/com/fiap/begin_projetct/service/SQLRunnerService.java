package com.fiap.begin_projetct.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SQLRunnerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void runInserts() {
        // Alimentos
        jdbcTemplate.execute("INSERT IGNORE INTO foods (id, calories_per100g, carbs, fats, fiber, name, proteins, serving_size, serving_unit, sodium, sugar) VALUES (4, 52, 14, 0.2, 2.4, 'Maçã', 0.3, 100, 'g', 1, 10)");
        jdbcTemplate.execute("INSERT IGNORE INTO foods (id, calories_per100g, carbs, fats, fiber, name, proteins, serving_size, serving_unit, sodium, sugar) VALUES (5, 89, 23, 0.3, 2.6, 'Banana Nanica', 1.1, 100, 'g', 1, 12)");
        jdbcTemplate.execute("INSERT IGNORE INTO foods (id, calories_per100g, carbs, fats, fiber, name, proteins, serving_size, serving_unit, sodium, sugar) VALUES (6, 208, 0, 14, 0, 'Salmão Grelhado', 20, 100, 'g', 59, 0)");
        
        // Paciente de Teste (ID 1)
        jdbcTemplate.execute("INSERT IGNORE INTO pacientes (id, nome, cpf, email, telefone, data_nascimento, convenio) VALUES (1, 'Paciente Teste', '111.111.111-11', 'teste@careplus.com', '11999999999', '1990-01-01', 'CarePlus')");

        // Refeições
        jdbcTemplate.execute("INSERT IGNORE INTO meals (id, name, total_calories, total_carbs, total_fats, total_proteins, patient_id, created_at, meal_date, is_preset) VALUES (1, 'Café da Manhã Fit', 250, 45, 5, 10, 1, NOW(), NOW(), 0)");
        jdbcTemplate.execute("INSERT IGNORE INTO meals (id, name, total_calories, total_carbs, total_fats, total_proteins, patient_id, created_at, meal_date, is_preset) VALUES (2, 'Almoço Proteico', 550, 60, 15, 45, 1, NOW(), NOW(), 0)");
        
        // Planos de Dieta
        jdbcTemplate.execute("INSERT IGNORE INTO diet_plans (id, target_calories, target_carbs, target_fats, target_proteins, patient_id, created_at, start_date, end_date) VALUES (1, 2500, 300, 70, 180, 1, NOW(), '2026-01-01', '2026-12-31')");

        // Usuário Administrador
        jdbcTemplate.execute("INSERT INTO usuarios (email, senha, sessao_ativa, created_at, updated_at) " +
                           "VALUES ('admin@careplus.com', '$2a$10$6IGGc/Ruxt8qK2fgk3LGyOg3L/UKYvdgHuXUpTh002QAdFy1zDcNm', 0, NOW(), NOW()) " +
                           "ON DUPLICATE KEY UPDATE senha = '$2a$10$6IGGc/Ruxt8qK2fgk3LGyOg3L/UKYvdgHuXUpTh002QAdFy1zDcNm'");
    }
}
