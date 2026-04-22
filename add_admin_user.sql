-- Script para adicionar usuário administrador
-- Email: admin@careplus.com
-- Senha: Admin@123 (BCrypt hash)

INSERT INTO `usuarios` (`email`, `senha`, `sessao_ativa`, `created_at`, `updated_at`) 
VALUES ('admin@careplus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOn2', 0, NOW(), NOW())
ON DUPLICATE KEY UPDATE senha = '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOn2';
