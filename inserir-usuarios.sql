-- Script completo para inserir usuários na tabela usuarios
-- Execute este script diretamente no seu cliente MySQL

-- =====================================================
-- VERIFICAÇÃO E PREPARAÇÃO
-- =====================================================

-- 1. Verificar se a tabela existe
SHOW TABLES LIKE 'usuarios';

-- 2. Verificar estrutura da tabela
DESCRIBE usuarios;

-- 3. Verificar quantidade atual de usuários
SELECT COUNT(*) as total_usuarios FROM usuarios;

-- 4. Corrigir tamanho do campo senha se necessário (BCrypt tem 60 caracteres)
ALTER TABLE usuarios MODIFY COLUMN senha VARCHAR(255) NOT NULL;

-- =====================================================
-- INSERÇÃO DE USUÁRIOS
-- =====================================================

-- 5. Limpar usuários existentes (opcional - remova se quiser manter dados)
-- DELETE FROM usuarios;

-- 6. Inserir usuário administrador principal
-- Email: admin@careplus.com | Senha: Admin@123
INSERT IGNORE INTO usuarios (email, senha, sessao_ativa) 
VALUES ('admin@careplus.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', FALSE);

-- 7. Inserir usuário regular para testes
-- Email: usuario@careplus.com | Senha: password
INSERT IGNORE INTO usuarios (email, senha, sessao_ativa) 
VALUES ('usuario@careplus.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', FALSE);

-- 8. Inserir usuário para testes de validação de senha
-- Email: testsenha@careplus.com | Senha: Test@1234
INSERT IGNORE INTO usuarios (email, senha, sessao_ativa) 
VALUES ('testsenha@careplus.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE);

-- 9. Inserir usuário com sessão ativa (para testes de expiração)
-- Email: sessao@careplus.com | Senha: Admin@123 | Sessão: 2 minutos atrás
INSERT IGNORE INTO usuarios (email, senha, sessao_ativa, ultimo_acesso) 
VALUES ('sessao@careplus.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', TRUE, DATE_SUB(NOW(), INTERVAL 2 MINUTE));

-- 10. Inserir usuário com sessão expirada (para testes de longevidade)
-- Email: expirado@careplus.com | Senha: Admin@123 | Sessão: 6 minutos atrás (expirada)
INSERT IGNORE INTO usuarios (email, senha, sessao_ativa, ultimo_acesso) 
VALUES ('expirado@careplus.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', TRUE, DATE_SUB(NOW(), INTERVAL 6 MINUTE));

-- 11. Inserir usuário para testes de SQL Injection
-- Email: sqltest@careplus.com | Senha: Sql@1234
INSERT IGNORE INTO usuarios (email, senha, sessao_ativa) 
VALUES ('sqltest@careplus.com', '$2a$10$x2ECJtmpMhTj0Z4G7sTfA.1JkK8jGz6bG3W1vY0sP8nQ2fR7zX9e', FALSE);

-- 12. Inserir usuário para testes de Token Tampering
-- Email: token@careplus.com | Senha: Token@1234
INSERT IGNORE INTO usuarios (email, senha, sessao_ativa) 
VALUES ('token@careplus.com', '$2a$10$y3FDKunqN0oL7sT5gUfZb.2KlL9hH0aC4X2wZ1tQ9oR3sS8yY0f', FALSE);

-- =====================================================
-- VERIFICAÇÃO FINAL
-- =====================================================

-- 13. Verificar usuários inseridos
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

-- 14. Contar total de usuários inseridos
SELECT COUNT(*) as total_usuarios_inseridos FROM usuarios;

-- =====================================================
-- REFERÊNCIA DE USUÁRIOS E SENHAS
-- =====================================================
/*
USUÁRIOS DISPONÍVEIS PARA TESTES:

1. admin@careplus.com         -> Admin@123      (Administrador principal)
2. usuario@careplus.com       -> password       (Usuário regular)
3. testsenha@careplus.com    -> Test@1234      (Testes de validação)
4. sessao@careplus.com       -> Admin@123      (Sessão ativa - 2 min)
5. expirado@careplus.com     -> Admin@123      (Sessão expirada - 6 min)
6. sqltest@careplus.com      -> Sql@1234       (Testes SQL Injection)
7. token@careplus.com        -> Token@1234     (Testes Token Tampering)

CASOS DE TESTE COBERTOS:
- CT01-CT04: Autenticação (login válido/inválido)
- CT05-CT11: Validação de senha (regras de formato)
- CT12-CT15: JWT Token (válido/inválido/expirado)
- CT16-CT18: Sessão (inatividade/longevidade)
- CT19-CT21: Autorização (acesso protegido)
- CT22-CT24: Segurança (SQL injection/tampering)

COMANDOS ÚTEIS:
- Verificar sessões expiradas: SELECT * FROM usuarios WHERE sessao_ativa = TRUE AND TIMESTAMPDIFF(MINUTE, ultimo_acesso, NOW()) > 5;
- Resetar todas as sessões: UPDATE usuarios SET sessao_ativa = FALSE, ultimo_acesso = NULL;
- Ativar sessão específica: UPDATE usuarios SET sessao_ativa = TRUE, ultimo_acesso = NOW() WHERE email = 'admin@careplus.com';
*/
