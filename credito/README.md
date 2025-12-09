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
    -   `idIdentidade`: Dono do cartão (Linked via JWT).
    -   `diaVencimentoFatura`: Dia de vencimento da fatura.
    -   `melhorDiaCompra`: Dia ideal para compra.
    - `melhorDiaCompra`: Dia ideal para compra.

### 🎮 Camadas
-   **Service (`servico`)**: Lógica de negócio (`CartaoService`, `FaturaService`).
-   **Controladores (`controladores`)**: `CartaoController`, `FaturaController`.
-   **DTOs (`dto`)**: `CartaoRequestDTO`, `CartaoResponseDTO`, `FaturaDTO` (Objeto dinâmico, não persistido).
-   **Exceções (`excecao`)**: `RecursoNaoEncontradoException`, `AcessoNegadoException`.

## 🔒 Segurança
Assim como no serviço Financeiro, utiliza **JWT Bearer Token** para autenticação e verificação de posse (`idIdentidade`).

## 🔌 API Endpoints
### Cartões
| Método | Recurso | Descrição |
| :--- | :--- | :--- |
| `GET` | `/cartoes` | Lista todos os cartões do usuário. |
| `POST` | `/cartoes` | Cadastra um novo cartão. |
| `GET` | `/cartoes/{id}` | Busca detalhes do cartão. |
| `PUT` | `/cartoes/{id}` | Atualiza dados do cartão. |
| `DELETE` | `/cartoes/{id}` | Remove um cartão. |

### Faturas
| Método | Recurso | Descrição |
| :--- | :--- | :--- |
| `GET` | `/faturas?mes=X&ano=Y` | Lista as faturas de todos os cartões do usuário para o mês/ano. |
| `GET` | `/faturas/{cartaoId}?mes=X&ano=Y` | Gera a fatura detalhada de um cartão específico. |

## 🧩 Integração
-   **Financeiro Service**: O serviço se comunica via **OpenFeign** com o microsserviço `financeiro` para buscar os lançamentos (transações) referentes ao período da fatura.


## ⚙️ Configurações Principais
-   **Porta**: 8082
-   **Eureka**: Registra-se como `CREDITO`.
-   **DB**: Connecta ao banco `financial_db` (mesmo banco lógico do Financeiro, mas tabelas separadas).
