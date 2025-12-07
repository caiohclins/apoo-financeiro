# Financeiro Service

Este microserviço é o núcleo do controle orçamentário pessoal. Ele gerencia as receitas e despesas (Lançamentos) do usuário, permitindo categorização através de tags.

## 📋 Visão Geral

-   **Porta**: `8083`
-   **Pacote Base**: `br.com.ufrpe.apoo.financeiro`
-   **Banco de Dados**: PostgreSQL (tabelas `lancamento`, `tag`, `lancamento_tags`)

## 🏗️ Estrutura Interna

### 🏛️ Entidades (`dominio`)
-   **`Lancamento`**: Representa uma transação financeira.
    -   `id`: Identificador único.
    -   `nome`, `descricao`: Detalhes do lançamento.
    -   `valor`: Quantia monetária.
    -   `dataPagamento`: Data da transação.
    -   `tipo`: `RECEITA` ou `DESPESA` (geralmente string ou enum).
    -   `usuarioId`: ID do dono do lançamento (vinculado ao token JWT).
    -   `cartaoId`: Opcional, se vinculado a um cartão de crédito.
    -   `tags`: Lista de tags associadas.
-   **`Tag`**: Categorias para lançamentos.
    -   `nome`: Ex: "Alimentação", "Transporte".
    -   `cor`: Código de cor para UI.

### 🎮 Controladores (`controladores`)
-   **`FinanceiroController`**: Gerencia operações de CRUD para lançamentos. Implementa segurança a nível de método validando se o recurso pertence ao usuário autenticado.

## 🔒 Segurança e Autenticação
Todos os endpoints são protegidos e requerem um **Token JWT** válido no header `Authorization`.
O serviço extrai o `sub` (Subject) do token para filtrar os dados, garantindo que um usuário acesse apenas seus próprios registros (Multi-tenancy lógico).

## 🔌 API Endpoints

| Método | Recurso | Descrição |
| :--- | :--- | :--- |
| `GET` | `/lancamentos` | Lista todos os lançamentos do usuário logado. |
| `POST` | `/lancamentos` | Cria um novo lançamento. O `usuarioId` é inserido automaticamente via token. |
| `GET` | `/lancamentos/{id}` | Busca detalhes de um lançamento, validando a posse. |
| `DELETE` | `/lancamentos/{id}` | Remove um lançamento. |

### Exemplo de Payload (Criar Lançamento)
```json
{
    "nome": "Supermercado Semanal",
    "valor": 350.00,
    "dataPagamento": "2023-11-20",
    "tipo": "DESPESA",
    "tags": [
        { "nome": "Alimentação" }
    ]
}
```

## ⚙️ Configurações Principais
-   **Eureka**: Registra-se como `FINANCEIRO`.
-   **Resource Server**: Valida a assinatura do token JWT com o Keycloak.
