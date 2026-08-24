# Spring AI - Gerenciador de Gastos Inteligente

Este projeto foi desenvolvido como parte do **Bootcamp Java AI da DIO** (Digital Innovation One). Trata-se de um sistema simples e didático (sem validações complexas, tratamentos de exceções avançados, etc.) focado na integração de serviços de Inteligência Artificial, transcrição de áudio e registro de gastos financeiros (transactions).

## 🚀 O que ele faz
A aplicação é uma API REST que permite o gerenciamento de despesas (Transações/Transactions) com recursos avançados de IA. Além do CRUD básico de gastos categorizados (ex: supermercado, farmácia, automóvel), a aplicação oferece um fluxo inteligente onde o usuário pode enviar um arquivo de áudio relatando um gasto. O sistema transcreve o áudio, processa a informação usando um modelo de linguagem natural (LLM) e devolve uma resposta em formato de áudio (Text-to-Speech).

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

### Inteligências Artificiais e Modelos
- **Gemini (Google GenAI) - `gemini-3.6-flash`**: Utilizado como o cérebro principal (Chat Model) para processar o texto transcrito, extrair intenções e gerar respostas inteligentes.
- **Groq AI (via compatibilidade OpenAI) - `whisper-large-v3-turbo`**: Modelo Whisper utilizado para transcrição de áudio (Speech-to-Text), convertendo a fala do usuário em texto com alta precisão e rapidez.
- **ElevenLabs - `elevenlabs`**: Serviço de Text-to-Speech de altíssima qualidade utilizado para converter a resposta gerada pelo Gemini em voz (formato MP3), retornando um áudio humanizado ao usuário.

## 🏗️ Arquitetura do Projeto
O projeto foi estruturado utilizando conceitos de **Clean Architecture / Arquitetura Hexagonal**, garantindo baixo acoplamento e separação de responsabilidades. O código está dividido em três camadas principais:
- **Domain**: Contém as regras de negócio puras (ex: `Transaction`, `Category`, `TransactionRepository`).
- **Application**: Contém os casos de uso (Use Cases) que orquestram a lógica da aplicação (ex: `PersistTransactionUseCase`, `DeleteTransactionUseCase`).
- **Infrastructure**: Contém os adaptadores externos, como Controladores HTTP (`TransactionController`), Entidades do banco (`TransactionEntity`), e Repositórios JPA.

## 💡 Implementação de Melhorias
Para expandir o projeto original, foram implementadas as seguintes melhorias:
1. **Atributo de Data de Criação (`createdAt`)**: Adicionado à entidade para registrar automaticamente o momento exato em que o gasto foi criado.
2. **Ação de Find (Busca)**: Implementados endpoints para listar todas as transações cadastradas e também filtrar as transações por categoria específica.
3. **Ação de Delete (Exclusão)**: Adicionado o endpoint e caso de uso correspondente para permitir a exclusão de uma transação específica via UUID.

## 💾 Estrutura da Entidade
*Nota: Embora muitas vezes referida no contexto de IA como "Transcription" devido ao processamento de áudio, a entidade de negócio principal deste sistema é a **Transaction** (Transação de Gasto).*

A entidade `TransactionEntity` possui a seguinte estrutura no banco de dados:
```java
UUID id;                 // Identificador único
String description;      // Descrição do gasto
long amount;             // Valor da transação
Category category;       // Categoria (GROCERIES, PHARMA, AUTO)
LocalDateTime createdAt; // Data e hora da criação (gerado automaticamente)
```

## 🔌 Funcionalidades Oferecidas: Endpoints

- `POST /transactions`: Cria uma nova transação.
- `GET /transactions`: Lista todas as transações.
- `GET /transactions/category?category=...`: Lista transações filtradas por categoria.
- `PUT /transactions?id=...`: Atualiza uma transação existente.
- `DELETE /transactions?id=...`: Deleta uma transação pelo seu ID.
- `POST /transactions/ai`: Recebe um arquivo de áudio, transcreve, processa com Gemini e retorna uma resposta em áudio (MP3).

### JSON dos Endpoints (Request / Response)

**Criar Transação (POST `/transactions`) / Atualizar (PUT `/transactions`)**
*Request:*
```json
{
  "description": "Compra no supermercado",
  "amount": 15000,
  "category": "GROCERIES"
}
```

*Response (também retornado nos GETs):*
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "description": "Compra no supermercado",
  "amount": 15000.0,
  "category": "GROCERIES",
  "createdAt": "2024-10-23T15:30:00"
}
```

## ⚙️ Passo a Passo para Clonar e Utilizar

1. **Clone o repositório:**
   ```bash
   git clone <URL_DO_SEU_REPOSITORIO>
   cd Spring_AI
   ```
2. **Configure as Variáveis de Ambiente (`.env`):**
   Crie ou edite o arquivo `.env` na raiz do projeto e insira suas chaves de API:
   ```env
   GENAI_API_KEY=sua_chave_do_google_gemini
   GROQ_API_KEY=sua_chave_do_groq
   ELEVENLABS_API_KEY=sua_chave_do_elevenlabs
   MYSQL_ROOT_PASSWORD=senha_root_desejada
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

## 🧪 Como Testar o Fluxo Principal

1. Com a aplicação rodando, você pode usar uma ferramenta como **Postman**, **Insomnia**, ou o próprio **Swagger** (geralmente em `http://localhost:8080/swagger-ui.html`).
2. **Para testar o CRUD:** Utilize os endpoints `POST /transactions`, `GET /transactions`, e `DELETE /transactions` enviando os JSONs de exemplo mostrados acima.
3. **Para testar o fluxo de IA (`/transactions/ai`):**
   - No Postman, crie uma requisição `POST` para `http://localhost:8080/transactions/ai`.
   - Vá na aba **Body**, selecione `form-data`.
   - Adicione uma chave chamada `file` do tipo **File** e selecione um arquivo de áudio (ex: `audio.ogg` ou `audio.mp3`) com você falando um gasto.
   - Envie a requisição. A resposta será um arquivo `.mp3` que você pode ouvir (gerado pelo ElevenLabs).

---

## 📚 O que foi aprendido

> *[Insira aqui os seus aprendizados e reflexões sobre a integração das IAs, o uso do Spring AI, arquitetura limpa, e os desafios encontrados durante o bootcamp.]*
