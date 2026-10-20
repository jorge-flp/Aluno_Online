# Aluno Online — Backend

Backend da aplicação **Aluno Online**, desenvolvido para um sistema de gerenciamento escolar com API REST, persistência em PostgreSQL na nuvem e armazenamento de arquivos em nuvem.

O projeto foi construído com **Java 21**, **Spring Boot 4.1.0**, **Spring Data JPA/Hibernate**, **PostgreSQL (Aiven)** e **Supabase Storage**.

---

## 1. Objetivo do projeto

O backend tem como objetivo fornecer uma API REST para centralizar as operações do sistema escolar, permitindo o cadastro, consulta, alteração e exclusão de informações relacionadas aos usuários e processos administrativos da escola.

Entre os recursos trabalhados estão:

- Alunos
- Responsáveis
- Relacionamento entre Aluno e Responsável
- Diretores
- Secretários
- Coordenadores
- Justificativas de falta
- Avisos
- Atestados e seus arquivos PDF

A aplicação foi organizada em camadas para facilitar manutenção, testes, evolução e integração futura com o frontend em React.

---

## 2. Tecnologias utilizadas

- **Java 21**
- **Spring Boot 4.1.0**
- **Spring Data JPA**
- **Hibernate ORM 7.4.1.Final**
- **Maven / Maven Wrapper**
- **PostgreSQL**
- **Aiven** — hospedagem do banco PostgreSQL
- **Supabase Storage** — armazenamento dos arquivos PDF de atestados
- **Lombok**
- **Bean Validation**
- **Thunder Client / Postman** — testes da API
- **Git e GitHub** — versionamento e colaboração

---

## 3. Arquitetura

A aplicação segue uma arquitetura em camadas:

```text
Cliente (Thunder Client / Postman / React)
                 |
                 v
            Controller
                 |
                 v
              Service
                 |
                 v
             Repository
                 |
                 v
        PostgreSQL / Aiven
```

Para arquivos de atestados, existe também o fluxo de armazenamento externo:

```text
Cliente
   |
   v
Spring Boot API
   |
   +-----> PostgreSQL / Aiven
   |          (metadados e links)
   |
   +-----> Supabase Storage
              (PDFs)
```

A ideia é evitar armazenar arquivos binários pesados diretamente no banco principal. O PostgreSQL mantém os dados e metadados necessários, enquanto o Supabase Storage mantém os PDFs.

---

## 4. Estrutura do projeto

```text
Api/
└── backend/
    ├── src/
    │   ├── main/
    │   │   ├── java/com/App_Escola/Api/
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

### Camadas

**Model**

Representa as entidades do domínio e os respectivos mapeamentos JPA para o banco de dados.

**Repository**

Responsável pelo acesso aos dados utilizando Spring Data JPA.

**Service**

Centraliza as operações e regras da aplicação entre Controller e Repository.

**Controller**

Expõe os endpoints HTTP da API REST.

**Exception**

Destinada ao tratamento de exceções e respostas de erro da aplicação.

---

## 5. Principais entidades e recursos

### Aluno

Representa os alunos cadastrados no sistema, incluindo informações pessoais e de contato.

Também foram aplicadas validações para dados obrigatórios, tamanho de campos, formato de e-mail e data de nascimento.

### Responsável

Representa os responsáveis pelos alunos, com dados como nome, CPF, e-mail e telefone.

### AlunoResponsável

Representa o relacionamento entre aluno e responsável, permitindo registrar também o parentesco.

O relacionamento foi tratado como uma entidade própria para permitir controle da associação e prevenção de vínculos duplicados.

### Diretor

Cadastro dos diretores da instituição.

### Secretário

Cadastro dos secretários da instituição.

### Coordenador

Cadastro dos coordenadores da instituição.

### Justificativa de Falta

Recurso destinado ao registro e gerenciamento de justificativas relacionadas às faltas dos alunos.

### Avisos

Recurso destinado ao cadastro e consulta de comunicados/avisos da escola.

### Atestados

Recurso destinado ao gerenciamento dos dados dos atestados e do respectivo arquivo PDF armazenado no Supabase Storage.

---

## 6. Banco de dados — PostgreSQL com Aiven

O projeto começou utilizando banco relacional local e posteriormente foi migrado para **PostgreSQL hospedado na Aiven**.

A aplicação utiliza JPA/Hibernate para persistência e foi configurada para atualizar a estrutura do banco durante o desenvolvimento:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

A conexão com o banco utiliza variáveis de ambiente para evitar que a senha fique exposta no código-fonte:

```properties
spring.datasource.password=${DB_PASSWORD}
```

A senha real não deve ser versionada no GitHub.

---

## 7. Supabase Storage

O **Supabase Storage** foi integrado ao projeto Spring Boot como repositório de armazenamento em nuvem dedicado aos arquivos binários, especificamente os **PDFs de atestados**.

### Para que serviu

O armazenamento dos arquivos foi separado do banco PostgreSQL para evitar que o banco principal precise lidar diretamente com arquivos binários pesados. O PostgreSQL fica responsável pelos metadados e pelas informações necessárias para localizar o documento.

### Comunicação

A aplicação realiza a comunicação com o Supabase por meio da API REST e da classe `RestTemplate` do Spring Boot, utilizando autenticação com uma chave de serviço apropriada ao backend.

A credencial não deve ser exposta no código-fonte. A configuração usa uma variável de ambiente:

```properties
supabase.key=${SUPABASE_KEY}
supabase.bucket=atestados
```

### Normalização dos nomes dos arquivos

Foi implementado tratamento e normalização das strings utilizadas nos nomes dos arquivos, removendo acentos, caracteres especiais e espaços.

Essa etapa resolveu os problemas de chaves inválidas (`InvalidKey`) e garantiu que os nomes dos arquivos fossem aceitos pelo bucket.

### URLs públicas

O sistema foi validado com geração de URLs de acesso ao documento armazenado no bucket `atestados`.

O link gerado é retornado pela aplicação e pode ser persistido junto aos metadados no PostgreSQL da Aiven.

### Fluxo completo de atestado

O fluxo ponta a ponta implementado foi:

```text
Upload do PDF
     |
     v
