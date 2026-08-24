<div align="center">

# <u> Spring AI - Gerenciador de Gastos Inteligente </u>

</div>

Este projeto foi desenvolvido como parte do **Bootcamp Java AI da DIO** (Digital Innovation One). Trata-se de um sistema simples e didático (sem validações complexas, tratamentos de exceções avançados, etc.) focado na integração de serviços de Inteligência Artificial, transcrição de áudio e registro de gastos financeiros (transactions).

<br>

## 🚀 O que ele faz
A aplicação é uma API REST que permite o gerenciamento de despesas (Transações/Transactions) com recursos avançados de IA. Além do CRUD básico de gastos categorizados (ex: supermercado, farmácia, cosméticos...), a aplicação oferece um fluxo inteligente onde o usuário pode enviar um arquivo de áudio relatando um gasto. O sistema transcreve o áudio, processa a informação usando um modelo de linguagem natural (LLM) e devolve uma resposta em formato de áudio (Text-to-Speech para download.

<br>

## 🛠️ Tecnologias Utilizadas
O projeto foi construído utilizando as seguintes tecnologias, frameworks e APIs:
- **Java 21**
- **Spring Boot 3.x / 4.x**
- **Spring AI**: Integração facilitada com múltiplos provedores de IA.
- **Spring Data JPA & Hibernate**: Persistência de dados.
- **MySQL**: Banco de dados relacional rodando via Docker Compose.
- **Docker Compose**: Orquestração do banco de dados local.
- **Lombok**: Redução de boilerplate code.
- **SpringDoc OpenAPI (Swagger)**: Documentação interativa dos endpoints.

<br>

### Inteligências Artificiais e Modelos
- **Gemini (Google GenAI) - `gemini-3.6-flash`**: Utilizado como o cérebro principal (Chat Model) para processar o texto transcrito, extrair informações e gerar respostas inteligentes.
- **Groq AI (via compatibilidade OpenAI) - `whisper-large-v3-turbo`**: Modelo Whisper utilizado para transcrição de áudio (Speech-to-Text), convertendo a fala do usuário em texto.
- **ElevenLabs - `elevenlabs`**: Serviço de Text-to-Speech utilizado para converter a resposta gerada pelo Gemini em voz (audio/MP3), retornando um áudio ao usuário.

<br>

## 🏗️ Arquitetura do Projeto
O projeto foi estruturado utilizando conceitos de **Clean Architecture / Arquitetura Hexagonal**, garantindo baixo acoplamento e separação de responsabilidades. O código está dividido em três camadas principais:
- **Domain**: Contém as regras de negócio principais (ex: `Transaction`, `Category`, `TransactionRepository`).
- **Application**: Contém os casos de uso (Use Cases) que orquestram a lógica aplicada (ex: `PersistTransactionUseCase`, `DeleteTransactionUseCase`).
- **Infrastructure**: Contém as conexões externas, como Controllers HTTP (`TransactionController`) e banco de dados - Entidades (`TransactionEntity`) e Repositórios JPA (`TransactionEntityRepository`).

<br>

## 💡 Implementação de Melhorias
Para expandir o projeto original, foram implementadas as seguintes melhorias:
1. **Atributo de Data de Criação (`createdAt`)**: Adicionado à entidade para registrar automaticamente o momento exato em que o gasto foi criado no banco.
2. **Ação de Find (Busca)**: Implementado o endpoint para listar todas as transações cadastradas.
3. **Ação de Delete (Exclusão)**: Adicionado o endpoint e caso de uso correspondentes à exclusão de uma transação específica a partir do id (UUID).

<br>

## 💾 Estrutura da Entidade
A entidade `TransactionEntity` (referente às transações de gastos) possui a seguinte estrutura no banco de dados:
```java
UUID id;                 // Identificador único (gerado automaticamente)
String description;      // Descrição do gasto
long amount;             // Valor da transação
Category category;       // Categoria (GROCERIES, PHARMA, COSMETICS, FOOD, ENTERTAINMENT, HOME_APPLIANCES, OTHERS)
LocalDateTime createdAt; // Data e hora da criação (gerado automaticamente)
```

<br>

## 🔌 Funcionalidades Oferecidas: Endpoints

- `POST /transactions`: Cria uma nova transação.
- `GET /transactions`: Lista todas as transações.
- `GET /transactions/category?category=...`: Lista transações filtradas por categoria.
- `PUT /transactions?id=...`: Atualiza uma transação existente.
- `DELETE /transactions?id=...`: Deleta uma transação a partir do seu ID.
- `POST /transactions/ai`: Recebe um arquivo de áudio, transcreve, processa e retorna uma resposta em áudio (MP3).

<br>

### Endpoints de Testes Individuais de IA (`/api/*`)
Além do fluxo principal, a aplicação conta com rotas específicas para testar cada serviço de IA individualmente:

- `GET /api/chat-client?prompt=...`: Envia um texto (prompt) para o Gemini e retorna a resposta textual (teste do Chat Client).
- `POST /api/transcribe`: Recebe um arquivo de áudio via `multipart/form-data` (chave `file`), envia para o modelo Whisper (Groq) e retorna o texto transcrito (Speech-to-Text).
- `POST /api/synthesize?text=...`: Recebe um texto, envia para ElevenLabs e retorna um arquivo de áudio `audio/mp3` contendo a fala gerada (Text-to-Speech).

<br>

### Exemplo de JSON dos Endpoints (Request / Response) 

**Criar Transação (POST `/transactions`) / Atualizar (PUT `/transactions`)**
*Request:*
```json
{
  "description": "Compra no supermercado",
  "amount": 800,
  "category": "GROCERIES"
}
```

*Response (também retornado nos GETs):*
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "description": "Compra no supermercado",
  "amount": 800.0,
  "category": "GROCERIES",
  "createdAt": "2024-10-23T15:30:00"
}
```

<br>

## ⚙️ Passo a Passo para Clonar e Utilizar

1. **Clone o repositório:**
   ```bash
   git clone <URL_DO_SEU_REPOSITORIO>
   cd Spring_AI
   ```
2. **Configure as Variáveis de Ambiente (`.env`):**
   Crie ou edite o arquivo `.env` na raiz do projeto e insira suas chaves de API e senha do banco de dados:
   ```env
   GENAI_API_KEY=sua_chave_do_google_gemini
   GROQ_API_KEY=sua_chave_do_groq
   ELEVENLABS_API_KEY=sua_chave_do_elevenlabs
   MYSQL_ROOT_PASSWORD=senha_root
   ```
3. **Suba o Banco de Dados:**
   A aplicação utiliza o Spring Boot Docker Compose Support, que subirá o banco automaticamente ao rodar a aplicação, mas se preferir usar manualmente:
   ```bash
   docker-compose up -d
   ```
4. **Execute a Aplicação:**
   ```bash
   ./gradlew bootRun
   ```

<br>

## 🧪 Como Testar o Fluxo Principal

1. Com a aplicação rodando, recomenda-se o uso do **Swagger** (URL: `http://localhost:8080/swagger-ui.html`).
2. **Para testar o CRUD:** Utilize os endpoints `POST /transactions`, `GET /transactions`, e `DELETE /transactions` enviando os JSONs de exemplo mostrados acima.
3. **Para testar o fluxo de IA (`/transactions/ai`):**
   - Com Swagger, selecione `POST /transactions/ai`.
   - Clique em `Try it out` e selecione um arquivo de áudio.
   - Envie a requisição `Execute`. A resposta será um arquivo `.mp3` que você pode ouvir (gerado pelo ElevenLabs).
