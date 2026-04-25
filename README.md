# Java Challenge Care Plus

## Documentos PDF Importantes

### **Requisitos e regras sprint01.pdf**
Este é o **documento principal de requisitos** que contém:
- Todos os requisitos e especificações do projeto
- Regras e diretrizes de desenvolvimento
- Objetivos e entregas da Sprint 01
- Critérios de aceitação
- Requisitos de implementação

### **Sprint01_Testing.pdf**
Este documento contém **todos os requisitos de teste**:
- Cenários de teste e casos de teste
- Procedimentos e metodologias de teste
- Requisitos de garantia de qualidade
- Critérios de validação

## Requisitos do Projeto

### Requisitos Principais (dos arquivos PDF)
- **Obrigatório ler**: `Requisitos e regras sprint01.pdf` - contém todos os requisitos do projeto
- **Obrigatório seguir**: `Sprint01_Testing.pdf` - contém todas as especificações de teste
- Implementar todas as funcionalidades conforme especificado no PDF de requisitos
- Seguir todas as diretrizes de teste do PDF de testes

### Configuração de Desenvolvimento
- **Java Development Kit (JDK) 21** - Versão obrigatória
- **Maven** - Ferramenta de build obrigatória
- IDE (IntelliJ IDEA, Eclipse ou VS Code)
- **Spring Boot** - Framework principal do projeto
- **MySQL** - Banco de dados utilizado

## Como Começar

1. **PRIMEIRO**: Ler `Requisitos e regras sprint01.pdf` completamente
2. **SEGUNDO**: Revisar `Sprint01_Testing.pdf` para requisitos de teste
3. Configurar seu ambiente de desenvolvimento Java
4. Implementar conforme os requisitos dos PDFs
5. Testar conforme as especificações de teste dos PDFs

## Pontos Chave
- Todos os requisitos estão nos arquivos PDF - **não pule a leitura deles**
- As especificações de teste são obrigatórias - **siga exatamente**
- O sucesso do projeto depende de seguir as diretrizes dos PDFs

## Estrutura MVC

### Pacotes Criados
```
src/main/java/com/fiap/begin_projetct/
├── controller/     # Camada de apresentação (Controllers REST)
├── service/        # Camada de lógica de negócio (Services)
├── repository/     # Camada de acesso a dados (Repositories)
├── model/          # Camada de modelo de dados (Entities)
└── BeginProjetctApplication.java
```

### Exemplo de Implementação

#### Model (Paciente.java)
- Entidade JPA para representar pacientes
- Mapeamento para tabela `pacientes`
- Anotações Lombok para redução de código boilerplate

#### Repository (PacienteRepository.java)
- Interface extending JpaRepository
- Métodos personalizados para busca por CPF e e-mail
- Validação de existência de registros

#### Service (PacienteService.java)
- Lógica de negócio para gestão de pacientes
- Validações de regras de negócio
- Operações CRUD completas

#### Controller (PacienteController.java)
- Endpoints REST para API de pacientes
- Operações: GET, POST, PUT, DELETE
- Tratamento de respostas HTTP adequadas

### Endpoints da API

#### Autenticação
- `POST /login` - Efetuar login e obter token JWT

#### Pacientes
- `GET /api/pacientes` - Listar todos os pacientes
- `GET /api/pacientes/{id}` - Buscar paciente por ID
- `GET /api/pacientes/cpf/{cpf}` - Buscar paciente por CPF
- `POST /api/pacientes` - Criar novo paciente
- `PUT /api/pacientes/{id}` - Atualizar paciente
- `DELETE /api/pacientes/{id}` - Deletar paciente

#### Alimentos (Integração Híbrida)
- `GET /api/foods/buscar?nome={nome}` - Buscar alimentos (Banco Local + API Externa)
- `GET /api/foods` - Listar todos os alimentos cadastrados/cacheados
- `POST /api/foods` - Cadastrar alimento manualmente
- `PUT /api/foods/{id}` - Atualizar dados nutricionais

---

## 🥗 Integração FoodData Central (USDA)

O projeto agora integra-se com a API oficial do **FoodData Central (USDA)** para fornecer uma base de dados nutricionais vasta e confiável.

### Como funciona:
1. **Busca Híbrida**: Ao buscar um alimento, o sistema consulta primeiro o banco de dados local.
2. **Fallback para API**: Se menos de 5 resultados forem encontrados localmente, o sistema consulta automaticamente a API do USDA.
3. **Cache Automático**: Novos alimentos retornados pela API são salvos no banco local (`source = 'API'`), evitando chamadas repetitivas e melhorando a performance.

### Configuração Necessária:
Para utilizar a funcionalidade de busca externa, você deve configurar sua chave de API no arquivo `src/main/resources/application.properties`:
```properties
fooddata.api.key=SUA_CHAVE_AQUI
```

---

## Configuração do Banco de Dados

### MySQL
- **Banco**: care_plus_db
- **Porta**: 3306
- **URL**: jdbc:mysql://localhost:3306/care_plus_db
- **DDL Auto**: update (cria/atualiza tabelas automaticamente)

## Como Rodar o Projeto

### Pré-requisitos
- MySQL rodando (usuário: `root`, senha: `0000`)
- Banco `care_plus_db` criado
- Chave de API do FoodData Central configurada

### Comando para Iniciar (Windows - PowerShell)
```powershell
$env:JWT_TOKEN="care-plus-secret-key-2024"; .\mvnw.cmd spring-boot:run
```

### Comando para Iniciar (Linux/Mac)
```bash
JWT_TOKEN="care-plus-secret-key-2024" ./mvnw spring-boot:run
```

> **Nota:** A aplicação roda por padrão na porta `8080`. O Swagger está disponível em `http://localhost:8080/swagger-ui.html`.

## Arquivos do Projeto
- `Requisitos e regras sprint01.pdf` - **DOCUMENTO DE REQUISITOS**
- `Sprint01_Testing.pdf` - **DOCUMENTO DE TESTES**
- `Care-Plus-API.postman_collection.json` - **COLEÇÃO POSTMAN ATUALIZADA**
- `architecture.md` - **DOCUMENTAÇÃO TÉCNICA DE ARQUITETURA**
- `README.md` - Este arquivo de visão geral