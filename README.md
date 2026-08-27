Aluno Online - Backend

Backend da plataforma Aluno Online, desenvolvido em Java e Spring Boot para disponibilizar uma API REST para gerenciamento de informações escolares.

Objetivo

Centralizar as regras de negócio e a persistência dos dados do sistema escolar, permitindo que o frontend em React consuma a API para operações de cadastro, consulta, atualização e exclusão.

Stack atual

Java 21

Spring Boot 4.1.0

Spring Data JPA

Hibernate ORM 7.x

Spring Web / REST

Spring Security

MySQL 8.0

Maven 3.8+

Lombok

React no frontend

Thunder Client para testes da API

Arquitetura

React / Thunder Client
          |
          | HTTP / JSON
          v
   Spring Boot API
          |
    +-----+-----+
    |           |
Controller     Security
    |
  Service
    |
 Repository
    |
  JPA/Hibernate
    |
    v
  MySQL

Camadas

Controller recebe as requisições HTTP e devolve as respostas.

Service concentra regras de negócio e validações de relacionamento.

Repository utiliza Spring Data JPA para acesso ao banco.

Model representa as entidades persistidas e seus relacionamentos.

Config concentra configurações transversais, incluindo segurança.

Estrutura do backend

Api/
├── src/main/java/com/App_Escola/Api/
│   ├── Config/
│   ├── Controller/
│   ├── Model/
│   ├── Repository/
│   ├── Service/
│   └── ApiApplication.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md

Entidades principais

O domínio escolar já foi estruturado com entidades para:

Escola

Aluno

Professor

Turma

Disciplina

Calendário letivo

Evento

Atividade

Boletim

Nota

Frequência

E-mail do aluno

Responsável

Relação aluno-responsável

Avaliação

Relação professor-disciplina

Histórico escolar

Curso

Relação turma-curso

Avisos

Relacionamentos importantes

Escola 1:N Turma
Escola 1:N Professor
Escola 1:1 Calendário Letivo (modelo atual)
Turma 1:N Aluno
Aluno 1:N AlunoEmail
Aluno 1:1 Boletim (modelo atual)
Boletim 1:N Nota
Disciplina 1:N Nota
Aluno 1:N Frequência
Disciplina 1:N Frequência
Disciplina 1:N Atividade
Calendário 1:N Evento
Professor N:N Turma
Professor 1:N ProfessorDisciplina
Disciplina 1:N ProfessorDisciplina
Aluno 1:N AlunoResponsavel
Responsável 1:N AlunoResponsavel
Turma N:1 Curso por meio de TurmaCurso
Disciplina 1:N Avaliação
Aluno 1:N Histórico Escolar
Escola 1:N Avisos
Turma 0:N Avisos

Banco de dados

Banco atual de desenvolvimento:

aluno_online

SGBD:

MySQL 8.0

A aplicação utiliza conexão local durante o desenvolvimento:

jdbc:mysql://localhost:3306/aluno_online

Ajustes já realizados no banco

Padronização de professor.matricula, substituindo o identificador legado id_professor.

Conversão de professor.data_nascimento de texto para DATE.

Remoção do vínculo antigo disciplina.professor_matricula.

Criação de professor_disciplina para representar o vínculo entre professor e disciplina.

Padronização do INEP para VARCHAR(20) nas relações com a tabela oficial escola.

Migração dos vínculos de professor, turma e calendario_letivo para a tabela escola, eliminando dependência da tabela duplicada escola_model no modelo definitivo.

Observação: o banco passou por várias alterações manuais durante o desenvolvimento. Recomenda-se consolidar essas mudanças em migrations (por exemplo, Flyway) antes da disponibilização para toda a equipe.

Segurança

O projeto já possui a dependência do Spring Security e uma configuração inicial de segurança para desenvolvimento.

A arquitetura planejada de autenticação é:

Login
  |
  v
Spring Security
  |
  v
JWT
  |
  v
Authorization: Bearer <token>

Perfis planejados:

ROLE_ADMIN

ROLE_PROFESSOR

ROLE_ALUNO

ROLE_RESPONSAVEL

Regra principal já definida para o produto:

ADMIN -> pode cadastrar aluno, professor e responsável
PROFESSOR -> operações pedagógicas permitidas
ALUNO -> acesso aos próprios dados permitidos
RESPONSAVEL -> acesso aos alunos vinculados

A configuração permitAll() utilizada em desenvolvimento não deve ser usada em produção.

Frontend

O frontend está sendo desenvolvido em React e deverá consumir os endpoints do backend por HTTP/JSON.

Exemplo de fluxo:

React
  |
  | POST /aluno/cadastrar
  v
AlunoController
  |
  v
AlunoService
  |
  v
AlunoRepository
  |
  v
MySQL

Durante o desenvolvimento local, é esperado:

Frontend: http://localhost:5173
Backend:  http://localhost:8080

A configuração de CORS deve ser restrita às origens necessárias.

Execução local

Requisitos

Java 21

Maven

MySQL 8

Node.js e npm para o frontend

Backend

cd ~/Documentos/Api
mvn clean
mvn spring-boot:run

Por padrão, a API utiliza a porta 8080.

Teste

O backend pode ser testado com Thunder Client, Postman ou Insomnia.

Exemplo:

GET http://localhost:8080/aluno

ou, conforme os Controllers configurados:

POST http://localhost:8080/aluno/cadastrar

Equipe e colaboração

O backend está sendo desenvolvido por uma equipe de aproximadamente 8 estagiários. A organização recomendada para o repositório é:

backend/
frontend/

Para o trabalho em equipe:

feature -> Pull Request -> revisão -> merge

Cada alteração relevante deve ser validada localmente antes de ser integrada.

Próximos passos

Finalizar e validar todos os Models e relacionamentos JPA.

Consolidar o esquema do banco em migrations versionadas.

Revisar todos os Repository/Service/Controller.

Criar UsuarioModel e Role.

Implementar login com senha criptografada.

Implementar JWT.

Aplicar autorização por role e por recurso.

Introduzir DTOs e Bean Validation.

Finalizar tratamento global de exceções.

Integrar autenticação ao React.

Criar ambiente compartilhado de desenvolvimento/staging.

Preparar CI/CD e infraestrutura de produção.

Status

Projeto em desenvolvimento ativo.

A base do backend, modelagem relacional e estrutura de segurança já foram iniciadas. A autenticação JWT e a infraestrutura em nuvem ainda devem ser finalizadas antes da entrada em produção.