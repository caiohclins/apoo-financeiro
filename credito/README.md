# Crédito Service

Este microserviço gerencia os produtos de crédito, especificamente **Cartões de Crédito** e suas respectivas **Faturas**.

## 📋 Visão Geral

-   **Porta**: `8082`
-   **Pacote Base**: `br.com.ufrpe.apoo.credito`
-   **Banco de Dados**: PostgreSQL (tabelas `cartao`, `fatura`)

## 🏗️ Estrutura Interna

### 🏛️ Entidades (`dominio`)
-   **`Cartao`**: Representa um cartão de crédito de um usuário.
    -   `id`: Identificador único.
    -   `nome`: Apelido do cartão (ex: "Nubank", "Visa Infinite").
    -   `numero`: Últimos dígitos (para identificação).
    -   `limite`: Limite total de crédito.
    -   `usuarioId`: Dono do cartão (Linked via JWT).
    -   `diaVencimentoFatura`: Dia de vencimento da fatura.
    -   `melhorDiaCompra`: Dia ideal para compra.
-   **`Fatura`**: Fatura mensal associada a um cartão.
    -   `cartao`: Relacionamento ManyToOne.
    -   `dataVencimento`: Data de vencimento desta fatura específica.
    -   `valorTotal`: Total a pagar.
    -   `fechada`: Status da fatura (aberta/fechada).

### 🎮 Camadas
-   **Service (`servico`)**: Lógica de negócio (`CartaoService`).
-   **Controladores (`controladores`)**: `CartaoController`.
-   **DTOs (`dto`)**: `CartaoRequestDTO`, `CartaoResponseDTO`, `FaturaRequestDTO`, `FaturaResponseDTO`.
-   **Exceções (`excecao`)**: `RecursoNaoEncontradoException`, `AcessoNegadoException`.

## 🔒 Segurança
Assim como no serviço Financeiro, utiliza **JWT Bearer Token** para autenticação e verificação de posse (`usuarioId`).

## 🔌 API Endpoints
### Cartões
| Método | Recurso | Descrição |
| :--- | :--- | :--- |
| `GET` | `/cartoes` | Lista todos os cartões do usuário. |
| `POST` | `/cartoes` | Cadastra um novo cartão. |
| `GET` | `/cartoes/{id}` | Busca detalhes do cartão. |
| `DELETE` | `/cartoes/{id}` | Remove um cartão. |

### Faturas
| Método | Recurso | Descrição |
| :--- | :--- | :--- |
| `GET` | `/cartoes/{id}/faturas` | Lista todas as faturas de um cartão específico. |
| `POST` | `/cartoes/{id}/faturas` | Cria uma fatura (geralmente gerada automaticamente, mas exposta para testes/MVP). |

## ⚙️ Configurações Principais
-   **Porta**: 8082
-   **Eureka**: Registra-se como `CREDITO`.
-   **DB**: Connecta ao banco `financial_db` (mesmo banco lógico do Financeiro, mas tabelas separadas).
