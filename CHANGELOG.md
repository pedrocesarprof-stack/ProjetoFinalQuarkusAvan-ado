# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato segue o padrão [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## [1.0.0] - 2026-05-26

### Adicionado
- Documentação completa no `README.md` com instruções de execução, endpoints e tecnologias
- Arquivo `CHANGELOG.md` com histórico de versões
- Arquivo `docker-compose.yml` para execução em ambiente produtivo (PostgreSQL + Kafka + Zookeeper)
- Arquivo `requests.http` com exemplos de todas as requisições da API
- Documentação do enunciado do projeto em `docs/PROJETO_FINAL.md`

---

## [0.5.0] - 2026-05-26

### Adicionado
- `ProdutoResource`: CRUD completo de produtos (`GET`, `POST`, `PUT`, `DELETE`)
- Controle de acesso por roles: `USER` pode listar, `ADMIN` pode criar/editar/deletar
- Cache com `@CacheResult` nos endpoints de listagem e busca por ID
- Invalidação de cache com `@CacheInvalidateAll` em operações de escrita
- Integração com `EstoqueKafkaProducer` para alertas automáticos quando `estoque < 5`
- `application.properties` com configurações de Hibernate, JWT e canais Kafka
- `import.sql` como placeholder para scripts de carga inicial

---

## [0.4.0] - 2026-05-26

### Adicionado
- `EstoqueKafkaProducer`: producer Kafka que envia alertas de estoque baixo ao tópico `estoque-baixo`
- `EstoqueKafkaConsumer`: consumer Kafka que consome o tópico `estoque-baixo` e registra log de alerta
- Configuração dos canais de mensageria reativa (`estoque-baixo-out` e `estoque-baixo-in`) no `application.properties`

---

## [0.3.0] - 2026-05-26

### Adicionado
- `TokenService`: serviço de geração de tokens JWT com issuer, subject, groups e expiração de 1 hora
- `AuthResource`: endpoints `POST /auth/register` e `POST /auth/login` com validação de campos, hash BCrypt e geração de token
- `DataInitializer`: inicializador de dados que cria usuários padrão (`admin@loja.com` / `user@loja.com`) na inicialização
- Chaves RSA (`privateKey.pem` / `publicKey.pem`) para assinatura e verificação JWT

---

## [0.2.0] - 2026-05-26

### Adicionado
- `Produto`: entidade JPA com campos `nome`, `descricao`, `preco` e `estoque`, mapeada via Panache
- `Usuario`: entidade JPA com campos `nome`, `email` (único), `senha` e `role`, com método `findByEmail`
- `LoginRequest`: DTO com campos `email` e `senha`
- `RegisterRequest`: DTO com campos `nome`, `email`, `senha` e `role`

---

## [0.1.0] - 2026-05-26

### Adicionado
- Estrutura base do projeto gerada pelo Quarkus Initializer
- `pom.xml` com dependências: Quarkus REST, Hibernate ORM Panache, PostgreSQL, SmallRye JWT, Cache e Kafka
- Scripts Maven Wrapper (`mvnw`, `mvnw.cmd`) para execução sem Maven instalado
- Dockerfiles para build JVM, Legacy JAR, Native e Native Micro em `src/main/docker`
- `.gitignore` padrão para projetos Java/Quarkus
- `ExampleResource`: endpoint de health check `GET /hello`

