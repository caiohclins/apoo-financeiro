# Relatório de Projeto: Parte 2 - Arquitetura SOA/Microserviços

## Capa

**Título do Documento:** Relatório da Parte 2 do Projeto - Arquitetura SOA/Microserviços  
**Nome do Projeto:** Sistema de Controle Financeiro  
**Disciplina:** 14108 – ANÁLISE E PROJETO DE SISTEMAS OO  
**Professor:** Lucas Albertins (lucas.albertins@ufrpe.br)

**Nome e E-mail dos Componentes da Equipe:**  
- Caio Henrique Cordeiro Lins (caio.hclins@ufrpe.br)  

**Versão e Data do Documento:**  
Versão 1.0 - 10/12/2025  

## Histórico de Revisões

| Data          | Versão | Descrição do que Foi Feito                                                                 | Responsáveis   |
|---------------|--------|--------------------------------------------------------------------------------------------|----------------|
| 23/11/2025   | 0.1    | Configuração inicial do repositório com commits iniciais. Contem esqueleto(funional) do Postgres, Keycloak e MicroServiço de Identidade, tudo orquestrado via Docker.                                 | Caio H.  C. Lins |
| 26/11/2025   | 0.2    | Configurações extras de Docker e Keycloak, incluindo definições e ajustes de imagens, variáveis de ambiente, refatorações no docker-compose.yml, constraints de placement, delays de restart, comandos Docker Swarm e endpoint de teste de login. Melhorias em tratamento de tokens. | Caio H. C. Lins |
| 28/11/2025   | 0.3    | Refatoração de endpoints de criação de usuários e login com uso de DTOs; Criação dos microserviços financeiro e crédito; | Caio H. C. Lins |
| 07/12/2025   | 0.4    | Adição de documentação abrangente e diagramas arquiteturais; atualização de dependências Spring Boot; implementação de API de gerenciamento de tags; refatoração de paths de contexto; introdução de DTOs, camadas de serviço, mappers e handling global de exceções. | Caio H. C. Lins |
| 08/12/2025   | 0.5    | Implementação de geração de faturas no serviço de crédito, integrando com o serviço financeiro via REST; refatoração de Fatura para DTO dinâmico; adição de endpoints de CRUD faltantes para recursos como Cartao, Lancamento e Tag. | Caio H. C. Lins |
| 08/12/2025   | 0.6    | Refatorações para alinhamento com diagrama de arquitetura: renomeação de endpoints de criação de usuários; renomeação de campos como dataPagamento para dataLancamento, numeroParcelas para quantidadeParcelas e melhorDiaCompra para diaFechamentoFatura; renomeação de usuarioId e keycloakId para idIdentidade em módulos de usuário; renomeação de métodos em serviços e controladores de Lancamento e Tag, com atualizações em DTOs; remoção de número de cartão por segurança, renomeação em módulos de cartões e fatura, e simplificação de DTOs e métodos de serviço. | Caio H. Clins |
| 08/12/2025   | 1.0    | Atualizações finais para suporte ao fluxo completo de dashboard: refinamento de modelos de dados e APIs para visualização integrada de lançamentos, faturas e cartões; adição de documentação de projeto alinhada; melhorias em consistência de identidade e transações financeiras para dashboards dinâmicos. | Caio H. Clins |

## Introdução

Este documento apresenta o relatório da Parte 2 do projeto para a disciplina de Análise e Projeto de Sistemas OO, focando na implementação de uma arquitetura em Microserviços, baseando-se nos documentos de análise da etapa anterior do projeto. O objetivo é descrever a estrutura do sistema financeiro desenvolvido, incluindo os serviços implementados, padrões arquiteturais, tecnologias utilizadas e infraestrutura. O documento está organizado em seções que cobrem a arquitetura de serviços, como microserviços de negócio, API Gateway, Service Discovery e containerização. Não há front-end implementado, porém a API back-end é acessível via ferramentas como Postman.

## Arquitetura de Serviços

### Descrição dos Serviços Implementados

