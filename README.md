# EducaMais API

API REST do **EducaMais**, plataforma de educação corporativa do Instituto Eurofarma — Challenge Eurofarma 2026.

O EducaMais não é um LMS. É uma camada de inteligência sobre o Moodle e o sistema legado de treinamentos, construída sobre quatro pilares: predição de evasão, tutoria por IA com fonte no material, salas de estudo síncronas e aprendizagem multicanal.

Esta API entrega o backend dessa solução: catálogo de trilhas, matrículas com progresso individual, salas de estudo com controle de lotação, alertas preditivos e a tutora EdIA com regra de zero alucinação.

---

## Equipe

| Integrante | RM |
|---|---|
| Samuel Okuma | 555370 |
| Juan Marini | 556678 |
| Eduardo Antunes | 555534 |
| Romeo Miranda | 557025 |
| Arthur Menon | 555918 |

Curso: Sistemas de Informação — 3º ano
Disciplina: Microservice and Web Engineering & IT Services

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 (LTS) |
| Spring Boot | 3.5.15 |
| Spring Web / Spring Data JPA / Bean Validation | — |
| H2 Database | em memória |
| Lombok | — |
| springdoc-openapi (Swagger UI) | 2.8.17 |
| Maven | via wrapper (`mvnw`) |

---

## Pré-requisitos

Apenas o **JDK 17 ou superior** instalado. O Maven não precisa ser instalado — o projeto usa o wrapper (`mvnw` / `mvnw.cmd`).

Para conferir a versão do Java:

```bash
java -version
```

---

## Como executar

### 1. Clonar o repositório

```bash
git clone https://github.com/juan-marini/microservico_2sem_sprint.git
cd microservico_2sem_sprint
```

### 2. Subir a aplicação

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

**Windows (PowerShell ou CMD):**

```bash
mvnw.cmd spring-boot:run
```

A aplicação sobe em **http://localhost:8080**. O console mostra `Started MsEducamaisApplication` quando estiver pronta.

### 3. Parar a aplicação

`Ctrl + C` no terminal.

### Alternativa: gerar o JAR

```bash
./mvnw clean package
java -jar target/ms-educamais-0.0.1-SNAPSHOT.jar
```

---

## Banco de dados

O projeto usa **H2 em memória** — não é preciso instalar nem configurar nada. O schema é criado automaticamente na inicialização e a carga inicial é feita pelo `src/main/resources/import.sql`.

Os dados são recriados a cada inicialização, então o ambiente está sempre limpo e previsível para teste.

### Console do H2

Disponível em **http://localhost:8080/h2-console**

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:educamais` |
| User Name | `sa` |
| Password | *(deixar em branco)* |

### Dados carregados

| Entidade | Quantidade |
|---|---|
| Usuário | 1 (Marina Ribeiro Costa) |
| Cursos | 8 |
| Módulos | 20 |
| Aulas | 55 |
| Matrículas | 8 |
| Aulas concluídas | 25 |
| Salas de estudo | 5 |
| Alertas de evasão | 2 |
| Conquistas | 4 |

Os cursos cobrem o contexto farmacêutico: BPF, Farmacovigilância, LGPD, Segurança do Trabalho, Validação de Processos, Comunicação Assertiva, Compliance e Excel Avançado.

As matrículas têm progresso variado — dois cursos concluídos, dois não iniciados e dois em risco alto de evasão. Entre as salas, uma está ao vivo e uma está lotada. Uma das quatro conquistas está bloqueada.

---

## Documentação da API

Com a aplicação rodando:

| Recurso | URL |
|---|---|
| **Swagger UI** | http://localhost:8080/swagger-ui.html |
| OpenAPI (JSON) | http://localhost:8080/v3/api-docs |

Todos os endpoints estão documentados com descrição e exemplos de payload. O Swagger permite executar as requisições direto do navegador, sem precisar de Postman.

---

## Endpoints

Todas as rotas são versionadas sob o prefixo **`/api/v1`**.

### Usuários — `/api/v1/usuarios`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/v1/usuarios` | Lista todos os usuários |
| GET | `/api/v1/usuarios/{id}` | Busca por id |
| GET | `/api/v1/usuarios/matricula/{matricula}` | Busca pela matrícula corporativa |
| POST | `/api/v1/usuarios` | Cadastra um usuário |
| PUT | `/api/v1/usuarios/{id}` | Atualiza os dados cadastrais |
| DELETE | `/api/v1/usuarios/{id}` | Exclui um usuário |

