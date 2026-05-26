# 🏆 Projeto Final — API de Gerenciamento de Produtos (Quarkus)

API RESTful com autenticação JWT, CRUD de produtos, cache e integração com Kafka.

## 🚀 Como executar

### Pré-requisitos
- **Java 21+**
- **Docker Desktop** instalado e **rodando** (necessário para Dev Services)

### Executar em modo dev

```bash
./mvnw quarkus:dev
```

> O Quarkus Dev Services sobe automaticamente o **PostgreSQL** e o **Kafka** via Docker. Nenhuma configuração extra necessária!

---

## 👥 Usuários padrão

| E-mail           | Senha      | Role  |
|------------------|------------|-------|
| admin@loja.com   | admin123   | ADMIN |
| user@loja.com    | user123    | USER  |

---

## 📋 Endpoints

### Autenticação

| Método | Rota            | Descrição              |
|--------|-----------------|------------------------|
| POST   | /auth/login     | Login e geração de JWT |
| POST   | /auth/register  | Cadastro de usuário    |

### Produtos (requer `Authorization: Bearer <token>`)

| Método | Rota             | Role          | Descrição          |
|--------|------------------|---------------|--------------------|
| GET    | /produtos        | USER / ADMIN  | Listar produtos    |
| GET    | /produtos/{id}   | USER / ADMIN  | Buscar por ID      |
| POST   | /produtos        | ADMIN         | Cadastrar produto  |
| PUT    | /produtos/{id}   | ADMIN         | Atualizar produto  |
| DELETE | /produtos/{id}   | ADMIN         | Remover produto    |

---

## ⚙️ Tecnologias

- Quarkus 3.x
- Hibernate ORM + Panache
- PostgreSQL (Dev Services)
- SmallRye JWT
- Quarkus Cache
- Kafka via SmallRye Reactive Messaging (Dev Services)
