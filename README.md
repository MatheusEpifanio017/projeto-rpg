# Mythbound

**Construa seu personagem. Enfrente desafios. Escreva sua lenda.**

Mythbound é um projeto de RPG desenvolvido em Java, com foco na criação de personagens, combates, exploração e progressão de aventuras em um mundo de fantasia.

O projeto utiliza Spring Boot para estruturar o backend e PostgreSQL, hospedado no Supabase, para armazenar os dados. O objetivo é desenvolver uma experiência de RPG na qual os jogadores possam criar personagens, participar de combates, avançar pela história e salvar seu progresso.

## Status do projeto

**Em desenvolvimento.**

O projeto possui estruturas para criação de personagens, classes, raças, atributos, inventário, combates, história e salvamentos. A aplicação Spring Boot está configurada para conectar-se ao banco PostgreSQL do Supabase.

A implementação completa do salvamento e carregamento de aventuras, da autenticação e da integração com uma interface web ainda está em desenvolvimento.

## Tecnologias utilizadas

- **Java 21** — linguagem principal.
- **Spring Boot** — estrutura e inicialização do backend.
- **Maven** — gerenciamento de dependências e execução do projeto.
- **PostgreSQL** — banco de dados relacional.
- **Supabase** — hospedagem do banco de dados na nuvem.
- **Hibernate / JPA** — integração entre Java e PostgreSQL.
- **Jackson** — manipulação de dados JSON.

## Funcionalidades e módulos

### Personagens

- Estrutura de atributos.
- Catálogo de classes e raças.
- Criação de personagens.
- Passivas raciais.

### Combate

- Estrutura para gerenciamento de combates.
- Organização das regras de interação entre personagens e inimigos.

### Inventário

- Representação de itens.
- Organização do inventário dos personagens.

### História

- Controle do estado e da progressão da aventura.
- Estrutura para acompanhar o desenvolvimento da narrativa.

### Salvamentos

- Estrutura inicial para gerenciamento de salvamentos.
- Banco de dados preparado para armazenar os dados das aventuras.
- Próxima etapa: implementar e testar o salvamento e o carregamento do progresso.

## Estrutura do projeto

```text
projeto-rpg/
└── demo/
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            │   └── com/
            │       └── rpg/
            │           ├── ErosGameApplication.java
            │           ├── model/
            │           ├── catalogo/
            │           ├── service/
            │           ├── inventario/
            │           ├── historia/
            │           └── main/
            └── resources/
                └── application.properties
```

*Observação: a estrutura representa a organização principal conhecida do projeto. Os nomes dos pacotes e arquivos podem mudar durante a refatoração.*

## Pré-requisitos

Antes de executar o projeto, instale:

