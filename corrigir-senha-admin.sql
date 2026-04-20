-- Script para corrigir hash da senha admin
-- Problema: Hash BCrypt não corresponde à senha Admin@123

-- 1. Remover usuário admin atual
DELETE FROM usuarios WHERE email = 'admin@careplus.com';

-- 2. Inserir admin com hash correto para Admin@123
INSERT INTO usuarios (email, senha, sessao_ativa) 
VALUES ('admin@careplus.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', FALSE);

-- 3. Verificar se foi inserido corretamente
SELECT id, email, LEFT(senha, 20) as hash_inicio, sessao_ativa 
FROM usuarios 
WHERE email = 'admin@careplus.com';

-- 4. Teste de verificação de hash (executar no Java para confirmar)
/*
Para testar no Java:
String senha = "Admin@123";
String hash = "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.";
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
boolean matches = encoder.matches(senha, hash);
System.out.println("Senha matches: " + matches);
*/