Spring Boot
     |
     +-----> Supabase Storage
     |          |
     |          +-----> PDF armazenado
     |
     +-----> PostgreSQL / Aiven
                |
                +-----> metadados + link do arquivo
```

Também foi implementado o fluxo de exclusão:

```text
DELETE
  |
  +-----> remove registro do banco Aiven
  |
  +-----> remove o PDF do bucket Supabase
```

O fluxo de upload, persistência do link e exclusão foi validado de ponta a ponta.

---

## 8. Configuração do `application.properties`

A configuração principal usa variáveis de ambiente para os valores sensíveis:

```properties
spring.application.name=Api

# PostgreSQL - Aiven
spring.datasource.url=jdbc:postgresql://SEU_HOST:SEU_PORT/defaultdb?sslmode=require
spring.datasource.username=avnadmin
spring.datasource.password=${DB_PASSWORD}

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Supabase Storage
supabase.url=https://SEU-PROJETO.supabase.co
supabase.key=${SUPABASE_KEY}
supabase.bucket=atestados

# Uploads
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB

# API
server.port=8081
```

> **Importante:** não coloque senhas, `service_role`, tokens ou outras credenciais diretamente neste arquivo.

---

## 9. Variáveis de ambiente

As principais variáveis utilizadas no projeto são:

```env
DB_PASSWORD=SUA_SENHA_DO_AIVEN
SUPABASE_KEY=SUA_CHAVE_DO_SUPABASE
```

O arquivo local pode ser mantido em:

```text
backend/.env
```

Esse arquivo deve permanecer fora do versionamento.

> **Observação:** o Spring Boot não lê arquivos `.env` automaticamente. As variáveis precisam estar disponíveis no ambiente de execução, por exemplo pelo terminal ou pela configuração de execução da IDE, ou por uma ferramenta/biblioteca específica para carregamento de `.env`.

---

## 10. `.gitignore` e segurança

O projeto foi organizado para evitar o envio de arquivos locais e credenciais ao GitHub.

O `.env` do backend é ignorado por meio de:

```gitignore
.env
```

Também foram removidas configurações específicas de IDE que não são necessárias para o código-fonte compartilhado, como arquivos/pastas do VS Code e IntelliJ.

Durante o desenvolvimento, uma credencial do Aiven foi detectada pelo GitHub Push Protection. A configuração foi então alterada para utilizar variável de ambiente no lugar da senha diretamente no `application.properties`.

> Credenciais que já tenham sido expostas devem ser rotacionadas no serviço correspondente.

---

## 11. Execução do projeto

No Windows, estando dentro da pasta `backend`:

```powershell
cd C:\Users\Jorge\Desktop\Api\backend
```

Como o projeto possui Maven Wrapper, a execução pode ser feita com:

```powershell
.\mvnw.cmd spring-boot:run
```

Para compilar o projeto:

```powershell
.\mvnw.cmd clean install
```

Para compilar sem executar os testes:

```powershell
.\mvnw.cmd clean install -DskipTests
```

A API foi configurada para executar na porta:

```text
http://localhost:8081
```

---

## 12. Testes realizados

Os testes da API foram realizados por meio de ferramentas de requisição HTTP, como Thunder Client/Postman.

### CRUD

Para os principais recursos foram previstos/testados:

- `POST` — criação
- `GET` — listagem
- `GET /{id}` — consulta individual
- `PUT /{id}` — atualização
- `DELETE /{id}` — exclusão

### Testes de validação

Também foram considerados casos de:

- campo obrigatório vazio;
- e-mail inválido;
- tamanho de campo fora do limite;
- data inválida ou futura;
- JSON inválido;
- campos ausentes;
- identificador inexistente;
- CPF duplicado;
- relacionamento duplicado;
- aluno inexistente no relacionamento;
- responsável inexistente no relacionamento.

### Relacionamento Aluno x Responsável

O relacionamento foi testado por meio da criação da associação e posterior consulta, incluindo cenários de vínculo inválido e duplicado.

### Paginação

Nos recursos que possuem paginação, são realizados testes com parâmetros como:

```text
?page=0&size=5
```

Também podem ser verificados tamanho de página diferente, segunda página e página sem resultados.

### Persistência

O fluxo de validação do banco segue a sequência:

```text
POST pela API
   ↓