- [Java JDK 21](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/download.cgi)
- Uma conta no [Supabase](https://supabase.com/)

Também é necessário ter acesso ao banco PostgreSQL configurado.

## Configuração do ambiente

### 1. Clonar o repositório

```bash
git clone URL_DO_REPOSITORIO
cd projeto-rpg/demo
```

Substitua `URL_DO_REPOSITORIO` pelo endereço real do repositório.

### 2. Configurar as variáveis de ambiente

No Git Bash, configure as credenciais do banco:

```bash
export SUPABASE_DB_URL="jdbc:postgresql://HOST_DO_BANCO:PORTA/postgres?sslmode=require"
export SUPABASE_DB_USERNAME="USUARIO_DO_BANCO"
export SUPABASE_DB_PASSWORD="SENHA_DO_BANCO"
```

Substitua os valores de exemplo pelos dados de conexão fornecidos pelo Supabase. Utilize o endereço e a porta correspondentes ao método de conexão escolhido.

Não publique as credenciais no GitHub.

**Importante:** essas variáveis permanecem disponíveis apenas na sessão atual do Git Bash. Se fechar o terminal, será necessário configurá-las novamente.

### 3. Configurar o `application.properties`

No arquivo `src/main/resources/application.properties`, utilize:

```properties
spring.datasource.url=${SUPABASE_DB_URL}
spring.datasource.username=${SUPABASE_DB_USERNAME}
spring.datasource.password=${SUPABASE_DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.open-in-view=false
```

As credenciais são carregadas pelas variáveis de ambiente, sem precisar ficar escritas no código.

## Como executar

Na pasta `demo`, execute:

```bash
mvn spring-boot:run
```

Se a configuração estiver correta e o banco estiver acessível, o Spring Boot deverá iniciar.

Para executar os testes automatizados:

```bash
mvn test
```

## Banco de dados

O Mythbound utiliza PostgreSQL hospedado no Supabase.

A tabela inicial `public.salvamentos` foi criada para armazenar informações como:

| Campo | Descrição |
|---|---|
| `id` | Identificador do salvamento. |
| `jogador_id` | Identificador do jogador associado à conta. |
| `nome_salvamento` | Nome da aventura salva. |
| `dados` | Dados da aventura em formato JSONB. |
| `criado_em` | Data de criação do salvamento. |
| `atualizado_em` | Data da última atualização. |

A tabela possui políticas de Row Level Security (RLS) destinadas a restringir o acesso aos registros de acordo com o jogador autenticado.

A integração completa com autenticação e os testes de isolamento dos dados ainda precisam ser concluídos.

## Segurança

- Não publique senhas ou credenciais do Supabase.
- Utilize variáveis de ambiente para configurar a conexão.
- Mantenha arquivos locais com segredos no `.gitignore`.
- Não exponha credenciais administrativas no frontend.
- Valide a identidade e as permissões dos jogadores antes de permitir operações de salvamento.
- Mantenha as políticas RLS habilitadas e teste o isolamento dos dados.

## Próximas etapas

- [ ] Implementar e testar o salvamento de personagens e aventuras.
- [ ] Implementar o carregamento de salvamentos existentes.
- [ ] Integrar a autenticação dos jogadores.
- [ ] Criar endpoints REST para comunicação com o frontend.
- [ ] Desenvolver a interface web do RPG.
- [ ] Integrar a interface aos serviços Java.
- [ ] Testar a segurança e a persistência dos dados.
- [ ] Documentar as regras de combate, progressão e exploração.

## Desenvolvimento

Mythbound é um projeto em desenvolvimento, voltado à aplicação prática de programação Java, orientação a objetos, persistência de dados e desenvolvimento de aplicações.

---

**Mythbound — Construa sua lenda.**


# Mythbound

**Create your character. Face challenges. Write your legend.**

Mythbound is a role-playing game project developed in Java, focused on character creation, combat, exploration, and adventure progression in a fantasy world.

The project uses Spring Boot to structure the backend and PostgreSQL, hosted on Supabase, to store data. The goal is to develop an RPG experience where players can create characters, engage in combat, progress through the story, and save their adventure progress.

## Project Status

**In development.**

The project includes structures for character creation, classes, races, attributes, inventory, combat, story progression, and save management. The Spring Boot application is configured to connect to the PostgreSQL database hosted on Supabase.

Full adventure saving and loading, player authentication, and integration with a web interface are still under development.

## Technologies Used

- **Java 21** — primary programming language.
- **Spring Boot** — backend framework and application startup.
- **Maven** — dependency management and project execution.
- **PostgreSQL** — relational database.
- **Supabase** — cloud-hosted database platform.
- **Hibernate / JPA** — integration between Java and PostgreSQL.
- **Jackson** — JSON data processing.

## Features and Modules

### Characters

- Character attribute structure.
- Class and race catalogs.
- Character creation.
- Racial passives.

### Combat

- Structure for combat management.
- Organization of interaction rules between characters and enemies.

### Inventory

- Item representation.
- Character inventory management.

### Story

- Adventure state and progression tracking.
- Structure for managing narrative development.

### Save System

- Initial structure for save management.
- Database prepared to store adventure data.
- Next step: implement and test adventure saving and loading.

## Project Structure

```text
projeto-rpg/
└── demo/
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            │   └── com/
            │       └── rpg/
            │           ├── ErosGameApplication.java
            │           ├── model/
            │           ├── catalogo/
            │           ├── service/
            │           ├── inventario/
            │           ├── historia/
            │           └── main/
            └── resources/
                └── application.properties
```

*Note: This structure represents the main organization currently known. Package and file names may change during future refactoring.*

## Prerequisites

Before running the project, install:

- [Java JDK 21](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/download.cgi)
- A [Supabase](https://supabase.com/) account

Access to the configured PostgreSQL database is also required.

## Environment Configuration

### 1. Clone the Repository

```bash
git clone REPOSITORY_URL
cd projeto-rpg/demo
```

Replace `REPOSITORY_URL` with the actual repository address.

### 2. Configure Environment Variables

In Git Bash, configure the database credentials:

```bash
export SUPABASE_DB_URL="jdbc:postgresql://DATABASE_HOST:PORT/postgres?sslmode=require"
export SUPABASE_DB_USERNAME="DATABASE_USERNAME"
export SUPABASE_DB_PASSWORD="DATABASE_PASSWORD"
```

Replace the example values with the connection details provided by Supabase. Use the host and port corresponding to your chosen connection method.

Do not publish database credentials on GitHub.

**Important:** These variables remain available only in the current Git Bash session. If you close the terminal, you will need to configure them again.

### 3. Configure `application.properties`

In `src/main/resources/application.properties`, use the following configuration:

```properties
spring.datasource.url=${SUPABASE_DB_URL}
spring.datasource.username=${SUPABASE_DB_USERNAME}
spring.datasource.password=${SUPABASE_DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.open-in-view=false
```

Credentials are loaded from environment variables instead of being stored directly in the source code.

## How to Run

From the `demo` directory, execute:

```bash
mvn spring-boot:run
```

If the configuration is correct and the database is accessible, Spring Boot should start successfully.

To run the automated tests:

```bash
mvn test
```

## Database

Mythbound uses PostgreSQL hosted on Supabase.

The initial `public.salvamentos` table was created to store information such as:

| Field | Description |
|---|---|
| `id` | Save identifier. |
| `jogador_id` | Identifier of the player associated with the account. |
| `nome_salvamento` | Name of the saved adventure. |
| `dados` | Adventure data stored in JSONB format. |
| `criado_em` | Save creation timestamp. |
| `atualizado_em` | Last update timestamp. |

The table has Row Level Security (RLS) policies intended to restrict access to records according to the authenticated player.

Full authentication integration and data-isolation testing still need to be completed.

## Security

- Do not publish Supabase passwords or credentials.
- Use environment variables to configure the database connection.
- Keep local files containing secrets in `.gitignore`.
- Do not expose administrative credentials in the frontend.
- Validate player identity and permissions before allowing save operations.
- Keep RLS policies enabled and test data isolation between players.

## Next Steps

- [ ] Implement and test character and adventure saving.
- [ ] Implement loading existing saves.
- [ ] Integrate player authentication.
- [ ] Create REST endpoints for frontend communication.
- [ ] Develop the RPG web interface.
- [ ] Connect the interface to the Java services.
- [ ] Test data security and persistence.
- [ ] Document combat, progression, and exploration rules.

## Development

Mythbound is a project under development, focused on the practical application of Java programming, object-oriented programming, data persistence, and application development.

---

**Mythbound — Write Your Legend.**