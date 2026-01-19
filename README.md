# Desafio Itaú — Serviço de Transações e Estatísticas

Este repositório implementa uma API REST em Java + Spring Boot para recepção de transações e cálculo de estatísticas em uma janela deslizante (padrão: últimos 60 segundos), seguindo o enunciado do desafio do Itaú Unibanco.

Resumo rápido

- Linguagem: Java 17
- Framework: Spring Boot
- Modelo de armazenamento: em memória (HashMap)
- Endpoints principais implementados: POST /transacao, DELETE /transacao/{id} (nota: ver observação), GET /estatistica

Planejamento (o que vou cobrir neste README)

- Resumo do desafio e requisitos técnicos
- Mapeamento entre o enunciado do desafio e o que está implementado
- Como construir e executar a aplicação
- Exemplos de requisições (curl)
- Observações, limitações e próximos passos

Status de implementação (resumo)

- POST /transacao — Implementado (com validações)
  - Responde 201 quando transação válida é aceita.
  - Responde 422 para dados válidos semanticamente inválidos (por exemplo, valor negativo, data futura).
  - Responde 400 para payloads que disparem BadRequestException.
- DELETE /transacao — Diferente do enunciado: implementado como DELETE /transacao/{id} (exclui uma transação por id). Planejado: adicionar endpoint DELETE /transacao para limpar todas as transações.
- GET /estatistica — Implementado: retorna estatísticas agregadas considerando apenas transações dos últimos 60 segundos.
- Persistência: em memória (conforme restrição do desafio).
- JSON: entrada e saída em JSON.

Mapeamento com o enunciado do desafio

O enunciado pede explicitamente os seguintes endpoints e comportamentos:

1) POST /transacao
- Recebe JSON: { "valor": 123.45, "dataHora": "2020-08-07T12:34:56.789-03:00" }
- Aceita apenas transações com valor >= 0, dataHora no passado (não futuro) e campos obrigatórios preenchidos.
- Respostas esperadas:
  - 201 Created — transação aceita
  - 422 Unprocessable Entity — transação não aceita (ex.: valor negativo / fora da janela)
  - 400 Bad Request — JSON inválido / requisição não compreendida

2) DELETE /transacao
- Deve apagar todas as transações em memória e retornar 200 OK

3) GET /estatistica
- Retorna estatísticas (count, sum, avg, min, max) somente com transações dos últimos 60 segundos
- Quando não houver transações, todos os valores devem ser 0

Observações sobre conformidade

- A maioria das regras do POST e do GET está implementada conforme o desafio.
- O repositório armazena dados em memória (HashMap) — isso atende à restrição de não usar bancos externos.

Contratos (DTOs / Entidades)

- TransacaoRequest
  - valor: BigDecimal
  - dataHora: OffsetDateTime (formato ISO-8601 com offset)

- EstatisticaResponse
  - count: Long
  - sum: BigDecimal
  - avg: BigDecimal
  - min: BigDecimal
  - max: BigDecimal

Endpoints (detalhado com exemplos)

1) Criar transação

- POST /transacao
- Request body (JSON):

  {
    "valor": 12.34,
    "dataHora": "2025-01-19T12:34:56Z"
  }

- Possíveis respostas HTTP:
  - 201 Created — transação registrada com sucesso (sem corpo)
  - 422 Unprocessable Entity — validação falhou (sem corpo)
  - 400 Bad Request — payload inválido (sem corpo)

Dica: use OffsetDateTime no formato ISO-8601 (ex.: 2025-01-19T12:34:56Z ou 2025-01-19T09:34:56-03:00).

2) Deletar transação (implementado atualmente)

- DELETE /transacao/{id}
- Remove a transação com o id informado e retorna 200 OK.
- Nota: o enunciado pede `DELETE /transacao` sem id para apagar todas as transações — isso é uma diferença conhecida e planejada para correção.

3) Consultar estatísticas

- GET /estatistica
- Retorna 200 OK com JSON:

  {
    "count": 10,
    "sum": 1234.56,
    "avg": 123.456,
    "min": 12.34,
    "max": 123.56
  }

- Quando não houver transações nos últimos 60 segundos, todos os valores retornados são zero.

Como rodar

Pré-requisitos

- JDK 17
- Maven (ou use o wrapper incluído `./mvnw`)

Build

```bash
./mvnw -DskipTests package
```

Executar em modo desenvolvimento

```bash
./mvnw spring-boot:run
```

Executar JAR gerado

```bash
java -jar target/desafioitau-0.0.1-SNAPSHOT.jar
```

Rodar testes

```bash
./mvnw test
```

Exemplos (curl)

Inserir uma transação válida:

```bash
curl -X POST http://localhost:8080/transacao \
  -H "Content-Type: application/json" \
  -d '{"valor": 15.50, "dataHora": "2025-01-19T12:00:00Z"}'
```

Consultar estatísticas:

```bash
curl http://localhost:8080/estatistica
```

Deletar uma transação por id (implementado atualmente):

```bash
curl -X DELETE http://localhost:8080/transacao/1
```

