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
    -   `idIdentidade`: ID do dono do lançamento (vinculado ao token JWT).
    -   `cartaoId`: Opcional, se vinculado a um cartão de crédito.
    -   `tags`: Lista de tags associadas.
-   **`Tag`**: Categorias para lançamentos.
    -   `nome`: Ex: "Alimentação", "Transporte".
    -   `cor`: Código de cor para UI.

### 🎮 Camadas
-   **Service (`servico`)**: Lógica de negócio (`LancamentoService`, `TagService`). Gerencia transações e regras de tags.
-   **Controladores (`controladores`)**: `FinanceiroController`, `TagController`.
-   **DTOs (`dto`)**: `LancamentoRequestDTO`, `LancamentoResponseDTO`, `TagRequestDTO`, `TagResponseDTO`.
-   **Exceções (`excecao`)**: `RecursoNaoEncontradoException`, `AcessoNegadoException`.

## 🔒 Segurança e Autenticação
Todos os endpoints são protegidos e requerem um **Token JWT** válido no header `Authorization`.
O serviço extrai o `sub` (Subject) do token para filtrar os dados, garantindo que um usuário acesse apenas seus próprios registros (Multi-tenancy lógico).

## 🔌 API Endpoints

| Método | Recurso | Descrição |
| :--- | :--- | :--- |
| `GET` | `/lancamentos` | Lista todos os lançamentos do usuário logado. |
| `POST` | `/lancamentos` | Cria um novo lançamento. Use `tagIds` para associar tags. |
| `GET` | `/lancamentos/{id}` | Busca detalhes de um lançamento. |
| `PUT` | `/lancamentos/{id}` | Atualiza um lançamento. |
| `DELETE` | `/lancamentos/{id}` | Remove um lançamento. |
| `GET` | `/lancamentos/cartao/{cartaoId}` | Lista lançamentos por cartão e mês/ano (Params: `mes`, `ano`). |
| `GET` | `/tags` | Lista todas as tags. |
| `POST` | `/tags` | Cria uma nova tag. |
| `PUT` | `/tags/{id}` | Atualiza uma tag. |
| `GET` | `/tags/{id}` | Busca uma tag por ID. |
| `DELETE` | `/tags/{id}` | Remove uma tag por ID. |

### Exemplo de Payload (Criar Lançamento)
```json
{
    "nome": "Supermercado Semanal",
    "valor": 350.00,
    "dataPagamento": "2023-11-20",
    "tipo": "DESPESA",
    "tagIds": [1, 5]
}
```

## ⚙️ Configurações Principais
-   **Eureka**: Registra-se como `FINANCEIRO`.
-   **Resource Server**: Valida a assinatura do token JWT com o Keycloak.
