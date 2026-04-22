-- ==========================================================
-- SCRIPT DE INSERÇÃO DE DADOS - CARE PLUS
-- Rode este script no seu banco de dados 'care_plus_db'
-- ==========================================================

-- 1. INSERTS PARA A TABELA DE PACIENTES
INSERT IGNORE INTO pacientes (nome, cpf, email, telefone, data_nascimento, convenio) VALUES 
('João Silva', '123.456.789-01', 'joao.silva@email.com', '(11) 98765-4321', '1985-05-20', 'Amil'),
('Maria Oliveira', '234.567.890-12', 'maria.oliveira@email.com', '(11) 91234-5678', '1992-08-15', 'Bradesco Saúde'),
('Carlos Souza', '345.678.901-23', 'carlos.souza@email.com', '(21) 99988-7766', '1978-03-10', 'SulAmérica'),
('Ana Costa', '456.789.012-34', 'ana.costa@email.com', '(31) 98877-6655', '1995-12-05', 'Unimed'),
('Ricardo Santos', '567.890.123-45', 'ricardo.santos@email.com', '(41) 97766-5544', '1988-10-30', 'Care Plus');

-- 2. INSERTS PARA A TABELA DE USUÁRIOS
-- Senha para todos: 'Teste@123' (já criptografada com BCrypt)
INSERT IGNORE INTO usuarios (email, senha, sessao_ativa, created_at) VALUES 
('medico1@careplus.com', '$2a$10$QKczI8HMmEHrNfI8ukQXQeXWeaJtgpNNMpXxZM3ombvU9.o3n/Pmi', FALSE, NOW()),
('recepcao@careplus.com', '$2a$10$QKczI8HMmEHrNfI8ukQXQeXWeaJtgpNNMpXxZM3ombvU9.o3n/Pmi', FALSE, NOW()),
('teste.user@careplus.com', '$2a$10$QKczI8HMmEHrNfI8ukQXQeXWeaJtgpNNMpXxZM3ombvU9.o3n/Pmi', FALSE, NOW());

-- ==========================================================
-- FIM DO SCRIPT
-- ==========================================================