O sistema é composto por microserviços independentes que gerenciam aspectos específicos de um aplicativo financeiro pessoal. Os serviços de negócio interagem entre si para fornecer funcionalidades integradas, como gerenciamento de usuários, lançamentos financeiros e cartões de crédito com geração de faturas. Todos os serviços são protegidos por autenticação JWT via Keycloak.

- **Serviço de Identidade (identidade):** Gerencia usuários, adaptando o provedor de identidade externo, nesse caso o Keycloak. Inclui cadastro e login, persistindo dados locais no PostgreSQL.
- **Serviço Financeiro (financeiro):** Controla lançamentos (receitas/despesas) e tags de categorização, com persistência no PostgreSQL.
- **Serviço de Crédito (credito):** Gerencia cartões de crédito e gera faturas dinâmicas, integrando-se ao serviço financeiro via Feign Client para obter Lançamentos.
- **API Gateway (gateway):** Ponto de entrada único, roteando requisições para os serviços via load balancing (lb://).
- **Service Discovery (discovery):** Utiliza Netflix Eureka para registro e descoberta dinâmica de serviços.

### Padrão de Arquitetura Considerando Tecnologias Utilizadas

A arquitetura segue o padrão de microserviços desacoplados, com comunicação síncrona via HTTP/REST e descoberta de serviços via Eureka. Tecnologias chave incluem:
- **Spring Boot 3.4.12** e **Spring Cloud** para desenvolvimento de microserviços.
- **Netflix Eureka** para Service Discovery.
- **Spring Cloud Gateway** para API Gateway.
- **Keycloak** com OAuth2/OIDC para segurança e autenticação.
- **PostgreSQL** para persistência de dados (bancos: keycloak_db e financial_db).
- **Feign** para integração entre microserviços (ex: crédito consulta financeiro).
- **Java 21** como linguagem principal.
- **Docker e Docker Compose** para containerização e orquestração.

Cada microserviço possui camadas claras: domínio (entidades), serviço (lógica de negócio), controladores (API), DTOs (transferência de dados) e exceções personalizadas. A segurança é aplicada via Resource Server, validando tokens JWT em endpoints protegidos.

### Front-End

Não implementado. O sistema é acessível via API REST, testável com ferramentas como Postman (coleção disponível: postman_collection.json).

### Back-end

#### Implementação de Microserviços

O sistema implementa três microserviços, todos com controle de dados via PostgreSQL, interações necessárias para funcionalidades integradas e autenticação via JWT utilizando Keycloak:

- **Microserviço de Identidade (identidade, porta 8081):** Envolve persistência de usuários no PostgreSQL.
    - Entidades: Usuario (com atributos como nome, email e roles). 
    - Fornece endpoints para criação de usuários (POST /criar-usuario) e login, atuando como adaptador para Keycloak. 
    - Interage indiretamente com outros serviços via autenticação compartilhada (JWT), garantindo que requisições autenticadas possam acessar recursos protegidos em financeiro e credito.
- **Microserviço Financeiro (financeiro, porta 8083):** Envolve persistência de lançamentos e tags no PostgreSQL. 
    - Entidades: Lancamento (valor, data, tipo, usuário, cartaoId) e Tag (nome, cor). 
    - Oferece CRUD completo para lançamentos e tags, com filtros por cartão, mês ou usuário. 
    - Interage com o serviço de Crédito fornecendo dados de transações via Feign Client para geração de faturas, assegurando consistência de dados financeiros.
    - No fluxo de dashboard, endpoints como GET /lancamentos e GET /tags permitem agregação de dados para métricas como balanço mensal e categorização de despesas.
- **Microserviço de Crédito (credito, porta 8082):** Envolve persistência de cartões de crédito no PostgreSQL. 
    - Entidades: Cartao (limite, vencimento, usuário). 
    - Oferece CRUD para cartões e gera faturas dinâmicas (como DTO) consultando lançamentos do serviço Financeiro via Feign. 
    - Essa interação é essencial, pois as faturas são compostas dinamicamente a partir de dados de outros serviços, evitando duplicação de dados.
    - No fluxo de dashboard, endpoints como GET /cartoes/{id}/fatura/{mesAno} fornecem resumos de faturas, integrando com lançamentos para visualizações completas de gastos por cartão.

Todos os microserviços de negócio (identidade, financeiro e credito) requerem interações: por exemplo, o Crédito depende do Financeiro para compor faturas, e todos dependem da autenticação via Identidade/Keycloak para segurança.

#### API Gateway e Service Discovery

- **API Gateway (gateway, porta 8888):** Expõe um ponto único de entrada, roteando paths como **/identidade/**** para lb://IDENTIDADE, **/financeiro/**** para lb://FINANCEIRO e **/credito/**** para lb://CREDITO. Utilizando load balancing via Eureka.
- **Service Discovery (discovery, porta 8761):** Eureka Server para registro automático de serviços. Cada microserviço (IDENTIDADE, FINANCEIRO, CREDITO, GATEWAY) se registra dinamicamente, permitindo descoberta e resiliência em ambientes distribuídos.

#### Infraestrutura

Todos os microserviços e componentes (incluindo Keycloak e PostgreSQL) são executados em containers Docker, orquestrados via Docker Compose. O arquivo docker-compose.yml define serviços com dependências, portas mapeadas e variáveis de ambiente para configuração (ex: credenciais de banco, hosts de serviços). Para escalabilidade, suporta Docker Swarm (comandos disponíveis em documentação).

## Instruções para Executar o Projeto

```

1. Certifique-se de ter Docker e Docker Compose instalados.

2. Clone o repositório: git clone https://github.com/caiohclins/apoo-financeiro.git

3. Navegue até a raiz do projeto: cd apoo-financeiro

4. Extraia o arquivo senhas.zip

5. Inicie os containers: docker-compose up -d

6. Aguarde os serviços subirem (verifique logs com docker-compose logs -f).

7. Acesse o API Gateway em http://localhost:8888 (ex: http://localhost:8888/identidade/criar-usuario para cadastro).

8. Use a coleção Postman (postman_collection.json) para testar endpoints.

9. Para parar: docker-compose down

Nota: Keycloak estará em http://localhost:8080 (keycloak_admin/keycloak_secure_pass para login inicial).
```

## Alterações da Fase Anterior

### Alterações em Entidades
- **Classe Usuario:** Adicionado o atributo -id: long, que armazena um identificador interno do banco de dados. Facilita busca e permite a alteração de provedor de identidade externo se necessário.
- **Outras Classes (Fatura):** Agora utiliza atributos de Lançamentos e Cartões para compor faturas.

### Alterações em Serviços, Controladores e Repositórios
- **Ajustes na organização do diagrama:**
  - **Nomes trocados:** Repositorio de tags e de usuários agora apresentam os nomes corretos.
  - **Localização dos componentes:** Interface e repositorio de usuarios agora estão localizadas no lugar correto.
  - **Preenchimento de métodos faltantes:** Implementação de métodos faltantes no diagrama, principalmeente nas classes relacionadas ao provedor de identidade.
  - **Serviço e Controlador de Dashboard** Criação de serviço de Dashboard e simplificação de controlador de dashboard, assim como ajuste nos relacionamentos.
- **Ajustes em Serviços Existentes:**
  - **ServicoUsuarios:** Remoção do método buscarDadosUsuario() e adição do método login(). Agora se comunica diretamente com o provedor de identidade para autenticação.
  - **Controladores (ex: ControladorCartao, ControladorFatura):** Agora possuem todos os métodos de CRUD, incluindo o buscarTodos.
- **Remoção e Criação de Padrões de Projeto**
  - **FachadaSistemaFinanceiro:** Agora também implementa a o padrão Singleton.
  - **Padrão Iterator:** Removido.

### Novas Relações
- **Relações:** Relações entre entidades destacadas. Relação entre serviço de cartão e serviço de lançamento criada.
- **Direções das relações:** Direções das relações entre classes destacadas.
