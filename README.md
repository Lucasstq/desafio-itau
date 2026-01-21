# Desafio Itaú — Serviço de Transações e Estatísticas

Este repositório implementa uma API REST em Java + Spring Boot para recepção de transações e cálculo de estatísticas em uma janela deslizante (padrão: últimos 60 segundos), seguindo o enunciado do desafio do Itaú Unibanco.

## Resumo rápido

| Item | Descrição |
|------|-----------|
| Linguagem | Java 17 |
| Framework | Spring Boot 4.0.1 |
| Documentação da API | Swagger/OpenAPI (springdoc-openapi) |
| Modelo de armazenamento | Em memória (HashMap) |
| Endpoints principais | `POST /transacao`, `DELETE /transacao`, `GET /estatistica` |

## Índice

- [Resumo do desafio e requisitos técnicos](#mapeamento-com-o-enunciado-do-desafio)
- [Status de implementação](#status-de-implementação)
- [Contratos (DTOs)](#contratos-dtos--entidades)
- [Endpoints detalhados](#endpoints-detalhado-com-exemplos)
- [Como construir e executar](#como-rodar)
- [Documentação Swagger](#documentação-swagger)
- [Exemplos de requisições (curl)](#exemplos-curl)

## Status de implementação

| Endpoint | Status | Descrição |
|----------|--------|-----------|
| `POST /transacao` | ✅ Implementado | Cria uma nova transação com validações |
| `DELETE /transacao` | ✅ Implementado | Apaga todas as transações |
| `GET /estatistica` | ✅ Implementado | Retorna estatísticas dos últimos 60 segundos |

### Detalhes da implementação

- **POST /transacao**
  - Responde `201 Created` quando transação válida é aceita.
  - Responde `422 Unprocessable Entity` para dados semanticamente inválidos (ex.: valor negativo, data futura).
  - Responde `400 Bad Request` para payloads que disparem `BadRequestException`.

- **DELETE /transacao**
  - Apaga todas as transações armazenadas em memória.
  - Responde `200 OK` após a exclusão.

- **GET /estatistica**
  - Retorna estatísticas agregadas (count, sum, avg, min, max) considerando apenas transações dos últimos 60 segundos.
  - Quando não houver transações no período, retorna todos os valores zerados.

- **Persistência:** Em memória (HashMap) — conforme restrição do desafio.
- **JSON:** Entrada e saída em JSON.

## Mapeamento com o enunciado do desafio

O enunciado pede explicitamente os seguintes endpoints e comportamentos:

### 1) POST /transacao
- Recebe JSON: `{ "valor": 123.45, "dataHora": "2020-08-07T12:34:56.789-03:00" }`
- Aceita apenas transações com valor >= 0, dataHora no passado (não futuro) e campos obrigatórios preenchidos.
- **Respostas esperadas:**
  - `201 Created` — transação aceita ✅
  - `422 Unprocessable Entity` — transação não aceita (ex.: valor negativo / data futura) ✅
  - `400 Bad Request` — JSON inválido / requisição não compreendida ✅

### 2) DELETE /transacao
- Apaga todas as transações em memória e retorna `200 OK` ✅

### 3) GET /estatistica
- Retorna estatísticas (count, sum, avg, min, max) somente com transações dos últimos 60 segundos ✅
- Quando não houver transações, todos os valores devem ser 0 ✅

### Conformidade

✅ **Todas as regras do enunciado estão implementadas:**
- Validações do POST (valor >= 0, data não futura, campos obrigatórios)
- DELETE para limpar todas as transações
- GET com estatísticas calculadas apenas sobre transações dos últimos 60 segundos
- Armazenamento em memória (HashMap) — atende à restrição de não usar bancos externos

## Contratos (DTOs / Entidades)

### TransacaoRequest
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `valor` | `BigDecimal` | Valor da transação (deve ser >= 0) |
| `dataHora` | `OffsetDateTime` | Data/hora da transação (formato ISO-8601 com offset) |

### EstatisticaResponse
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `count` | `Long` | Quantidade de transações nos últimos 60 segundos |
| `sum` | `double` | Soma dos valores das transações |
| `avg` | `double` | Média dos valores das transações |
| `min` | `double` | Menor valor de transação |
| `max` | `double` | Maior valor de transação |

## Endpoints (detalhado com exemplos)

### 1) Criar transação

**Endpoint:** `POST /transacao`

**Request body (JSON):**
```json
{
  "valor": 12.34,
  "dataHora": "2025-01-19T12:34:56Z"
}
```

**Respostas HTTP:**
| Código | Descrição |
|--------|-----------|
| `201 Created` | Transação registrada com sucesso (sem corpo) |
| `422 Unprocessable Entity` | Validação falhou (sem corpo) |
| `400 Bad Request` | Payload inválido (sem corpo) |

> 💡 **Dica:** Use `OffsetDateTime` no formato ISO-8601 (ex.: `2025-01-19T12:34:56Z` ou `2025-01-19T09:34:56-03:00`).

### 2) Deletar todas as transações

**Endpoint:** `DELETE /transacao`

Apaga todas as transações armazenadas em memória.

**Respostas HTTP:**
| Código | Descrição |
|--------|-----------|
| `200 OK` | Todas as transações foram deletadas com sucesso |

### 3) Consultar estatísticas

**Endpoint:** `GET /estatistica`

Retorna estatísticas agregadas das transações dos últimos 60 segundos.

**Response (JSON):**
```json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.456,
  "min": 12.34,
  "max": 123.56
}
```

> Quando não houver transações nos últimos 60 segundos, todos os valores retornados são `0`.

## Como rodar

### Pré-requisitos

- JDK 17+
- Maven 3.6+ (ou use o wrapper incluído `./mvnw`)

### Build

```bash
./mvnw -DskipTests package
```

### Executar em modo desenvolvimento

```bash
./mvnw spring-boot:run
```

### Executar JAR gerado

```bash
java -jar target/desafioitau-0.0.1-SNAPSHOT.jar
```

### Rodar testes

```bash
./mvnw test
```

## Documentação Swagger

A API possui documentação interativa via **Swagger UI** e especificação **OpenAPI 3.0**.

Após iniciar a aplicação, acesse:

| Recurso | URL |
|---------|-----|
| Swagger UI | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| OpenAPI JSON | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) |

## Exemplos (curl)

### Inserir uma transação válida:

```bash
curl -X POST http://localhost:8080/transacao \
  -H "Content-Type: application/json" \
  -d '{"valor": 15.50, "dataHora": "2025-01-19T12:00:00Z"}'
```

### Consultar estatísticas:

```bash
curl http://localhost:8080/estatistica
```

### Deletar todas as transações:

```bash
curl -X DELETE http://localhost:8080/transacao
```

## Estrutura do Projeto

```
src/main/java/dev/eu/desafioitau/
├── DesafioitauApplication.java     # Classe principal
├── config/
│   └── OpenAPIConfig.java          # Configuração Swagger/OpenAPI
├── controller/
│   ├── EstatisticaController.java  # Controller de estatísticas
│   └── TransacaoController.java    # Controller de transações
├── docs/
│   ├── EstatisticaControllerDocs.java  # Documentação OpenAPI (interface)
│   └── TransacaoControllerDocs.java    # Documentação OpenAPI (interface)
├── dto/
│   ├── request/
│   │   └── TransacaoRequest.java   # DTO de entrada
│   └── response/
│       └── EstatisticaResponse.java # DTO de saída
├── entities/
│   └── Transacao.java              # Entidade de domínio
├── exceptions/
│   └── BadRequestException.java    # Exceção customizada
├── repository/
│   └── TransacaoRepository.java    # Repositório em memória
└── service/
    ├── EstatisticaService.java     # Serviço de cálculo de estatísticas
    └── TransacaoService.java       # Serviço de transações
```

## Tecnologias Utilizadas

- **Java 17** — Linguagem de programação
- **Spring Boot 4.0.1** — Framework de aplicação
- **Lombok** — Redução de boilerplate code
- **SpringDoc OpenAPI** — Documentação automática da API (Swagger UI)

## Licença

Este projeto foi desenvolvido como parte de um desafio técnico.

