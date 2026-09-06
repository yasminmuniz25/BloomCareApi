# BloomCare API

API REST para gestão de doação de leite materno (nutrizes, pontos de coleta, triagens, agendamentos e doações).

## Pré-requisitos

- Java 21 instalado (`java -version`)

Não é necessário instalar Maven nem banco de dados — o projeto já inclui o Maven Wrapper e usa banco H2 em memória.

## Como executar

1. Clone o repositório e entre na pasta do projeto:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd BloomCareApi
   ```

2. Rode a aplicação:

   **Linux/macOS:**
   ```bash
   ./mvnw spring-boot:run
   ```

   **Windows:**
   ```cmd
   mvnw.cmd spring-boot:run
   ```

3. Aguarde o log mostrar:
   ```
   Tomcat started on port 8080
   ```

A aplicação já sobe com o banco criado e populado automaticamente (sem nenhum passo manual de configuração de banco).

## Testando a API

Com a aplicação rodando, acesse o Swagger UI para ver e testar todos os endpoints:

👉 **http://localhost:8080/swagger-ui.html**

Todas as rotas seguem o padrão `/api/v1/{recurso}`, por exemplo:
```
GET  http://localhost:8080/api/v1/nutrizes
POST http://localhost:8080/api/v1/agendamentos
```

O banco já sobe populado (via `import.sql`), então os `GET` abaixo funcionam direto, sem precisar cadastrar nada antes.

### Exemplos de requisições (cURL)

**Nutriz**
```bash
# Listar
curl http://localhost:8080/api/v1/nutrizes

# Criar
curl -X POST http://localhost:8080/api/v1/nutrizes \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "Fernanda Souza",
        "email": "fernanda.souza@email.com",
        "senha": "senha123",
        "telefone": "11991112222",
        "dtNascimento": "1995-03-10",
        "cpf": "12345678900"
      }'
```

**Ponto de Coleta**
```bash
# Listar
curl http://localhost:8080/api/v1/pontos-coleta

# Criar
curl -X POST http://localhost:8080/api/v1/pontos-coleta \
  -H "Content-Type: application/json" \
  -d '{
        "tipoColeta": "PONTO_COLETA",
        "endereco": "Av. Paulista, 1000",
        "cidade": "São Paulo",
        "estado": "SP",
        "telefone": "1130001000"
      }'
```

**Triagem**
```bash
# Listar
curl http://localhost:8080/api/v1/triagens

# Criar
curl -X POST http://localhost:8080/api/v1/triagens \
  -H "Content-Type: application/json" \
  -d '{
        "nutrizId": 3,
        "dataTriagem": "2026-12-15",
        "aptaDoacao": true
      }'
```

**Agendamento**
```bash
# Listar
curl http://localhost:8080/api/v1/agendamentos

# Criar
curl -X POST http://localhost:8080/api/v1/agendamentos \
  -H "Content-Type: application/json" \
  -d '{
        "nutrizId": 1,
        "pontoColetaId": 1,
        "dataHora": "2026-10-01T09:00:00",
        "tipoColeta": "PONTO_COLETA"
      }'

# Cancelar (troque {id} pelo id retornado acima)
curl -X PUT http://localhost:8080/api/v1/agendamentos/{id}/cancelar
```

**Doação**
```bash
# Listar
curl http://localhost:8080/api/v1/doacoes

# Criar
# Doação válida (agendamento 1, nutriz apta)
curl -X POST http://localhost:8080/api/v1/doacoes \
  -H "Content-Type: application/json" \
  -d '{
        "dataDoacao": "2026-09-04",
        "quantidadeMl": 450.0,
        "agendamentoId": 1
      }'

# Teste de regra de negócio: nutriz não apta -> deve retornar 409
curl -X POST http://localhost:8080/api/v1/doacoes \
  -H "Content-Type: application/json" \
  -d '{
        "dataDoacao": "2026-09-04",
        "quantidadeMl": 400.0,
        "agendamentoId": 2
      }'
```

### Testando os relacionamentos entre as entidades

O roteiro abaixo mostra, na prática, como as entidades se relacionam: `Nutriz` → `Triagem`, `Nutriz` + `PontoColeta` → `Agendamento`, e `Agendamento` → `Doacao` (essa última só é aceita se a nutriz estiver apta na triagem **e** o agendamento estiver ativo). Siga a ordem — cada passo usa o `id` retornado pelo passo anterior.

**1) Criar uma nutriz nova**
```bash
curl -X POST http://localhost:8080/api/v1/nutrizes \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "Fernanda Souza",
        "email": "fernanda.souza@email.com",
        "senha": "senha123",
        "telefone": "11991112222",
        "dtNascimento": "1995-03-10",
        "cpf": "12345678900"
      }'
