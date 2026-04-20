-- Script para verificar e corrigir estrutura da tabela usuarios

-- 1. Verificar estrutura atual da tabela
DESCRIBE usuarios;

-- 2. Verificar se a tabela existe
SHOW TABLES LIKE 'usuarios';

-- 3. Estrutura correta que a tabela deve ter:
/*
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,           -- ID do usuário
    email VARCHAR(255) NOT NULL UNIQUE,            -- Email único
    senha VARCHAR(255) NOT NULL,                   -- Hash BCrypt (60 chars)
    ultimo_acesso DATETIME,                         -- Último acesso
    token_expiracao DATETIME,                       -- Expiração do token
    sessao_ativa BOOLEAN DEFAULT FALSE,             -- Status da sessão
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Data criação
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Data atualização
);
*/

-- 4. Se a tabela não existir, criar com estrutura correta
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ultimo_acesso DATETIME,
    token_expiracao DATETIME,
    sessao_ativa BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email),
    INDEX idx_sessao_ativa (sessao_ativa),
    INDEX idx_ultimo_acesso (ultimo_acesso)
);

-- 5. Se a tabela existir mas campo senha estiver pequeno, corrigir
ALTER TABLE usuarios MODIFY COLUMN senha VARCHAR(255) NOT NULL;

-- 6. Verificar estrutura final
DESCRIBE usuarios;

-- 7. Verificar se há dados
SELECT COUNT(*) as total_usuarios FROM usuarios;

-- 8. Mostrar dados existentes (se houver)
SELECT id, email, sessao_ativa, ultimo_acesso, created_at FROM usuarios ORDER BY id LIMIT 10;
