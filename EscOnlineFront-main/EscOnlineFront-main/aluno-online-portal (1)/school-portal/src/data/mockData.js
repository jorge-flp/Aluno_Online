
import { BookOpen, ClipboardList, FileEdit, GraduationCap, Sparkles, Users } from 'lucide-react';
import { MATRICULAS, TURMAS } from './SchoolData';

export const student = {
  name: 'João Pedro Andrade Souza',
  registration: '2026001234',
  birthDate: '12/03/2008',
  personalEmail: 'joao.andrade@exemplo.com',
  institutionalEmail: 'joao.souza@aluno.exemplo.gov.br',
  className: 'Integrado 3ª Série | Ensino Médio | Profissional | Integral (45h) | TÉCNICO EM DESENVOLVIMENTO DE SISTEMAS',
  father: 'Marcos Souza',
  mother: 'Renata Andrade Souza',
  guardian: 'Não informado',
  avatarInitials: 'JP',
};

export const school = {
  inep: '00000000',
  name: 'EEEP MODELO DE ENSINO PROFISSIONAL',
  address: 'RUA DAS FLORES, 100, CENTRO, CIDADE MODELO, CE',
  cep: '60000-000',
  phone: '(85) 0000-0000',
  email: 'contato@escolamodelo.exemplo.gov.br',
};

export const calendarEvents = [
  { day: '03', weekday: 'SEG', month: 'AGO', title: 'Dia Letivo', info: 'Aula regular' },
  { day: '04', weekday: 'TER', month: 'AGO', title: 'Dia Letivo', info: 'Aula regular' },
  { day: '05', weekday: 'QUA', month: 'AGO', title: 'Dia Letivo', info: 'Aula regular' },
  { day: '06', weekday: 'QUI', month: 'AGO', title: 'Dia Letivo', info: 'Aula regular' },
  { day: '07', weekday: 'SEX', month: 'AGO', title: 'Dia Letivo', info: 'Aula regular' },
  { day: '10', weekday: 'SEG', month: 'AGO', title: 'Dia Letivo', info: 'Aula regular' },
];

export const news = [
  {
    date: '21/08/2026',
    tag: 'NOVO',
    title: 'Cadastro de e-mail pessoal agora disponível',
    excerpt:
      'Agora é possível cadastrar um e-mail pessoal para facilitar a recuperação de conta e melhorar o acesso ao portal.',
  },
];

export const latestPosts = [
  {
    title: 'Guia de acesso ao portal do aluno',
    date: '18/08/2026 às 09:30',
  },
];

export const partners = [
  {
    name: 'Google Classroom',
    color: 'bg-amber-400',
    description:
      'Ajuda alunos e professores a organizar as tarefas, aumentar a colaboração e melhorar a comunicação.',
  },
  {
    name: 'Enem Mix',
    color: 'bg-pink-400',
    description:
      'Simulados online, conteúdo em vídeo e mais de 3.000 aulas em vídeo disponíveis para consulta.',
  },
  {
    name: 'SISEDU',
    color: 'bg-teal-400',
    description:
      'Sistema Online de Avaliação, Suporte e Acompanhamento Educacional.',
  },
  {
    name: 'SIC',
    color: 'bg-sky-500',
    description:
      'Plataforma Educacional para geração de boletins, acompanhamento e emissão de certificados online.',
  },
  {
    name: 'Conexão Educação',
    color: 'bg-purple-400',
    description:
      'Conexão Educação é um sistema de acompanhamento de conteúdos educacionais nos mais diversos formatos.',
  },
  {
    name: 'Rede de Estudos',
    color: 'bg-emerald-500',
    description:
      'Projeto voltado para alunos do Ensino Médio, com foco na preparação para o ENEM.',
  },
];

