# Cardápio Digital — Back End (Java + Spring Boot)

API REST para o sistema de cardápio digital de lanchonete, com autenticação
JWT e 3 perfis de acesso: **VISITANTE**, **USER** e **ADMIN**.

## Stack

- Java 17
- Spring Boot 3.3.2 (Web, Security, Data JPA, Validation)
- JWT (jjwt 0.12.6)
- H2 (banco em memória, pronto para trocar por Postgres/MySQL)
- Lombok

## Como rodar

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.
Console do H2: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:cardapiodb`, usuário `sa`, sem senha).

Ao subir pela primeira vez, o `DataSeeder` popula automaticamente:
- **Admin**: `admin@lanchonete.com` / `admin123`
- **Cliente teste**: `cliente@lanchonete.com` / `cliente123`
- 12 produtos reais de lanchonete (lanches, bebidas, sobremesas, combos) com imagens autênticas.

## Perfis de acesso

| Perfil     | Como se autentica            | Pode fazer |
|------------|-------------------------------|------------|
| VISITANTE  | Sem token (não logado)       | Ver o cardápio (`GET /api/products`). Ao tentar adicionar ao carrinho / finalizar pedido, recebe `401/403` — o front deve redirecionar para o login. |
| USER       | Login normal (`ROLE_USER`)   | Tudo do visitante + montar carrinho, criar pedidos, ver os próprios pedidos, ver o próprio perfil. |
| ADMIN      | `ROLE_ADMIN`                  | Tudo do USER + criar/editar/remover produtos, ver **todos** os pedidos de todos os usuários, atualizar status de qualquer pedido. |

As regras estão centralizadas em `SecurityConfig.java`.

## Principais endpoints

### Autenticação (públicos)
- `POST /api/auth/register` — cadastro (sempre cria `USER`, nunca admin)
- `POST /api/auth/login`

### Produtos / Cardápio
- `GET /api/products` — público (visitante, user ou admin)
- `GET /api/products/{id}` — público
- `POST /api/products` — **ADMIN**
- `PUT /api/products/{id}` — **ADMIN**
- `DELETE /api/products/{id}` — **ADMIN**

### Pedidos (carrinho)
- `POST /api/orders` — **USER/ADMIN** — cria o pedido a partir dos itens do carrinho e já processa o pagamento
- `GET /api/orders` — **USER/ADMIN** — lista os pedidos do usuário logado
- `GET /api/orders/{id}` — **USER/ADMIN** — detalhe de um pedido próprio

### Administração
- `GET /api/admin/orders` — **ADMIN** — todos os pedidos de todos os usuários
- `GET /api/admin/orders/{id}` — **ADMIN**
- `PATCH /api/admin/orders/{id}/status` — **ADMIN** — atualiza status (`PAGO`, `EM_PREPARO`, `PRONTO`, `SAIU_PARA_ENTREGA`, `ENTREGUE`, `CANCELADO`...)
- `GET /api/admin/products` — **ADMIN** — catálogo completo, inclusive indisponíveis

### Pagamento
- `GET /api/payments/methods` — **USER/ADMIN** — métodos aceitos (PIX, cartão, dinheiro)

## Autenticação nas chamadas

Após login/registro, envie o token retornado em todo request autenticado:

```
Authorization: Bearer <token>
```

## Sobre o pagamento

O pagamento é processado automaticamente dentro de `OrderService` ao criar o
pedido (`POST /api/orders`), através da interface `PaymentGateway`
(`service/payment/PaymentGateway.java`).

Por padrão está plugada a `SimulatedPaymentGateway`, que aprova o pagamento
automaticamente — isso porque este ambiente de geração de código não tem
acesso a chaves reais de gateways (Stripe, Mercado Pago, PagSeguro etc).

**Para integrar um gateway real:**
1. Crie uma classe implementando `PaymentGateway` (ex: `MercadoPagoPaymentGateway`) usando o SDK oficial do provedor.
2. Anote com `@Component` e substitua/priorize sobre a simulada (`@Primary` ou removendo a simulada).
3. Nenhuma outra camada do sistema precisa mudar — `OrderService` depende apenas da interface.

## Estrutura de pacotes

```
com.lanchonete.cardapio
├── config          -> SecurityConfig, WebConfig, DataSeeder
├── controller       -> Auth, Product, Order, Admin, Payment, User
├── dto              -> Records de request/response
├── entity           -> User, Product, Order, OrderItem, enums
├── exception         -> BusinessException, GlobalExceptionHandler
├── repository        -> Spring Data JPA
├── security          -> JwtService, JwtAuthFilter, UserDetailsServiceImpl
└── service           -> AuthService, ProductService, OrderService, payment/*
```

## Próximos passos (parte 2)

O Front End (React + Tailwind) será entregue na próxima parte, consumindo
exatamente estes endpoints.
