-- Script para corrigir o tamanho do campo senha na tabela usuarios
-- Problema: VARCHAR(32) é muito pequeno para hash BCrypt (60 caracteres)

-- Verificar o tamanho atual da coluna
DESCRIBE usuarios;

-- Corrigir o tamanho da coluna senha
ALTER TABLE usuarios MODIFY COLUMN senha VARCHAR(255) NOT NULL;

-- Verificar se a correção foi aplicada
DESCRIBE usuarios;

-- Agora pode inserir os usuários sem erro
INSERT INTO usuarios (email, senha, sessao_ativa) 
VALUES ('admin@careplus.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', FALSE)
ON DUPLICATE KEY UPDATE senha = VALUES(senha);

-- Verificar se o usuário foi inserido
SELECT * FROM usuarios WHERE email = 'admin@careplus.com';
