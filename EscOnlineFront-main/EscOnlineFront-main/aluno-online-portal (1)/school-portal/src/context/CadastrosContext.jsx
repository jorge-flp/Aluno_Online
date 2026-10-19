import React, { createContext, useCallback, useContext, useMemo, useState } from 'react';
import { ALUNOS, getTurma, getCurso } from '../data/SchoolData';

const CadastrosContext = createContext(null);

const ALUNOS_INICIAIS = ALUNOS.map((a) => {
  const turma = getTurma(a.turmaId);
  return {
    id: a.id,
    matricula: a.matriculaId,
    nomeCompleto: a.nome,
    nomeSocial: '',
    dataNascimento: a.dataNascimento,
    email: '',
    telefone: '',
    cpf: '',
    rg: '',
    etnia: '',
    sexoGenero: '',
    endereco: '', numero: '', complemento: '', bairro: '', cidade: '', estado: '', cep: '',
    responsavelPrincipal: a.responsavel,
    parentesco: 'Responsável legal',
    telefoneResponsavel: '',
    emailResponsavel: '',
    cursoId: turma?.cursoId ?? '',
    ano: turma?.ano ?? '',
    turmaId: a.turmaId,
    dataMatricula: '',
    turno: 'Manhã',
    observacoes: '',
    status: a.situacao,
  };
});

const PROFESSORES_INICIAIS = [
  { id: 'HM', matricula: 'PROF-2021-HM', nomeCompleto: 'Helena Martins', areaAtuacao: 'Núcleo comum · Língua Portuguesa e Literatura', disciplinas: 'Língua Portuguesa, Literatura', turmasIds: ALUNOS_INICIAIS.map((a) => a.turmaId).filter((v, i, arr) => arr.indexOf(v) === i), turno: 'Manhã e Tarde', status: 'Ativo' },
  { id: 'RS', matricula: 'PROF-2019-RS', nomeCompleto: 'Rafael Souza', areaAtuacao: 'Curso técnico · Informática', disciplinas: 'Lógica de Programação, Redes de Computadores', turmasIds: ['1-info', '2-info', '3-info'], turno: 'Manhã', status: 'Ativo' },
  { id: 'CM', matricula: 'PROF-2022-CM', nomeCompleto: 'Carla Mendes', areaAtuacao: 'Curso técnico · Desenvolvimento de Sistemas', disciplinas: 'Banco de Dados, Programação Web', turmasIds: ['1-devs', '2-devs', '3-devs'], turno: 'Tarde', status: 'Ativo' },
  { id: 'AL', matricula: 'PROF-2018-AL', nomeCompleto: 'André Lima', areaAtuacao: 'Curso técnico · Enfermagem', disciplinas: 'Anatomia e Fisiologia, Farmacologia', turmasIds: ['1-enf', '2-enf', '3-enf'], turno: 'Manhã', status: 'Ativo' },
  { id: 'FD', matricula: 'PROF-2020-FD', nomeCompleto: 'Fernanda Duarte', areaAtuacao: 'Curso técnico · Administração', disciplinas: 'Contabilidade Básica, Marketing', turmasIds: ['1-adm', '2-adm', '3-adm'], turno: 'Tarde', status: 'Ativo' },
].map((p) => ({
  nomeSocial: '', dataNascimento: '', email: '', telefone: '', cpf: '', rg: '', etnia: '',
  endereco: '', numero: '', complemento: '', bairro: '', cidade: '', estado: '', cep: '',
  formacaoAcademica: '', instituicaoFormacao: '', grauFormacao: '',
  dataContratacao: '', observacoes: '',
  ...p,
}));

const RESPONSAVEIS_INICIAIS = [
  { id: 'FR', matricula: 'RESP-1042', nomeCompleto: 'Fernanda Ribeiro', parentesco: 'Mãe', alunoVinculado: 'Ana Clara Ribeiro', cpf: '', telefone: '', email: '', endereco: '', numero: '', complemento: '', bairro: '', cidade: '', estado: '', cep: '', observacoes: '', status: 'Ativo' },
  { id: 'CO', matricula: 'RESP-1043', nomeCompleto: 'Carlos Oliveira', parentesco: 'Pai', alunoVinculado: 'Mariana Oliveira', cpf: '', telefone: '', email: '', endereco: '', numero: '', complemento: '', bairro: '', cidade: '', estado: '', cep: '', observacoes: '', status: 'Ativo' },
];

