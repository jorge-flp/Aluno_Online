🎓 ##Aluno Online — Backend##

API REST do sistema escolar Aluno Online
Desenvolvida para centralizar regras de negócio, persistência, segurança e integração com o frontend em React.

<div align="center">

Java 21 · Spring Boot 4.1 · JPA/Hibernate · MySQL 8 · Spring Security · Maven

</div>

📌 ##Visão geral##

O Aluno Online é uma plataforma voltada ao gerenciamento de informações escolares. O backend fornece uma API REST para operações relacionadas a alunos, professores, turmas, disciplinas, responsáveis, avaliações, notas, frequência, histórico escolar, cursos e avisos.

O projeto está sendo desenvolvido por uma equipe de aproximadamente 8 estagiários, portanto a organização por camadas, padronização do código, segurança e controle de versão são prioridades do desenvolvimento.

🏗️ ##Arquitetura##

                         ┌─────────────────────┐
                         │       React         │
                         │     Frontend        │
                         └──────────┬──────────┘
                                    │
                              HTTP / JSON
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │    Spring Boot      │
                         │        API          │
                         └──────────┬──────────┘
                                    │
                 ┌──────────────────┼──────────────────┐
                 │                  │                  │
                 ▼                  ▼                  ▼
            Controller          Security            Config
                 │
                 ▼
              Service
                 │
                 ▼
             Repository
                 │
                 ▼
           JPA / Hibernate
                 │
                 ▼
            ┌───────────┐
            │  MySQL 8  │
            └───────────┘

##Camadas##

##Camada##

##Responsabilidade##

##Controller##

##Receber requisições HTTP e retornar respostas da API.##

##Service##

##Concentrar regras de negócio e validações.##

##Repository##

##Realizar o acesso aos dados com Spring Data JPA.##

##Model##

##Representar entidades, atributos e relacionamentos JPA.##

##Config##

##Centralizar configurações da aplicação, incluindo segurança.##

📂 ##Estrutura do projeto##

Api/
├── .github/
├── .mvn/
├── .vscode/
├── src/
│   ├── main/
│   │   ├── java/com/App_Escola/Api/
│   │   │   ├── Config/
│   │   │   ├── Controller/
│   │   │   ├── Model/
│   │   │   ├── Repository/
│   │   │   ├── Service/
│   │   │   └── ApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md

🧰 ##Stack tecnológica##

☕ Java 21

🍃 Spring Boot 4.1.0

🌐 Spring Web / REST

🗄️ Spring Data JPA

🔗 Hibernate ORM

🔐 Spring Security

🐬 MySQL 8.0

📦 Maven

🧩 Lombok

⚛️ React no frontend

🧪 Thunder Client para testes

🗃️ ##Domínio do sistema##

#Entidades principais#

Aluno
AlunoEmail
AlunoResponsavel
Responsavel
Professor
ProfessorDisciplina
Turma
TurmaCurso
Curso
Disciplina
Atividade
Avaliacao
Boletim
Nota
Frequencia
CalendarioLetivo
Evento
HistoricoEscolar
Avisos
Escola

##Relacionamentos principais##

Escola 1:N Turma
Escola 1:N Professor
Escola 1:1 CalendarioLetivo       (modelo atual)

Turma 1:N Aluno
Turma N:1 Curso                  (por meio de TurmaCurso)

Professor N:N Turma
Professor 1:N ProfessorDisciplina
Disciplina 1:N ProfessorDisciplina

Aluno 1:N AlunoEmail
Aluno 1:N AlunoResponsavel
Responsavel 1:N AlunoResponsavel
Aluno 1:1 Boletim                 (modelo atual)
Aluno 1:N Frequencia
Aluno 1:N HistoricoEscolar

Boletim 1:N Nota
Disciplina 1:N Nota
Disciplina 1:N Atividade
Disciplina 1:N Avaliacao

CalendarioLetivo 1:N Evento
Escola 1:N Avisos
Turma 0:N Avisos

👨‍🏫 ##Professor × Disciplina##

O vínculo entre professor e disciplina é representado por uma entidade associativa própria:

Professor
   │
   │ 1:N
   ▼
