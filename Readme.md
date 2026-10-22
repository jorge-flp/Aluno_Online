# Aluno Online

Sistema web de gerenciamento escolar desenvolvido com **React/Vite** no frontend e **Java 21 + Spring Boot** no backend.

O projeto foi criado para centralizar informações acadêmicas e administrativas da escola, permitindo o gerenciamento de alunos, responsáveis, professores, turmas, disciplinas, notas, boletins, documentos e outros recursos escolares.

---

## 1. Visão geral

O **Aluno Online** possui arquitetura separada entre frontend, backend, banco de dados e armazenamento de arquivos.

```text
Frontend React / Vercel
        |
        | HTTPS / JSON
        v
Backend Spring Boot / Railway
        |
        +----------> PostgreSQL / Aiven
        |
        +----------> Supabase Storage
                       |
                       +---- PDFs de atestados
```

### Infraestrutura atual

| Componente | Tecnologia |
|---|---|
| Frontend | React + Vite |
| Hospedagem Frontend | Vercel |
| Backend | Java 21 + Spring Boot |
| Hospedagem Backend | Railway |
| Banco de dados | PostgreSQL |
| Hospedagem do banco | Aiven |
| Arquivos | Supabase Storage |
| Versionamento | Git + GitHub |

---

## 2. Objetivo

O sistema busca facilitar o gerenciamento escolar por meio de uma aplicação centralizada.

Entre os principais recursos estão:

- cadastro e consulta de alunos;
- cadastro de responsáveis;
- vínculo entre aluno e responsável;
- gerenciamento de professores;
- gerenciamento de turmas;
- gerenciamento de disciplinas;
- vínculo professor/turma/disciplina;
- lançamento de notas;
- geração de boletins;
- cadastro de diretores;
- cadastro de coordenadores;
- cadastro de secretários;
- avisos;
- justificativas;
- envio e armazenamento de atestados em PDF.

---

## 3. Tecnologias

### Frontend

- React
- Vite
- JavaScript
- Axios / Fetch API
- HTML5
- CSS3
- Vercel

### Backend

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- Hibernate
- Spring Security
- Bean Validation
- Lombok
- Maven
- JUnit 5
- Mockito
- MockMvc

### Infraestrutura

- PostgreSQL
- Aiven
- Railway
- Supabase Storage
- Git
- GitHub

---

## 4. Estrutura geral

O repositório principal do backend segue esta organização:

```text
Api/
├── .git/
├── README.md
└── backend/
    ├── src/
    │   ├── main/
    │   │   ├── java/com/App_Escola/Api/
    │   │   │   ├── Config/
    │   │   │   ├── Controller/
    │   │   │   ├── Exception/
    │   │   │   ├── Model/
    │   │   │   ├── Repository/
    │   │   │   └── Service/
    │   │   └── resources/
    │   │       └── application.properties
    │   └── test/
    ├── pom.xml
    ├── mvnw
    ├── mvnw.cmd
    ├── .gitignore
    └── .env
```

> O Git principal deve permanecer somente na raiz `Api/.git`.

A pasta:

```text
Api/backend
```

não deve possuir outro `.git`.

Não execute:

```powershell
git init
```

dentro de `backend`.

---

## 5. Arquitetura do backend

A API utiliza separação em camadas:

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
Model / JPA
    |
    v
PostgreSQL
```

### Controller

Responsável por receber as requisições HTTP e expor os endpoints REST.

### Service

Responsável pelas regras de negócio.

### Repository

Responsável pelo acesso e persistência dos dados utilizando Spring Data JPA.

### Model

Representa as entidades do sistema e seus relacionamentos.

### Config

Contém configurações como:

- CORS;
- Spring Security;
- integração entre frontend e backend.

### Exception

Centraliza o tratamento de erros da API.

---

## 6. Banco de dados

O projeto utiliza **PostgreSQL hospedado na Aiven**.

Configuração do backend:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

O Hibernate está configurado atualmente com:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

As credenciais não devem ser versionadas no GitHub.

---

## 7. Supabase Storage

O Supabase é utilizado para armazenar arquivos PDF relacionados aos atestados.

Bucket utilizado:

```text
atestados
```

Fluxo:

```text
Frontend
   |
   v
