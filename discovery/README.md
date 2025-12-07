# Discovery Service (Eureka Server)

Este microserviço é responsável pelo **Service Discovery** de toda a arquitetura. Ele utiliza o **Netflix Eureka** para permitir que os serviços se registrem e descubram uns aos outros dinamicamente.

## 📋 Visão Geral

-   **Porta**: `8761`
-   **Tecnologia**: Spring Cloud Netflix Eureka Server
-   **Dependência Principal**: `spring-cloud-starter-netflix-eureka-server`

## ⚙️ Configurações Principais (`application.yml`)

Este serviço atua apenas como servidor, portanto não se registra em si mesmo.

```yaml
server:
  port: 8761

eureka:
  client:
    registerWithEureka: false
    fetchRegistry: false
```

## 🚀 Como Executar

Via Maven:
```bash
./mvnw spring-boot:run
```

Via Docker (na raiz do projeto):
```bash
docker-compose up -d discovery
```

## 🔗 Serviços Registrados
Todos os microserviços de negócio (Identidade, Financeiro, Crédito) e o Gateway devem aparecer no painel do Eureka em:
`http://localhost:8761`
