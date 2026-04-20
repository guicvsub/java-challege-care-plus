# 🔧 Guia para Corrigir Erro de Parse JSON

## 🚨 Erro Identificado
```
JSON parse error: Illegal unquoted character ((CTRL-CHAR, code 9)): 
has to be escaped using backslash to be included in string value
```

## 🔍 Causa do Problema
O erro é causado por **tabulação literal** (`\t`) dentro dos campos JSON do Postman. O caractere `CTRL-CHAR, code 9` representa uma tabulação que não está escapada.

## 🛠️ Como Corrigir

### Opção 1: Corrigir Manualmente no Postman
1. Abra a requisição que está falhando
2. Vá para aba **Body** → **raw** → **JSON**
3. Remova todas as tabulações e substitua por espaços
4. Garanta que não há caracteres especiais não escapados

### Opção 2: Usar JSON Correto
Copie e cole estes JSONs corrigidos:

#### Login Admin:
```json
{
  "email": "admin@careplus.com",
  "senha": "Admin@123"
}
```

#### Login Usuário Regular:
```json
{
  "email": "usuario@careplus.com",
  "senha": "password"
}
```

#### Register User:
```json
{
  "email": "testsenha@careplus.com",
  "senha": "Test@1234"
}
```

#### Criar Paciente:
```json
{
  "nome": "Carlos Silva",
  "cpf": "789.456.123-00",
  "email": "carlos.silva@careplus.com",
  "telefone": "(11) 97777-6666",
  "dataNascimento": "1980-05-20",
  "convenio": "Amil"
}
```

## ✅ Verificação
Antes de enviar, verifique:
1. **Sem tabulações** - use apenas espaços
2. **JSON válido** - use validadores como https://jsonlint.com/
3. **Aspas corretas** - use `"` não `"`
4. **Escape correto** - caracteres especiais devem ser escapados

## 🚀 Teste Rápido
Use este comando para testar se o JSON está válido:
```bash
echo '{"email": "admin@careplus.com", "senha": "Admin@123"}' | python -m json.tool
```

Se não houver erro, o JSON está correto!

## 📋 Requisições Afetadas
As requisições que podem ter este problema:
- Login endpoints
- Register endpoint
- Create/Update paciente endpoint
- SQL injection test endpoints

## 🔧 Solução Permanente
Para evitar este problema no futuro:
1. Configure o Postman para usar **2 espaços** em vez de tabulação
2. Use o modo **Pretty** para edição de JSON
3. Valide o JSON antes de enviar

---
**Status**: 🔄 Aguardando correção dos JSONs no Postman
