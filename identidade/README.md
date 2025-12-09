# Identidade Service

Este serviço é responsável pelo **Gerenciamento de Identidade e Acesso** (IAM) do sistema. Ele atua como uma camada de abstração sobre o **Keycloak**, simplificando o cadastro de usuários e o fluxo de login para os clientes da API. Além disso, mantém uma cópia local mínima dos dados do usuário para fins de integridade referencial no sistema.

## 📋 Visão Geral

-   **Porta**: `8081`
-   **Pacote Base**: `br.com.ufrpe.apoo.identidade`
-   **Banco de Dados**: PostgreSQL (tabela `usuario`)

## 🏗️ Estrutura Interna

### 🏛️ Entidades (`dominio`)
-   **`Usuario`**:
    -   `id` (Long): Identificador local.
    -   `nome` (String): Nome completo.
    -   `email` (String): E-mail do usuário.
    -   `idIdentidade` (String): UUID do usuário no Keycloak.

### 🎮 Camadas
-   **Service (`servico`)**: Contém a lógica de negócio (`UsuarioService`), encapsulando a comunicação com o Keycloak.
-   **Controladores (`controladores`)**: Recebem DTOs e delegam para os serviços.
-   **Adapters (`adapter`)**: `IProvedorIdentidade` para abstração do Keycloak.
-   **DTOs (`dto`)**: Objetos para transferência de dados (`UsuarioDTO`, `LoginDTO`).
-   **Exceções (`excecao`)**: Exceções de domínio (`RecursoNaoEncontradoException`, `RegraNegocioException`).

### 🔌 Adapters (`adapter`)
-   **`IProvedorIdentidade`**: Interface para comunicação com o provedor de identidade.

## 🔌 API Endpoints

| Método | Recurso | Descrição |
| :--- | :--- | :--- |
| `POST` | `/usuarios` | Cria um novo usuário no Keycloak e no banco local. |
| `POST` | `/login` | Autentica o usuário no Keycloak e retorna o Token JWT. |

### Exemplo de Payload (Cadastro)
```json
{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "123"
}
```

## ⚙️ Configurações Principais (`application.yml`)
-   **DB**: Conecta ao Postgres para salvar a entidade `Usuario`.
-   **Keycloak**: Configura credenciais de `admin-client` para poder criar usuários programaticamente.
-   **Segurança**: Configurado como Resource Server para validar tokens (embora seus endpoints principais sejam abertos ou usem client credentials internos para o login).

## 🚀 Comunicação Externa
-   **Keycloak**: Comunica-se via REST API (admin-cli) para criar usuários e via protocolo OIDC para obter tokens.