```
→ Guarde o `id` retornado (ex.: `4`). Chamaremos de **NUTRIZ_ID**.

**2) Criar um ponto de coleta novo**
```bash
curl -X POST http://localhost:8080/api/v1/pontos-coleta \
  -H "Content-Type: application/json" \
  -d '{
        "tipoColeta": "PONTO_COLETA",
        "endereco": "Av. Paulista, 1000",
        "cidade": "São Paulo",
        "estado": "SP",
        "telefone": "1130001000"
      }'
```
→ Guarde o `id` retornado (ex.: `4`). Chamaremos de **PONTO_ID**.

**3) Criar a triagem dessa nutriz (relacionamento Nutriz → Triagem)**
```bash
curl -X POST http://localhost:8080/api/v1/triagens \
  -H "Content-Type: application/json" \
  -d '{
        "nutrizId": NUTRIZ_ID,
        "dataTriagem": "2026-12-15",
        "aptaDoacao": true
      }'
```
→ A resposta traz `"nutrizId": NUTRIZ_ID`, confirmando o vínculo com a nutriz criada no passo 1.

**4) Criar o agendamento (relacionamento Nutriz + PontoColeta → Agendamento)**
```bash
curl -X POST http://localhost:8080/api/v1/agendamentos \
  -H "Content-Type: application/json" \
  -d '{
        "nutrizId": NUTRIZ_ID,
        "pontoColetaId": PONTO_ID,
        "dataHora": "2026-10-01T09:00:00",
        "tipoColeta": "PONTO_COLETA"
      }'
```
→ A resposta traz `"nutrizId"` e `"pontoColetaId"` iguais aos dos passos 1 e 2, e `"status": "AGENDADO"`. Guarde o `id` do agendamento como **AGENDAMENTO_ID**.

**5) Criar a doação vinculada ao agendamento (relacionamento Agendamento → Doacao)**
```bash
curl -X POST http://localhost:8080/api/v1/doacoes \
  -H "Content-Type: application/json" \
  -d '{
        "dataDoacao": "2026-09-06",
        "quantidadeMl": 450.0,
        "agendamentoId": AGENDAMENTO_ID
      }'
```
→ Funciona porque, por trás do vínculo `Doacao → Agendamento → Nutriz`, o service encontrou a triagem da nutriz e ela está apta. A resposta traz `"agendamentoId": AGENDAMENTO_ID`.

**6) Cancelar o agendamento e tentar doar de novo (mostra que Agendamento cancelado bloqueia Doacao)**
```bash
curl -X PUT http://localhost:8080/api/v1/agendamentos/AGENDAMENTO_ID/cancelar

curl -X POST http://localhost:8080/api/v1/doacoes \
  -H "Content-Type: application/json" \
  -d '{
        "dataDoacao": "2026-09-06",
        "quantidadeMl": 400.0,
        "agendamentoId": AGENDAMENTO_ID
      }'
```
→ A segunda chamada retorna **409 Conflict**: não é possível registrar doação para um agendamento cancelado.

**7) Repetir o fluxo com uma nutriz não apta (mostra que Triagem reprovada bloqueia Doacao)**
```bash
# nova nutriz + novo agendamento (repita os passos 1 e 4 com um novo PONTO_ID/NUTRIZ_ID2)
# depois, uma triagem com aptaDoacao = false:
curl -X POST http://localhost:8080/api/v1/triagens \
  -H "Content-Type: application/json" \
  -d '{
        "nutrizId": NUTRIZ_ID2,
        "dataTriagem": "2026-12-15",
        "aptaDoacao": false
      }'

# tentando registrar a doação para o agendamento dessa nutriz:
curl -X POST http://localhost:8080/api/v1/doacoes \
  -H "Content-Type: application/json" \
  -d '{
        "dataDoacao": "2026-09-06",
        "quantidadeMl": 400.0,
        "agendamentoId": AGENDAMENTO_ID2
      }'
```
→ Retorna **409 Conflict**: a nutriz não está apta para doação, segundo a triagem vinculada a ela.

> Esse roteiro completo mostra as 4 relações do domínio funcionando de ponta a ponta: `Nutriz–Triagem`, `Nutriz–Agendamento`, `PontoColeta–Agendamento` e `Agendamento–Doacao`, incluindo as regras de negócio que dependem desses relacionamentos (triagem apta e agendamento ativo).

### Tratamento de erros

| Situação | Status |
|---|---|
| Recurso não encontrado (ex.: `GET /api/v1/nutrizes/999`) | `404` |
| Regra de negócio violada (ex.: doação de nutriz não apta) | `409` |
| Campo obrigatório ausente/inválido no corpo | `422` |
| Tipo de parâmetro inválido (ex.: `GET /api/v1/nutrizes/abc`) | `400` |

## Banco de dados (opcional, para inspecionar os dados)

Console H2 disponível em `http://localhost:8080/h2-console` com:
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`


## Integrantes

- Daniela Grego Pantaleão — 555808
- Ana Carolina Calheiros de Lima — 555316
- Isabella Constance Silva Gonçalves — 559219
- Stefany Miyahara Omori — 558792
- Yasmin Sayuri Iechika Muniz — 559011
