# API Gateway

Este serviço atua como o **Ponto Único de Entrada** para o sistema financeiro. Ele utiliza o **Spring Cloud Gateway** para rotear as requisições para os microserviços apropriados, baseando-se no registro do Eureka.

## 📋 Visão Geral

-   **Porta**: `8888`
-   **Tecnologia**: Spring Cloud Gateway
-   **Dependências Principais**: `spring-cloud-starter-gateway`, `spring-cloud-starter-netflix-eureka-client`

## 🛣️ Rotas Mapeadas

As rotas são configuradas dinamicamente via `lb://` (Load Balancer) usando os nomes dos serviços no Eureka.

| Caminho | Serviço Destino | ID do Serviço (Eureka) |
| :--- | :--- | :--- |
| `/identidade/**` | Serviço de Identidade | `IDENTIDADE` |
| `/financeiro/**` | Serviço Financeiro | `FINANCEIRO` |
| `/credito/**` | Serviço de Crédito | `CREDITO` |

## ⚙️ Configurações Principais

```yaml
server:
  port: ${GATEWAY_PORT:8888}

spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: false # Rotas definidas manualmente abaixo
      routes:
        - id: identidade
          uri: lb://IDENTIDADE
          predicates:
            - Path=/identidade/**
          filters:
            - StripPrefix=1
        # ... outras rotas
```

## 🚀 Como Executar

Via Maven:
```bash
./mvnw spring-boot:run
```

Via Docker (na raiz):
```bash
docker-compose up -d gateway
```

**Nota:** O Gateway depende do **Discovery** estar ativo para resolver os nomes dos serviços (`lb://...`).