### Cursos — `/api/v1/cursos`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/v1/cursos?busca=&categoria=` | Lista com filtro opcional por texto e categoria |
| GET | `/api/v1/cursos/categorias` | Lista as categorias distintas do catálogo |
| GET | `/api/v1/cursos/obrigatorios` | Lista apenas os treinamentos obrigatórios |
| GET | `/api/v1/cursos/{id}` | Busca um curso com módulos e aulas |
| POST | `/api/v1/cursos` | Cadastra um curso com módulos e aulas |
| PUT | `/api/v1/cursos/{id}` | Atualiza o curso e substitui módulos e aulas |
| DELETE | `/api/v1/cursos/{id}` | Exclui um curso |

### Matrículas — `/api/v1/matriculas`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/v1/matriculas` | Lista todas as matrículas |
| GET | `/api/v1/matriculas/{id}` | Busca por id |
| GET | `/api/v1/matriculas/{id}/curso` | Detalha o curso marcando as aulas concluídas por aquele usuário |
| GET | `/api/v1/matriculas/usuario/{usuarioId}` | Lista as matrículas de um usuário |
| GET | `/api/v1/matriculas/usuario/{usuarioId}/risco/{risco}` | Filtra por nível de risco (`BAIXO`, `MEDIO`, `ALTO`) |
| POST | `/api/v1/matriculas` | Matricula um usuário em um curso |
| PATCH | `/api/v1/matriculas/{id}/aulas/{aulaId}/concluir` | Conclui uma aula e recalcula o progresso |
| PATCH | `/api/v1/matriculas/{id}/aulas/{aulaId}/reabrir` | Desfaz a conclusão e recalcula |
| PATCH | `/api/v1/matriculas/{id}/risco/{risco}` | Atualiza o nível de risco |
| DELETE | `/api/v1/matriculas/{id}` | Exclui a matrícula |

### Salas de estudo — `/api/v1/salas-estudo`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/v1/salas-estudo` | Lista todas as salas |
| GET | `/api/v1/salas-estudo/ao-vivo` | Lista apenas as salas ao vivo |
| GET | `/api/v1/salas-estudo/{id}` | Busca por id |
| POST | `/api/v1/salas-estudo` | Cria uma sala |
| PUT | `/api/v1/salas-estudo/{id}` | Atualiza uma sala |
| PATCH | `/api/v1/salas-estudo/{id}/confirmar-participacao` | Confirma participação, recusa se lotada |
| PATCH | `/api/v1/salas-estudo/{id}/cancelar-participacao` | Cancela e libera a vaga |
| DELETE | `/api/v1/salas-estudo/{id}` | Exclui a sala |

### Alertas de evasão — `/api/v1/alertas`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/v1/alertas` | Lista todos os alertas |
| GET | `/api/v1/alertas/{id}` | Busca por id |
| GET | `/api/v1/alertas/usuario/{usuarioId}` | Lista os alertas de um usuário |
| POST | `/api/v1/alertas` | Registra um alerta |
| PUT | `/api/v1/alertas/{id}` | Atualiza um alerta |
| DELETE | `/api/v1/alertas/{id}` | Exclui um alerta |

### Conquistas — `/api/v1/conquistas`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/v1/conquistas` | Lista todas as conquistas |
| GET | `/api/v1/conquistas/{id}` | Busca por id |
| GET | `/api/v1/conquistas/usuario/{usuarioId}` | Lista as conquistas de um usuário |
| POST | `/api/v1/conquistas` | Cria uma conquista |
| PUT | `/api/v1/conquistas/{id}` | Atualiza uma conquista |
| PATCH | `/api/v1/conquistas/{id}/desbloquear` | Desbloqueia uma conquista |
| DELETE | `/api/v1/conquistas/{id}` | Exclui uma conquista |

### EdIA — `/api/v1/edia`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/v1/edia/perguntas` | Envia uma pergunta e recebe a resposta com a fonte |
| GET | `/api/v1/edia/historico/usuario/{usuarioId}` | Histórico de conversa em ordem cronológica |
| DELETE | `/api/v1/edia/historico/usuario/{usuarioId}` | Apaga o histórico |

---

## Exemplos de requisição

Os exemplos abaixo usam os dados já carregados no banco. Podem ser colados no Postman, no Insomnia ou executados no Swagger UI.

### Listar os cursos filtrando por texto

```
GET http://localhost:8080/api/v1/cursos?busca=BPF
```

### Filtrar por categoria

```
GET http://localhost:8080/api/v1/cursos?categoria=Compliance
```

### Ver o progresso de um usuário em todas as trilhas

```
GET http://localhost:8080/api/v1/matriculas/usuario/1
```

Resposta (trecho):

