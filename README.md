# Refresca SP

O **Refresca SP** é uma iniciativa de CitizenTech desenvolvida para mitigar os impactos das ondas de calor extremo nas grandes cidades. Focando no bem-estar de populações vulneráveis — como trabalhadores ambulantes, pessoas em situação de rua e idosos —, a solução disponibiliza informações em tempo real sobre temperaturas críticas e mapeia pontos de apoio e refúgio urbano (como praças arborizadas, tendas de acolhimento e bebedouros públicos).

### Papel desta API no projeto

Desenvolvida com base nos princípios de Arquitetura Orientada a Serviços (SOA), esta API RESTful é responsável pela gestão e distribuição dos **alertas meteorológicos e de calor extremo**. O microsserviço processa informações climáticas, classifica a gravidade térmica em níveis de risco (`LOW`, `MEDIUM`, `HIGH`, `EXTREME`), gera recomendações preventivas de saúde e expõe esses dados de forma padronizada para que aplicações clientes (como aplicativos móveis e painéis de monitoramento) possam alertar o cidadão tempestivamente.

## Como rodar o projeto

```bash
./mvnw spring-boot:run
```

A API estará disponível em http://localhost:8080. A documentação Swagger estará disponível em http://localhost:8080/swagger-ui/index.html.

## Integrantes

| Nome | RM |
| --- | --- |
| Gustavo Atanazio | 559098 |
| Matheus Vinícius | 555177 |
| Nicolas Borges | 556617 |
| Gustavo Coelho | 556289 |
| Igor Ribeiro | 556010 |

## Endpoints da API

A URL base para os endpoints de alertas é `/api/v1/alerts`.

### 1. Criar alerta

Cria um novo alerta meteorológico com indicação de endereço, nível de severidade e recomendações para a população.

- **URL:** `POST /api/v1/alerts`
- **Dados de entrada (Request Body - JSON):**

```json
{
  "address": {
    "cep": "01310-100",
    "streetName": "Avenida Paulista",
    "number": 1578
  },
  "riskLevel": "HIGH",
  "recommendation": "Evite exposição ao sol entre 10h e 16h. Hidrate-se frequentemente e procure refúgios climatizados.",
  "active": true
}
```

*Descrição dos campos de entrada:*
- `address` (objeto, obrigatório): Dados de localização do sensor ou região afetada.
  - `cep` (string, obrigatório): Código de Endereçamento Postal.
  - `streetName` (string, obrigatório): Nome do logradouro.
  - `number` (integer, obrigatório): Número do local.
- `riskLevel` (string, obrigatório): Nível de criticidade térmica. Valores permitidos: `LOW`, `MEDIUM`, `HIGH`, `EXTREME`.
- `recommendation` (string, obrigatório): Mensagem de orientação preventiva para o cidadão.
- `active` (boolean, opcional): Indica se o alerta está ativo (padrão: `true`).

- **Dados de saída (Response Body - JSON | Status: `201 Created`):**

```json
{
  "alertID": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "address": {
    "cep": "01310-100",
    "streetName": "Avenida Paulista",
    "number": 1578
  },
  "riskLevel": "HIGH",
  "recommendation": "Evite exposição ao sol entre 10h e 16h. Hidrate-se frequentemente e procure refúgios climatizados.",
  "active": true
}
```

---

### 2. Listar alertas

Lista os alertas de calor de forma paginada, permitindo filtro opcional por status de atividade.

- **URL:** `GET /api/v1/alerts`
- **Dados de entrada:**
  - **Parâmetros de consulta (Query Params - Opcionais):**
    - `active` (boolean): Filtra alertas ativos (`true`) ou inativos (`false`).
    - `page` (integer, padrão: `0`): Índice da página requisitada.
    - `size` (integer, padrão: `10`): Quantidade de itens por página.
    - `sort` (string): Critério de ordenação (ex: `createdAt,desc`).
- **Dados de saída (Response Body - JSON | Status: `200 OK`):**