export const subjects = [
  { name: 'Biologia', b1: '9.0', b2: '—', b3: '—', b4: '—' },
  { name: 'Química', b1: '7.5', b2: '—', b3: '—', b4: '—' },
  { name: 'Física', b1: '10', b2: '—', b3: '—', b4: '—' },
  { name: 'Português', b1: '8.5', b2: '—', b3: '—', b4: '—' },
  { name: 'Matemática', b1: '8.5', b2: '—', b3: '—', b4: '—' },
  { name: 'Filosofia', b1: '9.0', b2: '—', b3: '—', b4: '—' },
  { name: 'Educação Física', b1: '10', b2: '—', b3: '—', b4: '—' },
  { name: 'Sociologia', b1: '10', b2: '—', b3: '—', b4: '—' },
  { name: 'Língua Estrangeira - Inglês', b1: '9.5', b2: '—', b3: '—', b4: '—' },
  { name: 'Língua Estrangeira - Espanhol', b1: '8.0', b2: '—', b3: '—', b4: '—' },
  { name: 'História', b1: '8.0', b2: '—', b3: '—', b4: '—' },
];

export const weekDays = [
  { key: 'seg', label: 'SEG', date: '17/08' },
  { key: 'ter', label: 'TER', date: '18/08' },
  { key: 'qua', label: 'QUA', date: '19/08' },
  { key: 'qui', label: 'QUI', date: '20/08' },
  { key: 'sex', label: 'SEX', date: '21/08' },
];

export const schedule = {
  seg: ['Português', 'Física', 'Matemática', 'Espanhol', 'Inglês', 'Filosofia', 'Sociologia', 'Química', 'Biologia'],
  ter: ['Filosofia', 'Inglês', 'Biologia', 'Sociologia', 'Física', 'Química', 'Espanhol', 'Português', 'Matemática'],
  qua: ['Matemática', 'Filosofia', 'Espanhol', 'Português', 'Sociologia', 'Biologia', 'Química', 'Inglês', 'Física'],
  qui: ['Biologia', 'Matemática', 'Física', 'Inglês', 'Português', 'Espanhol', 'Química', 'Sociologia', 'Filosofia'],
  sex: ['Física', 'Português', 'Química', 'Espanhol', 'Biologia', 'Matemática', 'Filosofia', 'Sociologia', 'Inglês'],
};

export const attendance = [
  { subject: 'Aprofundamento em Matemática', absences: 0 },
  { subject: 'Biologia', absences: 2 },
  { subject: 'Educação Física', absences: 0 },
  { subject: 'Estágio Curricular', absences: 0 },
  { subject: 'Filosofia', absences: 1 },
  { subject: 'Física', absences: 0 },
  { subject: 'Geografia', absences: 0 },
  { subject: 'História', absences: 0 },
  { subject: 'Horário de Estudo I', absences: 0, dividerAfter: true },
  { subject: 'Horário de Estudo II', absences: 3 },
  { subject: 'Língua Estrangeira - Espanhol', absences: 0 },
  { subject: 'Língua Estrangeira - Inglês', absences: 0 },
  { subject: 'Língua Portuguesa', absences: 0 },
];

export const months = ['JAN', 'FEV', 'MAR', 'ABR', 'MAI', 'JUN', 'JUL', 'AGO', 'SET', 'OUT', 'NOV', 'DEZ'];

export const classInfo = {
  crede: 'Crede 16',
  school: school.name,
  className: 'Integrado 3ª Série | Ensino Médio | Profissional | Integral | TÉCNICO EM DESENVOLVIMENTO DE SISTEMAS',
  year: '2026',
  status: 'Letivo',
};

export const classmates = [
  'Amanda Felix', 'Ana Beatriz', 'Antônia Ticyane', 'Bruno Castro',
  'Camila Duarte', 'Danyelle Batista', 'Eduardo Franlin', 'Estela Garcia',
  'Fábio Lima', 'Gustavo Silva', 'Heitor Gonçalves', 'João Victor',
  'Jonas Vicente', 'Jonny Lacerda', 'Jorge Felipe', 'Kyara Duarte',
  'Letícia Silva', 'Levi Mesquita', 'Lívia Gonçalves', 'Marina Alves',
];
export const teacher = {
  name: 'Carlos Eduardo',
  subject: 'Professor de Matemática',
  avatarInitials: 'CE',
};

export const teacherClasses = [
  { id: 1, name: '3ª Série J', info: 'Téc. em Desenv. de Sistemas · Integral', students: 28, avgAttendance: 94, avgGrade: 8.1 },
  { id: 2, name: '2ª Série A', info: 'Ensino Médio Integral', students: 31, avgAttendance: 90, avgGrade: 7.6 },
  { id: 3, name: '1ª Série B', info: 'Ensino Médio Integral', students: 30, avgAttendance: 91, avgGrade: 7.9 },
];