ProfessorDisciplina
   ▲
   │ N:1
   │
Disciplina

##Tabela correspondente:##

professor_disciplina
├── id_professor_disciplina
├── professor_matricula
├── id_disciplina
├── data_inicio
└── data_fim

Isso evita prender uma disciplina a um único professor e permite evolução futura da relação.

👨‍🏫 ##Professor × Turma##

A associação atual utiliza a tabela intermediária professor_turma:

Professor N:N Turma

A relação é mapeada com JPA e não exige uma classe ProfessorTurmaModel enquanto a associação não possuir atributos próprios.

👨‍👩‍👧 ##Aluno × Responsável##

Foi adotada uma entidade associativa para permitir informações próprias do vínculo:

Aluno 1:N AlunoResponsavel N:1 Responsavel

Exemplos de atributos da associação:

parentesco
responsavel_principal

🗄️ ##Banco de dados##

Banco atual

SGBD:     MySQL 8.0
Database: aluno_online
Host:     localhost
Porta:    3306

Conexão utilizada durante o desenvolvimento:

jdbc:mysql://localhost:3306/aluno_online

Ajustes importantes já realizados

professor.id_professor foi padronizado para professor.matricula.

professor.data_nascimento foi convertido para o tipo DATE.

O relacionamento antigo disciplina.professor_matricula foi removido.

Foi criada a tabela professor_disciplina.

O INEP foi padronizado como VARCHAR(20) na relação com a tabela escola.

As referências de professor, turma e calendario_letivo foram direcionadas para a tabela escola.

A tabela duplicada escola_model foi identificada como legado do mapeamento anterior e está sendo eliminada da estrutura definitiva.

⚠️ Importante: durante o desenvolvimento, o banco passou por alterações manuais. Antes de compartilhar a base com toda a equipe ou colocar o sistema em produção, recomenda-se consolidar o histórico em migrations versionadas (por exemplo, Flyway ou Liquibase).

🔐 ##Segurança##

O projeto já utiliza Spring Security e a arquitetura definitiva de autenticação está sendo construída.

Perfis planejados

ROLE_ADMIN
ROLE_PROFESSOR
ROLE_ALUNO
ROLE_RESPONSAVEL

Regras principais

##Perfil##

Exemplos de permissões

##ADMIN##

Cadastrar aluno, professor e responsável; administrar dados da escola.

##PROFESSOR##

Trabalhar com notas, frequência, atividades e alunos de suas turmas.

##ALUNO##

Consultar seus próprios dados, notas, frequência, atividades e avisos permitidos.

##RESPONSAVEL##

Consultar informações dos alunos aos quais está vinculado.

Fluxo planejado de autenticação

Login
  │
  ▼
Spring Security
  │
  ▼
Validação de credenciais
  │
  ▼
JWT
  │
  ▼
Authorization: Bearer <token>
  │
  ▼
Autorização por role e recurso

Regra de negócio crítica

Somente usuários com ROLE_ADMIN deverão ter permissão para operações administrativas como:

POST /aluno/cadastrar
POST /professor/cadastrar
POST /responsavel/cadastrar

A autorização deverá ser aplicada no backend, independentemente dos controles de interface existentes no React.

⚠️ A configuração permitAll() usada durante o desenvolvimento é temporária e não deve ser utilizada em produção.

🌐 ##Integração com React##

O frontend é desenvolvido em React e consome o backend por HTTP/JSON.

Exemplo:

React
  │
  │ POST /aluno/cadastrar
  ▼
AlunoController
  │
  ▼
AlunoService
  │
  ▼
AlunoRepository
  │
  ▼
MySQL

Durante o desenvolvimento local:

Frontend → http://localhost:5173
Backend  → http://localhost:8080

A configuração de CORS deverá permitir somente as origens necessárias.

🧪 ##Testes da API##

#Ferramentas utilizadas:#

Thunder Client

Postman

Insomnia

##Exemplos:##

GET http://localhost:8080/aluno

POST http://localhost:8080/aluno/cadastrar
Content-Type: application/json

Exemplo de payload:

{
  "nome": "João",
  "cpf": "12345678900",
  "telefone": "88999999999",
  "turma": {
    "id_turma": 1
  }
}