Spring Boot
   |
   +-------> PostgreSQL / Aiven
   |            |
   |            +---- metadados
   |
   +-------> Supabase Storage
                |
                +---- PDF
```

Configuração:

```properties
supabase.url=${SUPABASE_URL}
supabase.key=${SUPABASE_KEY}
supabase.bucket=atestados
```

---

## 8. Frontend

O frontend utiliza React com Vite.

URL de produção:

```text
https://esconlinefront-school-portal.vercel.app
```

A URL do backend deve ser configurada por variável de ambiente:

```env
VITE_API_URL=https://backend-production-c612.up.railway.app
```

Exemplo:

```javascript
api.get("/alunos");
```

Isso gera a chamada:

```text
https://backend-production-c612.up.railway.app/alunos
```

Não utilizar:

```text
/api/alunos
```

caso o Controller do backend exponha:

```text
/alunos
```

---

## 9. Backend

O backend está hospedado no Railway.

URL pública:

```text
https://backend-production-c612.up.railway.app
```

Configuração esperada no Railway:

```text
Root Directory: backend
```

Variáveis necessárias:

```text
DB_URL
DB_USERNAME
DB_PASSWORD

SUPABASE_URL
SUPABASE_KEY

FRONTEND_URL
FRONTEND_PREVIEW_ORIGIN_PATTERN
```

A variável `PORT` é fornecida automaticamente pelo Railway.

---

## 10. CORS

O backend aceita requisições do frontend principal e dos ambientes de desenvolvimento.

Origens previstas:

```text
http://localhost:5173
https://esconlinefront-school-portal.vercel.app
https://*.vercel.app
```

Variáveis:

```env
FRONTEND_URL=https://esconlinefront-school-portal.vercel.app
FRONTEND_PREVIEW_ORIGIN_PATTERN=https://*.vercel.app
```

Métodos permitidos:

```text
GET
POST
PUT
PATCH
DELETE
OPTIONS
```

Controllers individuais não precisam utilizar:

```java
@CrossOrigin(origins = "*")
```

quando o CORS global estiver corretamente configurado.

---

## 11. Segurança

O projeto possui Spring Security configurado.

Durante a fase atual de integração, os endpoints permanecem liberados:

```java
.authorizeHttpRequests(auth ->
        auth.anyRequest().permitAll()
)
```

Esse comportamento é temporário.

A próxima evolução de segurança deverá incluir autenticação e autorização por perfil.

Perfis planejados:

```text
DIRETOR
COORDENADOR
SECRETARIO
PROFESSOR
RESPONSAVEL
ALUNO
```

Cada usuário deverá acessar somente as funcionalidades compatíveis com seu perfil.

---

## 12. Principais módulos

### Alunos

Cadastro, consulta e exclusão de alunos.

### Responsáveis

Gerenciamento dos responsáveis pelos alunos.

### Aluno x Responsável

Relacionamento entre aluno e responsável com possibilidade de registrar parentesco.

### Professores

Cadastro e gerenciamento dos professores.

### Turmas

Gerenciamento das turmas escolares.

### Disciplinas

Cadastro das disciplinas.

### Professor x Turma x Disciplina

Define qual professor ministra determinada disciplina em determinada turma.

### Notas

Registro de notas por aluno, disciplina e bimestre.

### Boletins

Consulta e geração de boletins com média geral e conceito.

### Atestados

Upload de PDFs para o Supabase e armazenamento de metadados no PostgreSQL.

### Gestão administrativa

Inclui:

- diretor;
- coordenador;
- secretário.

---

## 13. Principais endpoints

### Alunos

```http
GET    /alunos
GET    /alunos/{matricula}
POST   /alunos
DELETE /alunos/{matricula}
```

### Turmas

```http
GET    /turmas
GET    /turmas/{id}
POST   /turmas
PUT    /turmas/{id}
DELETE /turmas/{id}
```

### Responsáveis

```http
GET    /responsaveis
GET    /responsaveis/{id}
POST   /responsaveis
PUT    /responsaveis/{id}
DELETE /responsaveis/{id}
```

### Aluno x Responsável

```http
GET    /alunos-responsaveis
GET    /alunos-responsaveis/{id}
POST   /alunos-responsaveis
PUT    /alunos-res