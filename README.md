# Documentação do Sistema Financeiro (Apoo Financeiro)

Esta documentação fornece uma visão detalhada da estrutura do projeto `apoo-financeiro`, descrevendo a arquitetura, serviços, infraestrutura e entidades.

## 1. Visão Geral da Arquitetura

O sistema é construído sobre uma arquitetura de **microserviços** baseada em **Spring Boot** e **Spring Cloud**. A comunicação entre os serviços é gerenciada através de um **API Gateway** e **Service Discovery** (Eureka). A autenticação e autorização são centralizadas no **Keycloak** (Identity Provider), utilizando **OAuth2/OIDC**. A persistência de dados é feita no **PostgreSQL**.

### Tecnologias Principais
-   **Linguagem**: Java 21
-   **Framework**: Spring Boot 3.4.12
-   **Service Discovery**: Netflix Eureka
-   **API Gateway**: Spring Cloud Gateway
-   **Segurança**: Keycloak, Spring Security (OAuth2 Resource Server)
-   **Banco de Dados**: PostgreSQL
-   **Containerização**: Docker, Docker Compose

---

## 2. Infraestrutura e Serviços Core

A infraestrutura básica é orquestrada via [docker-compose.yml](file:///Users/caiohclins/code-projects/apoo-financeiro/docker-compose.yml).

### 2.1. Discovery Service (Eureka)
-   **Diretório**: `/discovery`
-   **Porta**: `8761`
-   **Função**: Registro e descoberta de serviços. Todos os microserviços (Gateway, Identidade, Financeiro, Credito) se registram aqui.
-   **Configuração**:
    -   `registerWithEureka: false` (é o servidor)
    -   `fetchRegistry: false`

### 2.2. API Gateway
-   **Diretório**: `/gateway`
-   **Porta**: `8888` (Exposta para o cliente)
-   **Função**: Ponto de entrada único para o sistema. Roteia requisições para os serviços competentes de acordo com a URL.
-   **Rotas Mapeadas**:
    -   `/identidade/**` -> Serviço `IDENTIDADE` (`lb://IDENTIDADE`)
    -   `/financeiro/**` -> Serviço `FINANCEIRO` (`lb://FINANCEIRO`)
    -   `/credito/**` -> Serviço `CREDITO` (`lb://CREDITO`)
-   **Dependências**: `spring-cloud-starter-gateway`, `eureka-client`.

### 2.3. Keycloak (Identity Provider)
-   **Container Name**: `sistema-financeiro-keycloak`
-   **Porta**: `8080`
-   **Função**: Gerenciamento de usuários, login, emissão de tokens JWT.
-   **Banco de Dados**: Conecta ao Postgres (`keycloak_db`).
-   **Realm**: `financeiro` (Configurado via variáveis de ambiente/import).

### 2.4. PostgreSQL (Database)
-   **Container Name**: `sistema-financeiro-postgres`
-   **Porta**: `5432`
-   **Função**: Banco de dados relacional para todos os serviços e Keycloak.
-   **Databases Utilizados**:
    -   `keycloak_db` (implícito para Keycloak)
    -   `financial_db` (utilizado pelos serviços `financeiro` e `credito`, e possivelmente `identidade`).

---

## 3. Microserviços de Negócio

### 3.1. Serviço: Identidade
-   **Diretório**: `/identidade`
-   **Porta**: `8081`
-   **Função**: Gerenciamento de usuários. Atua como um *wrapper* ou adaptador para o Keycloak, facilitando a criação de usuários e possivelmente armazenando dados complementares.
-   **Pacote Base**: `br.com.ufrpe.apoo.identidade`
-   **Dependências**: `spring-boot-starter-web`, `data-jpa`, `postgresql`, `security`, `oauth2-resource-server`.
-   **Entidades** (`dominio`):
    -   `Usuario.java`: Representa o usuário no sistema. Provavelmente mapeia ID do Keycloak para dados locais.
-   **API (via Postman)**:
    -   `POST /identidade/usuarios`: Cadastro de usuário.
    -   `POST /identidade/login`: Login (retorna Token JWT).

### 3.2. Serviço: Financeiro
-   **Diretório**: `/financeiro`
-   **Porta**: `8083`
-   **Função**: Gerenciamento de fluxo de caixa (receitas e despesas).
-   **Pacote Base**: `br.com.ufrpe.apoo.financeiro`
-   **Entidades** (`dominio`):
    -   `Lancamento.java`: Representa uma receita ou despesa. Atributos prováveis: valor, data, tipo, descrição, usuário (owner).
    -   `Tag.java`: Categorização de lançamentos.
-   **API (via Postman)**:
    -   `GET /financeiro/lancamentos`: Listar.
    -   `POST /financeiro/lancamentos`: Criar.
    -   `GET/DELETE /financeiro/lancamentos/{id}`: Detalhar/Remover.

### 3.3. Serviço: Credito
-   **Diretório**: `/credito`
-   **Porta**: `8082`
-   **Função**: Gerenciamento de cartões de crédito e faturas.
-   **Pacote Base**: `br.com.ufrpe.apoo.credito`
-   **Entidades** (`dominio`):
    -   `Cartao.java`: Cartão de crédito (limite, vencimento, nome).
    -   `Fatura.java`: Faturas associadas aos cartões.
-   **API (via Postman)**:
    -   `GET/POST /credito/cartoes`: Gerenciar cartões.
    -   `GET/POST /credito/cartoes/{id}/faturas`: Gerenciar faturas do cartão.

---

## 4. Mapa de Comunicação e Dependências

### Fluxo de Requisição
1.  **Cliente** faz requisição para `http://localhost:8888/{servico}/{recurso}`.
2.  **Gateway** intercepta, verifica no **Discovery (Eureka)** onde está o serviço `{servico}`.
3.  **Gateway** encaminha a requisição para a instância correta (ex: `financeiro` na porta `8083`).
4.  **Serviço** recebe a requisição.
    -   Verifica Token JWT (Bearer) no header `Authorization` validando com o **Keycloak** (via `issuer-uri`).
    -   Processa a regra de negócio.
    -   Acessa o **PostgreSQL** para persistência.

### Variáveis de Ambiente Importantes (`docker-compose.yml`)
-   `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD`: Credenciais do banco.
-   `KEYCLOAK_HOST`, `DISCOVERY_HOST`: Endereços dos serviços de infraestrutura (necessários para comunicação interna dos containers).
-   `FINANCIAL_DB`: Nome do banco de dados de negócio.

## 5. Estrutura de Diretórios (Resumo)
```
/
├── credito/          # Serviço de Cartões/Faturas
├── discovery/        # Eureka Server
├── financeiro/       # Serviço de Lançamentos
├── gateway/          # API Gateway
├── identidade/       # Serviço de Usuários
├── infra/            # (Implícito) docker-compose.yml, keycloak/, postgres/
└── postman_collection.json # Coleção de testes de API
```