🚀 ##Como executar##

#Pré-requisitos#

Java 21

Maven

MySQL 8

Node.js e npm para o frontend

Verificar Java

java -version

Verificar Maven

mvn -version

##Executar o backend##

cd ~/Documentos/Api
mvn clean
mvn spring-boot:run

##Por padrão:##

http://localhost:8080

🔧 ##Configuração##

As configurações da aplicação ficam em:

src/main/resources/application.properties

Credenciais e segredos não devem ser versionados no Git. Para ambientes compartilhados e produção, a recomendação é utilizar variáveis de ambiente ou um serviço de gerenciamento de secrets.

🌿 ##Git e colaboração##

O backend está sendo desenvolvido por uma equipe de aproximadamente 8 estagiários.

Fluxo recomendado:

Branch de feature
       │
       ▼
Desenvolvimento
       │
       ▼
Pull Request
       │
       ▼
Revisão
       │
       ▼
Merge

Exemplo:

git add .
git commit -m "feat: adiciona autenticação"
git push

Cada alteração relevante deve ser validada localmente antes de ser integrada à branch principal.

☁️ Estratégia de infraestrutura

Desenvolvimento atual

Cada desenvolvedor
       │
       ▼
API local
       │
       ▼
MySQL local

Evolução planejada

                    ┌──────────────┐
                    │    DEV       │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │   STAGING    │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │  PRODUÇÃO    │
                    └──────────────┘

Quando houver infraestrutura e orçamento adequados, o banco compartilhado e a produção poderão ser migrados para uma solução gerenciada em nuvem, mantendo ambientes separados.

🛡️ Segurança futura

Além de Spring Security e JWT, a arquitetura deverá evoluir com:

🔑 Senhas armazenadas somente como hash seguro (ex.: BCrypt).

🎫 JWT com validação adequada.

👮 Autorização por função e por recurso.

🧱 DTOs para limitar propriedades expostas pela API.

✅ Bean Validation.

🌍 CORS restritivo.

🔒 HTTPS em produção.

🚦 Rate limiting para fluxos sensíveis.

📝 Logs e auditoria de ações importantes.

🔐 Segredos fora do código-fonte.

🚫 Tratamento seguro de exceções sem exposição de stack trace.

🔎 Revisão de endpoints administrativos e atuadores antes da produção.

📋 Próximas etapas

[ ] Finalizar revisão de Models e relacionamentos JPA
[ ] Consolidar banco em migrations
[ ] Revisar Repositories
[ ] Revisar Services
[ ] Revisar Controllers

[ ] UsuarioModel
[ ] Role
[ ] PasswordEncoder
[ ] Login
[ ] JWT
[ ] Autorização por role
[ ] Autorização por recurso

[ ] DTOs
[ ] Bean Validation
[ ] Tratamento global de exceções
[ ] CORS definitivo
[ ] Auditoria
[ ] Rate limiting

[ ] Integração completa com React
[ ] Ambiente compartilhado de desenvolvimento
[ ] CI/CD
[ ] Staging
[ ] Deploy de produção

📊 Status do projeto

🟡 Em desenvolvimento ativo

Já estruturado

✅ Projeto Maven/Spring Boot

✅ API REST em camadas

✅ Modelagem JPA em evolução

✅ Integração com MySQL local

✅ Entidades escolares principais

✅ Entidades de expansão do domínio

✅ Relacionamento Professor ↔ Disciplina

✅ Ajustes importantes de estrutura do banco

✅ Spring Security adicionado ao projeto

✅ Integração planejada com React

Em implementação

🔄 Autenticação completa

🔄 JWT

🔄 Roles e autorização

🔄 DTOs e validação

🔄 Padronização definitiva do banco

🔄 Infraestrutura compartilhada

👥 Equipe

Projeto desenvolvido por uma equipe de aproximadamente 8 estagiários.

A organização do backend segue uma arquitetura em camadas para facilitar manutenção, colaboração, testes e evolução do sistema.

📎 Observação final

Este README descreve o estado atual e a direção arquitetural do backend. Funcionalidades marcadas como planejamento ou implementação não devem ser consideradas concluídas até que sejam validadas e integradas ao projeto.