const FUNCIONARIOS_INICIAIS = [
  { id: 'PC2', matricula: 'FUNC-2020-01', nomeCompleto: 'Patrícia Correia', cargo: 'Secretária escolar', cpf: '', telefone: '', email: '', dataContratacao: '', endereco: '', numero: '', complemento: '', bairro: '', cidade: '', estado: '', cep: '', observacoes: '', status: 'Ativo' },
  { id: 'MC3', matricula: 'FUNC-2019-02', nomeCompleto: 'Marina Costa', cargo: 'Diretora', cpf: '', telefone: '', email: '', dataContratacao: '', endereco: '', numero: '', complemento: '', bairro: '', cidade: '', estado: '', cep: '', observacoes: '', status: 'Ativo' },
];

let nextAlunoId = 1000;
let nextProfessorId = 1000;
let nextResponsavelId = 1000;
let nextFuncionarioId = 1000;

export function CadastrosProvider({ children }) {
  const [alunos, setAlunos] = useState(ALUNOS_INICIAIS);
  const [professores, setProfessores] = useState(PROFESSORES_INICIAIS);
  const [responsaveis, setResponsaveis] = useState(RESPONSAVEIS_INICIAIS);
  const [funcionarios, setFuncionarios] = useState(FUNCIONARIOS_INICIAIS);

  const addAluno = useCallback((dados) => {
    const idNum = nextAlunoId++;
    const novo = { id: `NA${idNum}`, matricula: `MAT-${idNum}`, status: 'Ativo', ...dados };
    setAlunos((prev) => [novo, ...prev]);
    return novo;
  }, []);

  const addProfessor = useCallback((dados) => {
    const idNum = nextProfessorId++;
    const novo = { id: `NP${idNum}`, matricula: `PROF-2026-${idNum}`, status: 'Ativo', ...dados };
    setProfessores((prev) => [novo, ...prev]);
    return novo;
  }, []);

  const addResponsavel = useCallback((dados) => {
    const idNum = nextResponsavelId++;
    const novo = { id: `NR${idNum}`, matricula: `RESP-${idNum}`, status: 'Ativo', ...dados };
    setResponsaveis((prev) => [novo, ...prev]);
    return novo;
  }, []);

  const addFuncionario = useCallback((dados) => {
    const idNum = nextFuncionarioId++;
    const novo = { id: `NF${idNum}`, matricula: `FUNC-2026-${idNum}`, status: 'Ativo', ...dados };
    setFuncionarios((prev) => [novo, ...prev]);
    return novo;
  }, []);

  const value = useMemo(
    () => ({ alunos, professores, responsaveis, funcionarios, addAluno, addProfessor, addResponsavel, addFuncionario }),
    [alunos, professores, responsaveis, funcionarios, addAluno, addProfessor, addResponsavel, addFuncionario]
  );

  return <CadastrosContext.Provider value={value}>{children}</CadastrosContext.Provider>;
}

export function useCadastros() {
  const ctx = useContext(CadastrosContext);
  if (!ctx) throw new Error('useCadastros deve ser usado dentro de <CadastrosProvider>');
  return ctx;
}

export function resumoAluno(a) {
  const curso = getCurso(a.cursoId);
  const turma = getTurma(a.turmaId);
  return {
    id: (a.nomeCompleto || '??').split(' ').map((p) => p[0]).slice(0, 2).join('').toUpperCase(),
    name: a.nomeCompleto,
    code: a.matricula,
    etapa: curso?.nome ?? '—',
    turma: turma?.nome ?? '—',
    status: a.status,
  };
}

export function resumoProfessor(p) {
  const turmasNomes = (p.turmasIds ?? []).map((id) => getTurma(id)?.nome).filter(Boolean);
  return {
    id: (p.nomeCompleto || '??').split(' ').map((s) => s[0]).slice(0, 2).join('').toUpperCase(),
    name: p.nomeCompleto,
    code: p.matricula,
    etapa: p.areaAtuacao || '—',
    turma: turmasNomes.length ? `${turmasNomes.length} turma(s)` : '—',
    status: p.status,
  };
}

export function resumoResponsavel(r) {
  return {
    id: (r.nomeCompleto || '??').split(' ').map((s) => s[0]).slice(0, 2).join('').toUpperCase(),
    name: r.nomeCompleto,
    code: r.matricula,
    etapa: r.parentesco || '—',
    turma: r.alunoVinculado || '—',
    status: r.status,
  };
}

export function resumoFuncionario(f) {
  return {
    id: (f.nomeCompleto || '??').split(' ').map((s) => s[0]).slice(0, 2).join('').toUpperCase(),
    name: f.nomeCompleto,
    code: f.matricula,
    etapa: f.cargo || '—',
    turma: '—',
    status: f.status,
  };
}