```json
[
  {
    "id": 1,
    "usuario": { "id": 1, "nome": "Marina Ribeiro Costa", "iniciais": "MC" },
    "curso": { "id": 1, "titulo": "Boas Práticas de Fabricação (BPF)" },
    "progresso": 0.625,
    "percentual": 63,
    "concluida": false,
    "risco": "MEDIO",
    "rotuloRisco": "Atenção",
    "totalAulas": 8,
    "aulasConcluidas": 5,
    "proximaAula": {
      "titulo": "Avaliação do módulo de contaminação",
      "duracaoFormatada": "12 min",
      "rotuloTipo": "Quiz"
    }
  }
]
```

### Concluir uma aula e ver o progresso subir

```
PATCH http://localhost:8080/api/v1/matriculas/1/aulas/6/concluir
```

O `percentual` vai de 63 para 75 e a `proximaAula` avança para a aula seguinte.

### Perguntar à EdIA — resposta com fonte no material

```
POST http://localhost:8080/api/v1/edia/perguntas
Content-Type: application/json
```

```json
{
  "usuarioId": 1,
  "pergunta": "Como evitar contaminação cruzada na área de pesagem?"
}
```

Resposta (trecho):

```json
{
  "resposta": {
    "autor": "EDIA",
    "texto": "A prevenção de contaminação cruzada começa pela segregação física das áreas...",
    "fonte": "Boas Práticas de Fabricação (BPF) > Módulo 2 > Aula 1",
    "possuiFonte": true
  }
}
```

### Perguntar à EdIA — regra de zero alucinação

```json
{
  "usuarioId": 1,
  "pergunta": "Qual a capital da Mongólia?"
}
```

A EdIA responde que não encontrou a informação no material indexado e que por isso prefere não responder, com `possuiFonte: false`. É a demonstração da regra: sem fonte, sem resposta.

### Confirmar participação em uma sala lotada

A sala 2 já está com 8/8 participantes.

```
PATCH http://localhost:8080/api/v1/salas-estudo/2/confirmar-participacao
```

Retorna **409 Conflict**:

```json
{
  "status": 409,
  "error": "Sala lotada. Capacidade máxima de 8 participantes",
  "path": "/api/v1/salas-estudo/2/confirmar-participacao"
}
```

### Cadastrar um usuário com dados inválidos

```
POST http://localhost:8080/api/v1/usuarios
```

```json
{ "nome": "Ma", "cargo": "", "area": "Qualidade", "matricula": "X" }
```

Retorna **422 Unprocessable Entity** com a lista de campos:

```json
{
  "status": 422,
  "error": "Dados inválidos",
  "errors": [
    { "fieldName": "nome", "message": "Nome deve ter entre 3 e 100 caracteres" },
    { "fieldName": "cargo", "message": "Cargo deve ter entre 3 e 100 caracteres" },
    { "fieldName": "matricula", "message": "Matrícula deve ter entre 4 e 20 caracteres" }
  ]
}
```

### Cadastrar um curso completo

```
POST http://localhost:8080/api/v1/cursos
```

```json
{
  "titulo": "Gestão de Desvios e CAPA",
  "categoria": "Qualidade",
  "instrutor": "Dra. Beatriz Andrade",
  "descricao": "Investigação de desvios de qualidade e construção de planos de ação corretiva e preventiva.",
  "cargaHoraria": 9,
  "obrigatorio": true,
  "prazo": "2027-03-31",
  "modulos": [
    {
      "titulo": "Investigação de desvios",
      "ordem": 1,
      "aulas": [
        { "titulo": "Classificação de desvios por criticidade", "duracaoMinutos": 20, "tipo": "VIDEO", "ordem": 1 },
        { "titulo": "Análise de causa raiz na prática", "duracaoMinutos": 26, "tipo": "VIDEO", "ordem": 2 }
      ]
    }
  ]
}
```

Retorna **201 Created** com o header `Location` apontando para o recurso criado.

---

## Códigos de status

| Código | Quando ocorre |
|---|---|
| **200** OK | Consulta ou atualização bem-sucedida |
| **201** Created | Recurso criado, com header `Location` |
| **204** No Content | Exclusão bem-sucedida |
| **400** Bad Request | JSON mal formatado ou valor inválido em parâmetro de rota |
| **404** Not Found | Recurso inexistente |
| **409** Conflict | Conflito de regra de negócio — matrícula duplicada, sala lotada, exclusão com vínculos |
| **422** Unprocessable Entity | Falha de validação, com a lista de campos e mensagens |
| **500** Internal Server Error | Erro inesperado no servidor |

Todos os erros seguem o mesmo formato, com `timestamp`, `status`, `error` e `path`. O 422 acrescenta o array `errors`.

---

## Arquitetura

O projeto segue separação em camadas, sem acesso direto do controller à persistência:

```
Controller  ->  Service  ->  Repository  ->  Entity / H2
     |             |
   DTOs        regras de
 Request /      negócio
 Response
```

