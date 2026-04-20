# Guia Rápido - Care Plus API Postman Collection

## Arquivos Criados

1. **Care-Plus-API-Complete.postman_collection.json** - Coleção completa
2. **Care-Plus-Environment.postman_environment.json** - Ambiente configurado
3. **POSTMAN-QUICK-GUIDE.md** - Este guia

## Como Importar

### 1. Importar Coleção
- Abra Postman
- **Import** > **File** > **Upload Files**
- Selecione: `Care-Plus-API-Complete.postman_collection.json`

### 2. Importar Ambiente  
- **Import** > **File** > **Upload Files**
- Selecione: `Care-Plus-Environment.postman_environment.json`

### 3. Selecionar Ambiente
- No dropdown superior direito, escolha **"Care Plus API Environment"**

## Ordem de Execução Recomendada

### 1. Autenticação
1. **POST /login - Login Válido** - Obtém token JWT
2. **POST /login - Login Inválido** - Testa erro
3. **POST /login - Campos Vazios** - Testa validação

### 2. CRUD Pacientes
1. **GET /api/pacientes - Listar Todos** - Lista pacientes
2. **GET /api/pacientes/{id} - Buscar por ID** - Busca paciente
3. **POST /api/pacientes - Criar Novo** - Cria paciente
4. **PUT /api/pacientes/{id} - Atualizar** - Atualiza paciente
5. **DELETE /api/pacientes/{id} - Deletar** - Deleta paciente

### 3. Testes de Segurança
1. **GET /api/pacientes - Sem Token** - Testa 401
2. **GET /api/pacientes - Token Inválido** - Testa 401
3. **GET /api/pacientes - Token Malformado** - Testa 401

### 4. Validação de Token
1. **Validar Estrutura do Token** - Valida claims JWT

### 5. Documentação
1. **Swagger UI** - Interface web
2. **OpenAPI JSON** - Especificação

## Credenciais de Teste

```json
{
  "login": "usuario1@careplus.com",
  "senha": "minha_senha_secreta"
}
```

## Variáveis Automáticas

| Variável | Descrição |
|----------|-----------|
| `jwt_token` | Token JWT (salvo automaticamente) |
| `created_patient_id` | ID do paciente criado |
| `baseUrl` | http://localhost:8080 |
| `valid_email` | usuario1@careplus.com |
| `valid_password` | minha_senha_secreta |

## Scripts Automáticos

- **Login**: Salva token JWT automaticamente
- **POST**: Salva ID do paciente criado
- **DELETE**: Usa ID do paciente criado
- **Validação**: Verifica estrutura do token
- **Logs**: Console detalhado para debug

## Respostas Esperadas

### Login Sucesso (200)
```json
{
  "tokenJWT": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Lista Pacientes (200)
```json
[
  {
    "id": 1,
    "nome": "Ana Santos Atualizada",
    "cpf": "456.789.123-00",
    "email": "ana.atualizada@email.com",
    "telefone": "(11) 98888-7777",
    "dataNascimento": "1985-03-15",
    "convenio": "Bradesco",
    "createdAt": "2026-04-19T11:31:02.733158",
    "updatedAt": "2026-04-19T11:31:26.274269"
  }
]
```

### Erro (401/403/400)
```json
{
  "error": "Unauthorized",
  "message": "Descrição do erro"
}
```

## Dicas

1. **Execute na ordem**: Comece pelo login para obter token
2. **Verifique logs**: Console do Postman mostra detalhes
3. **Tokens expiram**: 30 minutos após geração
4. **ID automático**: DELETE usa ID do paciente criado

## Troubleshooting

### 401 Unauthorized
- Execute o login primeiro
- Verifique se o token foi salvo
- Confirme o header Authorization

### 403 Forbidden  
- Verifique credenciais no banco
- Confirme que o usuário existe

### 500 Internal Error
- Verifique logs da aplicação
- Confirme conexão com banco

---

**Status**: 100% Funcional  
**Baseado em**: Auto_Escola_3ESPB JWT Pattern  
**Data**: 20/04/2026
