export const ETAPA = 'Ensino Médio';

export const ANOS = ['1º ano', '2º ano', '3º ano'];

export const CURSOS = [
  { id: 'info', nome: 'Informática', letra: 'A' },
  { id: 'devs', nome: 'Desenvolvimento de Sistemas', apelido: 'Devs', letra: 'B' },
  { id: 'enf', nome: 'Enfermagem', letra: 'C' },
  { id: 'adm', nome: 'Administração', letra: 'D' },
];

export function getCurso(cursoId) {
  return CURSOS.find((c) => c.id === cursoId) ?? null;
}

export const DISCIPLINAS_COMUNS = [
  'Língua Portuguesa', 'Matemática', 'Educação Física', 'Física', 'Química',
  'Biologia', 'História', 'Geografia', 'Filosofia', 'Sociologia', 'Literatura',
  'Inglês', 'Espanhol',
];

export const DISCIPLINAS_POR_CURSO = {
  info: ['Lógica de Programação', 'Redes de Computadores', 'Manutenção de Computadores'],
  devs: ['Banco de Dados', 'Programação Web', 'Estrutura de Dados'],
  enf: ['Anatomia e Fisiologia', 'Farmacologia', 'Primeiros Socorros'],
  adm: ['Contabilidade Básica', 'Marketing', 'Gestão de Pessoas'],
};

export function getDisciplinasDoCurso(cursoId) {
  return [...DISCIPLINAS_COMUNS, ...(DISCIPLINAS_POR_CURSO[cursoId] ?? [])];
}

const PROFESSOR_TECNICO = {
  info: 'Prof. Rafael Souza',
  devs: 'Prof.ª Carla Mendes',
  enf: 'Prof. André Lima',
  adm: 'Prof.ª Fernanda Duarte',
};
const PROFESSOR_NUCLEO_COMUM = 'Prof.ª Helena Martins';

export const TURMAS = ANOS.flatMap((anoLabel, anoIndex) => {
  const anoNum = anoIndex + 1;
  return CURSOS.map((curso) => ({
    id: `${anoNum}-${curso.id}`,
    nome: `${anoNum}º ${curso.letra} — ${curso.apelido ?? curso.nome}`,
    cursoId: curso.id,
    ano: anoLabel,
    anoNum,
    etapa: ETAPA,
    professores: [PROFESSOR_TECNICO[curso.id], PROFESSOR_NUCLEO_COMUM],
    disciplinas: getDisciplinasDoCurso(curso.id),
    status: 'Ativa',
  }));
});

export function getTurma(turmaId) {
  return TURMAS.find((t) => t.id === turmaId) ?? null;
}
export function getTurmasDoAno(anoLabel) {
  return TURMAS.filter((t) => t.ano === anoLabel);
}
export function getTurmasDoCurso(cursoId) {
  return TURMAS.filter((t) => t.cursoId === cursoId);
}

