import { apiFetch } from './api';

async function listarTodasAsPaginas(endpoint) {
  const itens = [];
  let pagina = 0;
  let totalPaginas = 1;

  while (pagina < totalPaginas) {
    const resposta = await apiFetch(`${endpoint}?page=${pagina}&size=100`);
    if (!resposta || !Array.isArray(resposta.content)) {
      throw new Error(`A resposta de ${endpoint} não contém a lista paginada esperada.`);
    }
    itens.push(...resposta.content);
    totalPaginas = Number.isInteger(resposta.totalPages) ? resposta.totalPages : pagina + 1;
    pagina += 1;
  }

  return itens;
}

function json(body) {
  return { method: 'POST', body: JSON.stringify(body) };
}

function cpfSemFormatacao(cpf) {
  return cpf.replace(/\D/g, '');
}

export function listarTurmas() {
  return listarTodasAsPaginas('/turmas');
}

export function criarTurma(dados) {
  return apiFetch('/turmas', json({
    nome: dados.nome.trim(),
    anoSerie: dados.anoSerie.trim(),
  }));
}

export async function listarCadastros() {
  const [alunos, professores, responsaveis, secretarios, diretores, coordenadores] = await Promise.all([
    listarTodasAsPaginas('/alunos'),
    listarTodasAsPaginas('/professores'),
    listarTodasAsPaginas('/responsaveis'),
    listarTodasAsPaginas('/secretarios'),
    listarTodasAsPaginas('/diretores'),
    listarTodasAsPaginas('/coordenadores'),
  ]);

  return {
    alunos: alunos.map((aluno) => ({
      id: aluno.matricula,
      matricula: aluno.matricula,
      nomeCompleto: aluno.nome,
      dataNascimento: aluno.dataNascimento || '',
      email: aluno.email || '',
      cpf: aluno.cpf || '',
      turma: aluno.turma,
      turmaId: aluno.turma?.idTurma,
      status: 'Ativo',
    })),
    professores: professores.map((professor) => ({
      id: professor.idProfessor,
      matricula: professor.idProfessor,
      nomeCompleto: professor.nome,
      cpf: professor.cpf || '',
      email: professor.email || '',
      telefone: professor.telefone || '',
      turmasIds: [],
      status: 'Ativo',
    })),
    responsaveis: responsaveis.map((responsavel) => ({
      id: responsavel.idResponsavel,
      matricula: responsavel.idResponsavel,
      nomeCompleto: responsavel.nome,
      cpf: responsavel.cpf || '',
      email: responsavel.email || '',
      telefone: responsavel.telefone || '',
      parentesco: '',
      alunoVinculado: '',
      status: 'Ativo',
    })),
    funcionarios: [
      ...secretarios.map((item) => ({ ...item, tipo: 'secretario', cargo: 'Secretário(a) escolar' })),
      ...diretores.map((item) => ({ ...item, tipo: 'diretor', cargo: 'Diretor(a)' })),
      ...coordenadores.map((item) => ({ ...item, tipo: 'coordenador', cargo: 'Coordenador(a)' })),
    ].map((funcionario) => ({
      id: `${funcionario.tipo}-${funcionario.id}`,
      matricula: `${funcionario.tipo}-${funcionario.id}`,
      nomeCompleto: funcionario.nome,
      email: funcionario.email || '',
      telefone: funcionario.telefone || '',
      cargo: funcionario.cargo,
      status: 'Ativo',
    })),
  };
}

export async function criarAluno(dados) {
  const turmaId = Number(dados.turmaId);
  if (!Number.isInteger(turmaId) || turmaId <= 0) {
    throw new Error('Selecione uma turma válida cadastrada no backend.');
  }

  const aluno = await apiFetch('/alunos', json({
    nome: dados.nomeCompleto.trim(),
    cpf: cpfSemFormatacao(dados.cpf),
    email: dados.email.trim() || null,
    dataNascimento: dados.dataNascimento || null,
    turma: { idTurma: turmaId },
  }));

  return {
    ...dados,
    id: aluno.matricula,
    matricula: aluno.matricula,
    nomeCompleto: aluno.nome,
    cpf: aluno.cpf,
    email: aluno.email || '',
    dataNascimento: aluno.dataNascimento || '',
    turma: aluno.turma,
    turmaId: aluno.turma?.idTurma,
    status: 'Ativo',
  };
}

export async function criarProfessor(dados) {
  const professor = await apiFetch('/professores', json({
    nome: dados.nomeCompleto.trim(),
    cpf: cpfSemFormatacao(dados.cpf),
    email: dados.email.trim() || null,
    telefone: dados.telefone.trim() || null,
  }));

  return {
    ...dados,
    id: professor.idProfessor,
    matricula: professor.idProfessor,
    nomeCompleto: professor.nome,
    cpf: professor.cpf,
    email: professor.email || '',
    telefone: professor.telefone || '',
    status: 'Ativo',
  };
}

export async function criarResponsavel(dados) {
  const responsavel = await apiFetch('/responsaveis', json({
    nome: dados.nomeCompleto.trim(),
    cpf: cpfSemFormatacao(dados.cpf),
    email: dados.email.trim() || null,
    telefone: dados.telefone.trim() || null,
  }));

  return {
    ...dados,
    id: responsavel.idResponsavel,
    matricula: responsavel.idResponsavel,
    nomeCompleto: responsavel.nome,
    cpf: responsavel.cpf || '',
    email: responsavel.email || '',
    telefone: responsavel.telefone || '',
    status: 'Ativo',
  };
}

export async function criarFuncionario(dados) {
  const cargos = {
    'Secretário(a) escolar': { endpoint: '/secretarios', cargo: 'Secretário(a) escolar' },
    'Diretor(a)': { endpoint: '/diretores', cargo: 'Diretor(a)' },
    'Coordenador(a) pedagógico': { endpoint: '/coordenadores', cargo: 'Coordenador(a)' },
    'Coordenador(a) administrativo': { endpoint: '/coordenadores', cargo: 'Coordenador(a)' },
  };
  const configuracao = cargos[dados.cargo];

  if (!configuracao) {
    throw new Error('Esse cargo não possui uma rota de cadastro confirmada no backend.');
  }
  if (configuracao.endpoint === '/coordenadores' && !dados.email.trim()) {
    throw new Error('O e-mail é obrigatório para cadastrar um coordenador.');
  }

  const funcionario = await apiFetch(configuracao.endpoint, json({
    nome: dados.nomeCompleto.trim(),
    email: dados.email.trim() || null,
    telefone: dados.telefone.trim() || null,
  }));

  return {
    ...dados,
    id: `${configuracao.endpoint.slice(1)}-${funcionario.id}`,
    matricula: `${configuracao.endpoint.slice(1)}-${funcionario.id}`,
    nomeCompleto: funcionario.nome,
    email: funcionario.email || '',
    telefone: funcionario.telefone || '',
    cargo: configuracao.cargo,
    status: 'Ativo',
  };
}
