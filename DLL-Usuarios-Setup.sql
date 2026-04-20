-- =====================================================
-- Script DDL para Configuração de Usuários - Care Plus API
-- =====================================================
-- Autor: Sistema Care Plus
-- Versão: 1.0
-- Data: 2026-04-20
-- =====================================================

-- Limpar tabela existente (apenas para setup inicial)
-- DROP TABLE IF EXISTS usuarios;

-- Criar tabela de usuários para autenticação
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,  -- Alterado para VARCHAR(255) para acomodar hash BCrypt
    ultimo_acesso DATETIME,
    token_expiracao DATETIME,
    sessao_ativa BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email),
    INDEX idx_sessao_ativa (sessao_ativa),
    INDEX idx_ultimo_acesso (ultimo_acesso)
);

-- =====================================================
-- Inserção de Usuários para Testes e Desenvolvimento
-- =====================================================

-- 1. Usuário Administrador Principal
-- Email: admin@careplus.com
-- Senha: Admin@123
INSERT INTO usuarios (email, senha, sessao_ativa) 
VALUES ('admin@careplus.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', FALSE)
ON DUPLICATE KEY UPDATE senha = VALUES(senha);

-- 2. Usuário Regular para Testes Funcionais
-- Email: usuario@careplus.com
-- Senha: password
INSERT INTO usuarios (email, senha, sessao_ativa) 
VALUES ('usuario@careplus.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', FALSE)
ON DUPLICATE KEY UPDATE senha = VALUES(senha);

-- 3. Usuário para Testes de Validação de Senha
-- Email: testsenha@careplus.com
-- Senha: Test@1234
INSERT INTO usuarios (email, senha, sessao_ativa) 
VALUES ('testsenha@careplus.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE)
ON DUPLICATE KEY UPDATE senha = VALUES(senha);

-- 4. Usuário com Sessão Ativa (para testes de expiração por inatividade)
-- Email: sessao@careplus.com
-- Senha: Admin@123
-- Sessão ativa há 2 minutos (irá expirar em 3 minutos)
INSERT INTO usuarios (email, senha, sessao_ativa, ultimo_acesso) 
VALUES ('sessao@careplus.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', TRUE, DATE_SUB(NOW(), INTERVAL 2 MINUTE))
ON DUPLICATE KEY UPDATE senha = VALUES(senha), sessao_ativa = VALUES(sessao_ativa), ultimo_acesso = VALUES(ultimo_acesso);

-- 5. Usuário com Sessão Expirada (para testes de longevidade)
-- Email: expirado@careplus.com
-- Senha: Admin@123
-- Sessão expirada (>5 minutos)
INSERT INTO usuarios (email, senha, sessao_ativa, ultimo_acesso) 
VALUES ('expirado@careplus.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', TRUE, DATE_SUB(NOW(), INTERVAL 6 MINUTE))
ON DUPLICATE KEY UPDATE senha = VALUES(senha), sessao_ativa = VALUES(sessao_ativa), ultimo_acesso = VALUES(ultimo_acesso);

-- 6. Usuário para Testes de SQL Injection
-- Email: sqltest@careplus.com
-- Senha: Sql@1234
INSERT INTO usuarios (email, senha, sessao_ativa) 
VALUES ('sqltest@careplus.com', '$2a$10$x2ECJtmpMhTj0Z4G7sTfA.1JkK8jGz6bG3W1vY0sP8nQ2fR7zX9e', FALSE)
ON DUPLICATE KEY UPDATE senha = VALUES(senha);

-- 7. Usuário para Testes de Token Tampering
-- Email: token@careplus.com
-- Senha: Token@1234
INSERT INTO usuarios (email, senha, sessao_ativa) 
VALUES ('token@careplus.com', '$2a$10$y3FDKunqN0oL7sT5gUfZb.2KlL9hH0aC4X2wZ1tQ9oR3sS8yY0f', FALSE)
ON DUPLICATE KEY UPDATE senha = VALUES(senha);

-- =====================================================
-- Consultas de Verificação
-- =====================================================

-- Verificar todos os usuários inseridos
SELECT 
    id,
    email,
    sessao_ativa,
    ultimo_acesso,
    created_at,
    CASE 
        WHEN ultimo_acesso IS NOT NULL AND sessao_ativa = TRUE THEN
            CASE 
                WHEN TIMESTAMPDIFF(MINUTE, ultimo_acesso, NOW()) > 5 THEN 'SESSAO_EXPIRADA'
                WHEN TIMESTAMPDIFF(MINUTE, ultimo_acesso, NOW()) > 1 THEN 'SESSAO_INATIVA'
                ELSE 'SESSAO_ATIVA'
            END
        ELSE 'SESSAO_INATIVA'
    END as status_sessao
FROM usuarios 
ORDER BY id;

-- =====================================================
-- Referência de Senhas (para desenvolvimento)
-- =====================================================
/*
Lista de usuários e senhas para testes:

1. admin@careplus.com         -> Admin@123      (Administrador)
2. usuario@careplus.com       -> password       (Usuário regular)
3. testsenha@careplus.com    -> Test@1234      (Testes de validação)
4. sessao@careplus.com       -> Admin@123      (Sessão ativa - 2 min)
5. expirado@careplus.com     -> Admin@123      (Sessão expirada - 6 min)
6. sqltest@careplus.com      -> Sql@1234       (Testes SQL Injection)
7. token@careplus.com        -> Token@1234     (Testes Token Tampering)

Regras de Senha:
- Mínimo 8 caracteres
- Máximo 32 caracteres
- Pelo menos 1 letra maiúscula
- Pelo menos 1 letra minúscula
- Pelo menos 1 número
- Pelo menos 1 caractere especial
*/

-- =====================================================
-- Comandos Úteis para Debug
-- =====================================================

-- Limpar todos os usuários (apenas para desenvolvimento)
-- DELETE FROM usuarios;

-- Resetar sessões de todos os usuários
-- UPDATE usuarios SET sessao_ativa = FALSE, ultimo_acesso = NULL;

-- Verificar sessões expiradas
-- SELECT * FROM usuarios WHERE sessao_ativa = TRUE AND TIMESTAMPDIFF(MINUTE, ultimo_acesso, NOW()) > 5;

-- Ativar sessão de usuário específico
-- UPDATE usuarios SET sessao_ativa = TRUE, ultimo_acesso = NOW() WHERE email = 'admin@careplus.com';
