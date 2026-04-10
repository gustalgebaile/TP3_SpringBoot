### 1. Elasticsearch, Kibana e Redis

```yaml
version: '3.8'

services:
  postgres-db:
    image: leogloriainfnet/postgres-tp2-spring:2.0-win
    container_name: guilda-postgres
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_DB=postgres

  elasticsearch-db:
    image: leogloriainfnet/elastic-tp2-spring:1.0-windows
    container_name: guilda-elastic
    ports:
      - "9200:9200"
    environment:
      - discovery.type=single-node
      - ES_JAVA_OPTS=-Xms512m -Xmx512m
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
ALTER USER postgres WITH PASSWORD 'guildpass';
```

---

### 4. Verificar containers

```bash
docker ps
```

**Containers esperados:**

- `postgres-tp2`
- `elasticsearch-tp3`
- `kibana-tp3`
- `redis-cache-tp3`

---

## Rodando a aplicação

```bash
mvn spring-boot:run
```

Acesso: [http://localhost:8080](http://localhost:8080)