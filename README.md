### 1. Elasticsearch

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres

  jpa:
    hibernate:
      ddl-auto: update

  elasticsearch:
    uris: http://localhost:9200
```

#### Subir serviços

```bash
docker-compose up -d
```

---

### 2. PostgreSQL (imagem do professor)

```bash
docker run -d --name postgres-tp2 \
  -e POSTGRES_PASSWORD=guildpass \
  -p 5432:5432 \
  leogloriainfnet/postgres-tp2-spring:2.0-mac
```

---

### 3. Ajustar senha

```bash
docker exec -it  psql -U postgres
ALTER USER postgres WITH PASSWORD 'postgres';
```

---

### 4. Verificar containers

```bash
docker ps
```

**Containers esperados:**

- `guilda-elastic`
- `guilda-postgres`

---

## Rodando a aplicação

```bash
mvn spring-boot:run
```

Acesso: [http://localhost:8080](http://localhost:8080)