GET pela API
   ↓
SELECT no PostgreSQL/Aiven
```

Também foi considerado o teste de reinicialização do backend para confirmar que os dados continuam persistidos no banco.

---

## 13. Fluxo de desenvolvimento

A evolução do projeto passou por algumas etapas importantes:

1. Estruturação inicial do backend Spring Boot.
2. Organização em Controller, Service, Repository e Model.
3. Implementação dos recursos escolares.
4. Implementação do relacionamento Aluno x Responsável.
5. Implementação das validações de dados.
6. Migração do banco para PostgreSQL hospedado na Aiven.
7. Configuração do acesso por variáveis de ambiente.
8. Integração do Supabase Storage para PDFs de atestados.
9. Tratamento e normalização dos nomes dos arquivos enviados.
10. Implementação de upload e exclusão de arquivos.
11. Testes da API e da persistência no banco.
12. Organização e limpeza dos arquivos do repositório.
13. Versionamento e colaboração utilizando Git/GitHub.

---

## 14. Git e GitHub

O projeto é versionado utilizando Git e hospedado no GitHub.

A estrutura do repositório foi organizada para manter somente os arquivos relevantes ao projeto e evitar o envio de configurações específicas de IDE e credenciais.

Comandos utilizados durante o desenvolvimento incluem:

```powershell
git status
git add
git commit
git log --oneline
git push
```

Quando foi necessário reescrever um commit que continha uma credencial removida, foi utilizado o `commit --amend` e, após a alteração do histórico local, o envio do histórico foi tratado com cuidado utilizando `--force-with-lease`.

---

## 15. Integração futura com o frontend

A arquitetura foi preparada para integração com um frontend em React.

O fluxo planejado é:

```text
React
  |
  v
Axios / HTTP
  |
  v
Spring Boot API
  |
  +----> Service
  |
  +----> Repository
  |
  v
PostgreSQL / Aiven
```

Para arquivos de atestados, o backend também realiza a comunicação com o Supabase Storage.

O frontend não acessa diretamente o PostgreSQL ou o Supabase para operações de negócio; as requisições passam pela API do backend.

---

## 16. Próximas etapas

- Finalizar e padronizar os endpoints de todos os recursos.
- Consolidar o tratamento global de exceções e mensagens de erro.
- Finalizar a suíte de testes automatizados.
- Integrar o frontend React com os endpoints da API.
- Evoluir autenticação e autorização conforme a necessidade do projeto.
- Documentar os endpoints e contratos da API.
- Revisar configurações de produção e gerenciamento de segredos.

---

## 17. Status atual

O backend possui a base arquitetural definida, integração com PostgreSQL na Aiven, integração com Supabase Storage para os PDFs de atestados, recursos de gerenciamento escolar e estrutura preparada para testes e futura integração com o frontend.

A prioridade atual é garantir que a conexão com os serviços em nuvem esteja corretamente configurada por variáveis de ambiente, validar os endpoints no ambiente de execução e concluir a integração com o frontend.

---

## 18. Autor / Equipe

Desevolvido por:
- Jorge
- Heitor

Projeto desenvolvido como aplicação acadêmica e colaborativa para gerenciamento escolar.
