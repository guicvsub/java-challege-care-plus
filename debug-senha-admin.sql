-- Script para debug do problema de senha admin

-- 1. Verificar o hash atual do admin no banco
SELECT email, senha, sessao_ativa, ultimo_acesso 
FROM usuarios 
WHERE email = 'admin@careplus.com';

-- 2. Testar se o hash corresponde à senha Admin@123
-- Hash esperado para Admin@123 com BCrypt (fator 10):
-- $2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.

-- 3. Comparar com hash no banco
SELECT 
    email,
    senha as hash_banco,
    '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.' as hash_esperado,
    CASE 
        WHEN senha = '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.' 
        THEN 'HASH CORRETO'
        ELSE 'HASH INCORRETO'
    END as status_hash
FROM usuarios 
WHERE email = 'admin@careplus.com';

-- 4. Atualizar hash do admin se necessário
UPDATE usuarios 
SET senha = '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.'
WHERE email = 'admin@careplus.com';

-- 5. Verificar após atualização
SELECT email, senha, sessao_ativa 
FROM usuarios 
WHERE email = 'admin@careplus.com';

-- 6. Listar todos os usuários para verificação
SELECT id, email, LEFT(senha, 20) as hash_inicio, sessao_ativa 
FROM usuarios 
ORDER BY id;