export const ALUNOS = [
  { id: 'AR', nome: 'Ana Clara Ribeiro', turmaId: '1-info', matriculaId: 'MAT-1042', situacao: 'Ativo', dataNascimento: '12/03/2010', responsavel: 'Fernanda Ribeiro' },
  { id: 'MO', nome: 'Mariana Oliveira', turmaId: '1-info', matriculaId: 'MAT-1043', situacao: 'Ativo', dataNascimento: '05/07/2010', responsavel: 'Carlos Oliveira' },
  { id: 'PC', nome: 'Pedro Cardoso', turmaId: '1-devs', matriculaId: 'MAT-1044', situacao: 'Ativo', dataNascimento: '19/01/2010', responsavel: 'Sandra Cardoso' },
  { id: 'LF', nome: 'Laura Ferreira', turmaId: '1-devs', matriculaId: 'MAT-1045', situacao: 'Ativo', dataNascimento: '02/11/2009', responsavel: 'Marcos Ferreira' },
  { id: 'MC2', nome: 'Mateus Correia', turmaId: '1-enf', matriculaId: 'MAT-1053', situacao: 'Ativo', dataNascimento: '09/02/2010', responsavel: 'Patrícia Correia' },
  { id: 'SN', nome: 'Sofia Nunes', turmaId: '1-enf', matriculaId: 'MAT-1047', situacao: 'Em análise', dataNascimento: '21/10/2010', responsavel: 'Ricardo Nunes' },
  { id: 'IM', nome: 'Isabela Martins', turmaId: '1-adm', matriculaId: 'MAT-1051', situacao: 'Pendente', dataNascimento: '08/04/2010', responsavel: 'André Martins' },
  { id: 'JS', nome: 'João Pedro Santos', turmaId: '2-info', matriculaId: 'MAT-1046', situacao: 'Ativo', dataNascimento: '23/02/2009', responsavel: 'Renata Santos' },
  { id: 'GS', nome: 'Gustavo Silva', turmaId: '2-devs', matriculaId: 'MAT-1050', situacao: 'Ativo', dataNascimento: '30/09/2009', responsavel: 'Juliana Silva' },
  { id: 'BC', nome: 'Beatriz Costa', turmaId: '2-devs', matriculaId: 'MAT-1049', situacao: 'Ativo', dataNascimento: '14/06/2009', responsavel: 'Paulo Costa' },
  { id: 'RT', nome: 'Rafaela Teixeira', turmaId: '2-enf', matriculaId: 'MAT-1052', situacao: 'Ativo', dataNascimento: '11/12/2009', responsavel: 'Eduardo Teixeira' },
  { id: 'CD', nome: 'Camila Duarte', turmaId: '2-adm', matriculaId: 'MAT-1054', situacao: 'Ativo', dataNascimento: '27/05/2009', responsavel: 'Marcelo Duarte' },
  { id: 'LA', nome: 'Lucas Almeida', turmaId: '3-info', matriculaId: 'MAT-1041', situacao: 'Pendente', dataNascimento: '17/05/2008', responsavel: 'Camila Almeida' },
  { id: 'DR', nome: 'Daniel Rocha', turmaId: '3-devs', matriculaId: 'MAT-1048', situacao: 'Pendente', dataNascimento: '25/08/2008', responsavel: 'Vanessa Rocha' },
  { id: 'VH', nome: 'Vitor Hugo Alves', turmaId: '3-enf', matriculaId: 'MAT-1055', situacao: 'Ativo', dataNascimento: '03/07/2008', responsavel: 'Simone Alves' },
  { id: 'LP', nome: 'Larissa Prado', turmaId: '3-adm', matriculaId: 'MAT-1056', situacao: 'Ativo', dataNascimento: '15/09/2008', responsavel: 'Roberto Prado' },
];

export function getAlunosDaTurma(turmaId) {
  return ALUNOS.filter((a) => a.turmaId === turmaId);
}
export function getAluno(alunoId) {
  return ALUNOS.find((a) => a.id === alunoId) ?? null;
}

export const MATRICULAS = [
  { id: 'MAT-1041', aluno: 'Lucas Almeida', alunoId: 'LA', tipo: 'Rematrícula', cursoId: 'info', ano: '3º ano', turmaId: '3-info', status: 'Pendente', documentosPendentes: ['Comprovante de residência atualizado'], escolaOrigem: null },
  { id: 'MAT-1047', aluno: 'Sofia Nunes', alunoId: 'SN', tipo: 'Nova', cursoId: 'enf', ano: '1º ano', turmaId: '1-enf', status: 'Em análise', documentosPendentes: ['Conferência final da documentação'], escolaOrigem: null },
  { id: 'MAT-1048', aluno: 'Daniel Rocha', alunoId: 'DR', tipo: 'Transferência', cursoId: 'devs', ano: '3º ano', turmaId: '3-devs', status: 'Pendente', documentosPendentes: ['Histórico escolar da escola de origem'], escolaOrigem: 'E.E. Prof. Carlos Drummond' },
  { id: 'MAT-1049', aluno: 'Beatriz Costa', alunoId: 'BC', tipo: 'Rematrícula', cursoId: 'devs', ano: '2º ano', turmaId: '2-devs', status: 'Concluída', documentosPendentes: [], escolaOrigem: null },
  { id: 'MAT-1051', aluno: 'Isabela Martins', alunoId: 'IM', tipo: 'Transferência', cursoId: 'adm', ano: '1º ano', turmaId: '1-adm', status: 'Pendente', documentosPendentes: ['Histórico escolar', 'Declaração de transferência'], escolaOrigem: 'Colégio Municipal Ipê Amarelo' },
  { id: 'MAT-1054', aluno: 'Camila Duarte', alunoId: 'CD', tipo: 'Nova', cursoId: 'adm', ano: '2º ano', turmaId: '2-adm', status: 'Concluída', documentosPendentes: [], escolaOrigem: null },
];