| Camada | Responsabilidade |
|---|---|
| `controller` | Expõe os endpoints REST, recebe e devolve DTOs. Não conhece o repository |
| `service` | Concentra as regras de negócio, o cálculo de progresso e a conversão entre DTO e entidade |
| `repository` | Interfaces `JpaRepository` com as consultas do domínio |
| `entities` | Entidades JPA e enums, com os campos derivados calculados no próprio modelo |
| `dto.request` | Estruturas de entrada com Bean Validation. Relacionamento entra como id |
| `dto.response` | Estruturas de saída. A entidade nunca é serializada diretamente |
| `exceptions` | Exceções de domínio, DTOs de erro e o `@RestControllerAdvice` |
| `config` | Configuração do OpenAPI |

### Estrutura de pastas

```
src/main/java/br/com/fiap/ms/educamais/
├── config/          OpenApiConfig
├── controller/      7 controllers REST
├── dto/
│   ├── request/     9 DTOs de entrada
│   └── response/    12 DTOs de saída
├── entities/        7 entidades + 3 enums
├── exceptions/
│   ├── dto/         CustomErrorDTO, FieldMessageDTO, ValidationErrorDTO
│   └── handler/     GlobalExceptionHandler
├── repository/      10 repositories
└── service/         7 services

src/main/resources/
├── application.properties
└── import.sql       carga inicial
```

---

## Modelo de domínio

### Entidades

| Entidade | Descrição |
|---|---|
| **Usuario** | Colaborador da Eurofarma, com matrícula, nível, XP e ofensiva de dias |
| **Curso** | Trilha de treinamento do catálogo, com categoria, instrutor, carga horária e prazo |
| **Modulo** | Agrupamento ordenado de aulas dentro de um curso |
| **Aula** | Conteúdo individual, com duração e tipo |
| **Matricula** | Vínculo entre usuário e curso, com progresso e risco de evasão |
| **AulaConcluida** | Registro de qual aula aquele usuário concluiu e quando |
| **SalaEstudo** | Encontro síncrono mediado, com participantes e capacidade |
| **AlertaEvasao** | Alerta preditivo com probabilidade de abandono e ação sugerida |
| **Conquista** | Item de gamificação, bloqueado ou desbloqueado |
| **MensagemEdia** | Mensagem do histórico de conversa com a EdIA |

### Enums

| Enum | Valores |
|---|---|
| **TipoAula** | `VIDEO` (Vídeo), `LEITURA` (Leitura), `QUIZ` (Quiz), `AO_VIVO` (Ao vivo) |
| **RiscoEvasao** | `BAIXO` (Em dia), `MEDIO` (Atenção), `ALTO` (Risco alto) |
| **AutorMensagem** | `ALUNO` (Aluno), `EDIA` (EdIA) |

### Relacionamentos

```
Usuario 1 ── N Matricula N ── 1 Curso 1 ── N Modulo 1 ── N Aula
                  |                                        |
                  1                                        1
                  |                                        |
                  N                                        N
             AulaConcluida ─────────────────────────────────

Usuario 1 ── N AlertaEvasao N ── 1 Curso
Usuario 1 ── N Conquista
Usuario 1 ── N MensagemEdia
Curso   1 ── N SalaEstudo
```

### Campos derivados

Os cálculos ficam no modelo, nunca espalhados pelos controllers:

| Entidade | Derivados |
|---|---|
| `Usuario` | `iniciais`, `primeiroNome`, `progressoNivel` |
| `Curso` | `totalAulas`, `totalModulos`, `aulasEmOrdem` |
| `Modulo` | `totalAulas`, `duracaoMinutos` |
| `Aula` | `duracaoFormatada` |
| `Matricula` | `percentual`, `concluida`, `naoIniciada`, `proximaAula`, `quantidadeAulasConcluidas` |
| `SalaEstudo` | `lotada`, `vagasRestantes`, `ocupacao` |
| `MensagemEdia` | `possuiFonte` |

O `progresso` da matrícula é recalculado pelo service a cada aula concluída ou reaberta, a partir das linhas de `AulaConcluida`.

---

## Sobre a EdIA

A tutora responde por correspondência de palavra-chave sobre o material indexado, cobrindo BPF, Farmacovigilância, LGPD e Validação de Processos.

A regra de zero alucinação é o comportamento central:

- Pergunta com conteúdo no material → resposta longa **com a fonte** no formato `Curso > Módulo > Aula`
- Pergunta sobre o próprio funcionamento da plataforma → resposta **sem fonte**, explicando que a informação não vem do material de treinamento
- Pergunta fora do material → a EdIA informa que **não encontrou** e prefere não responder

Cada troca grava duas mensagens no histórico: a pergunta com autor `ALUNO` e a resposta com autor `EDIA`, com a fonte quando houver.