export const eventosEscola = [
  { id: 1, data: '2026-08-28', titulo: 'Reunião de Pais', horario: '19:00', local: 'Auditório' },
  { id: 2, data: '2026-09-02', titulo: 'Semana da Pátria', horario: '08:00', local: 'Pátio Central' },
  { id: 3, data: '2026-09-10', titulo: 'Conselho de Classe', horario: '14:00', local: 'Sala dos Professores' },
];

export const matriculasEscola = [
  { id: 1, nome: 'Rafael Lima', status: 'Pendente' },
  { id: 2, nome: 'Sofia Mendes', status: 'Em análise' },
  { id: 3, nome: 'Bruno Castro', status: 'Aprovada' },
];

export const noticiasEscola = [
  { id: 1, titulo: 'Início do 3º Bimestre', status: 'Publicado' },
  { id: 2, titulo: 'Resultado da Olimpíada de Matemática', status: 'Publicado' },
  { id: 3, titulo: 'Campanha do Agasalho', status: 'Publicado' },
];

export const DEFAULT_FOOTER_STATS = [
  { icon: Users, label: 'Cadastros', value: '418', caption: 'pessoas na comunidade' },
  { icon: BookOpen, label: 'Planejamento', value: '24', caption: 'em revisão esta semana' },
  { icon: Sparkles, label: 'Eventos', value: '3', caption: 'nos próximos 15 dias' },
];

export const CADASTROS_FOOTER_STATS = [
  { icon: GraduationCap, label: 'Turmas', value: String(TURMAS.length), caption: '3 anos · 4 cursos técnicos' },
  { icon: ClipboardList, label: 'Matrículas', value: String(MATRICULAS.length), caption: 'com pendência documental' },
  { icon: FileEdit, label: 'Notas', value: '2º', caption: 'bimestre em andamento' },
];

export const diarioTurma = {
  '3ª Série J': [
    { aluno: 'Amanda Felix Veras', nota: '9,0', faltas: 1, status: 'ok' },
    { aluno: 'Anna Narah Queiroz Silva', nota: '7,1', faltas: 3, status: 'ok' },
    { aluno: 'Antonia Ticyane Oliveira', nota: '9,4', faltas: 0, status: 'ok' },
    { aluno: 'Antonio Eduardo da Silva', nota: '5,1', faltas: 6, status: 'atencao' },
    { aluno: 'Danyelle Batista Alencar', nota: '8,4', faltas: 2, status: 'ok' },
  ],
  '2ª Série A': [
    { aluno: 'Eduardo Franklin da Silva', nota: '6,7', faltas: 4, status: 'ok' },
    { aluno: 'Estela Garcia da Silva', nota: '9,8', faltas: 0, status: 'ok' },
    { aluno: 'Gustavo Silva Goncalves', nota: '5,1', faltas: 8, status: 'atencao' },
  ],
  '1ª Série B': [
    { aluno: 'Heitor Goncalves Teixeira', nota: '7,9', faltas: 2, status: 'ok' },
    { aluno: 'Joao Victor da Silva Guedes', nota: '6,8', faltas: 5, status: 'ok' },
  ],
};

export const guardian = {
  name: 'Marcos Souza',
  relation: 'Pai',
  childName: student.name,
  avatarInitials: 'MS',
};

export const comunicadosResponsavel = [
  { id: 1, data: '25/08/2026', titulo: 'Lembrete: provas bimestrais na próxima semana', resumo: 'Confira o cronograma de avaliações do 2º bimestre por turma no calendário letivo.' },
  { id: 2, data: '21/08/2026', titulo: 'Aluno Online passa a contar com cadastro de e-mail pessoal', resumo: 'Agora é possível inserir login com e-mail próprio para facilitar o acesso à plataforma.' },
  { id: 3, data: '18/08/2026', titulo: 'Reunião de pais e mestres', resumo: 'Reunião marcada para apresentação dos resultados do 2º bimestre.' },
];