```json
{
  "content": [
    {
      "alertID": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
      "address": {
        "cep": "01310-100",
        "streetName": "Avenida Paulista",
        "number": 1578
      },
      "riskLevel": "HIGH",
      "recommendation": "Evite exposição ao sol entre 10h e 16h. Hidrate-se frequentemente e procure refúgios climatizados.",
      "active": true
    }
  ],
  "page": {
    "size": 10,
    "number": 0,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

---

### 3. Buscar alerta por ID

Obtém os detalhes de um alerta específico a partir do seu identificador único.

- **URL:** `GET /api/v1/alerts/{id}`
- **Dados de entrada:**
  - **Parâmetro de Caminho (Path Variable):**
    - `id` (string, obrigatório): Identificador único do alerta (ex: `a1b2c3d4-e5f6-7890-abcd-ef1234567890`).
- **Dados de saída (Response Body - JSON | Status: `200 OK`):**

```json
{
  "alertID": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "address": {
    "cep": "01310-100",
    "streetName": "Avenida Paulista",
    "number": 1578
  },
  "riskLevel": "HIGH",
  "recommendation": "Evite exposição ao sol entre 10h e 16h. Hidrate-se frequentemente e procure refúgios climatizados.",
  "active": true
}
```

*Em caso de ID não existente, o endpoint retorna status `404 Not Found`:*

```json
{
  "timestamp": "2026-11-06T20:00:00.000000",
  "status": 404,
  "error": "Not Found",
  "message": "Alerta não encontrado com ID: a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

---

### 4. Atualizar alerta

Atualiza integralmente as informações de um alerta existente.

- **URL:** `PUT /api/v1/alerts/{id}`
- **Dados de entrada:**
  - **Parâmetro de Caminho (Path Variable):**
    - `id` (string, obrigatório): Identificador único do alerta a ser atualizado.
  - **Request Body (JSON):**

```json
{
  "address": {
    "cep": "01310-100",
    "streetName": "Avenida Paulista",
    "number": 1578
  },
  "riskLevel": "EXTREME",
  "recommendation": "Situação crítica de calor extremo. Permaneça em ambientes climatizados e hidrate-se continuamente.",
  "active": true
}
```

- **Dados de saída (Response Body - JSON | Status: `200 OK`):**

```json
{
  "alertID": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "address": {
    "cep": "01310-100",
    "streetName": "Avenida Paulista",
    "number": 1578
  },
  "riskLevel": "EXTREME",
  "recommendation": "Situação crítica de calor extremo. Permaneça em ambientes climatizados e hidrate-se continuamente.",
  "active": true
}
```

---

### 5. Excluir alerta

Realiza a exclusão lógica (*soft delete*) de um alerta, alterando seu estado `active` para `false`.

- **URL:** `DELETE /api/v1/alerts/{id}`
- **Dados de entrada:**
  - **Parâmetro de Caminho (Path Variable):**
    - `id` (string, obrigatório): Identificador único do alerta a ser inativado.
- **Dados de saída:**
  - **Status:** `204 No Content` (sem corpo de resposta).

## Pré-requisitos

- **Docker:** Necessário para provisionar e orquestrar automaticamente o contêiner do banco de dados relacional via integração com Spring Boot Docker Compose.
- **Java 25:** Versão do Java Development Kit (JDK) exigida para compilação e execução da aplicação.

## Pacotes Spring e tecnologias utilizadas

| Pacote / Dependência | Descrição e finalidade |
| --- | --- |
| `spring-boot-starter-webmvc` | Fornece a infraestrutura para construção de APIs RESTful utilizando Spring MVC, servlets e serialização JSON. |
| `spring-boot-starter-data-jpa` | Camada de persistência com Spring Data e Hibernate para mapeamento objeto-relacional (ORM) e operações no banco de dados. |
| `spring-boot-starter-security` | Framework de segurança para controle de requisições HTTP, proteção de rotas e políticas de CORS/CSRF. |
| `spring-boot-docker-compose` | Gerencia o ciclo de vida dos contêineres Docker (como PostgreSQL) automaticamente ao iniciar e parar a aplicação. |
| `spring-boot-devtools` | Ferramentas de desenvolvimento que oferecem live reload, reinicialização rápida e conveniências para ambiente local. |
| `springdoc-openapi-starter-webmvc-ui` | Gera a especificação OpenAPI 3 e fornece a interface interativa do Swagger UI para exploração e testes dos endpoints. |
| `spring-boot-starter-flyway` | Mecanismo de controle de versão e migração automatizada do esquema de banco de dados relacional. |
| `flyway-database-postgresql` | Extensão do Flyway com suporte dedicado a bancos de dados PostgreSQL. |
| `postgresql` | Driver JDBC oficial para conectividade com o banco de dados PostgreSQL. |
| `h2` / `spring-boot-h2console` | Banco de dados relacional em memória e console web, utilizado para testes unitários e de integração rápidos. |
| `lombok` | Biblioteca para geração automática em tempo de compilação de getters, setters, construtores e utilitários, reduzindo código boilerplate. |