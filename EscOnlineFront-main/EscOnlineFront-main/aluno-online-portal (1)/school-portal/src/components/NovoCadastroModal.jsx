import React, { useMemo, useState } from 'react';
import { X, UserPlus, Users as UsersIcon, Heart, Briefcase, Check, ChevronLeft } from 'lucide-react';
import { CURSOS, ANOS, TURMAS } from '../data/SchoolData';

const ETNIAS = ['Branca', 'Preta', 'Parda', 'Amarela', 'Indígena', 'Prefere não informar'];
const SEXO_GENERO = ['Feminino', 'Masculino', 'Prefere não informar'];
const PARENTESCOS = ['Mãe', 'Pai', 'Avô/Avó', 'Tio/Tia', 'Irmão/Irmã', 'Responsável legal', 'Outro'];
const TURNOS = ['Manhã', 'Tarde', 'Noite'];
const GRAUS_FORMACAO = ['Licenciatura', 'Bacharelado', 'Especialização', 'Mestrado', 'Doutorado'];
const ESTADOS = ['CE', 'PE', 'PB', 'RN', 'PI', 'BA', 'MA', 'SP', 'RJ', 'MG', 'Outro'];
const CARGOS_FUNCIONARIO = ['Secretário(a) escolar', 'Diretor(a)', 'Vice-diretor(a)', 'Coordenador(a) pedagógico', 'Coordenador(a) administrativo', 'Porteiro(a)', 'Auxiliar de limpeza', 'Merendeiro(a)', 'Outro'];

function calcularIdade(dataNascimento) {
  if (!dataNascimento) return '';
  const nasc = new Date(dataNascimento);
  if (Number.isNaN(nasc.getTime())) return '';
  const hoje = new Date();
  let idade = hoje.getFullYear() - nasc.getFullYear();
  const aindaNaoFezAniversario =
    hoje.getMonth() < nasc.getMonth() || (hoje.getMonth() === nasc.getMonth() && hoje.getDate() < nasc.getDate());
  if (aindaNaoFezAniversario) idade -= 1;
  return idade >= 0 && idade < 120 ? idade : '';
}

const ALUNO_INICIAL = {
  nomeCompleto: '', nomeSocial: '', dataNascimento: '', email: '', telefone: '',
  cpf: '', rg: '', etnia: '', sexoGenero: '',
  endereco: '', numero: '', complemento: '', bairro: '', cidade: 'Pacatuba', estado: 'CE', cep: '',
  responsavelPrincipal: '', parentesco: 'Responsável legal', telefoneResponsavel: '', emailResponsavel: '',
  ano: '', cursoId: '', turmaId: '',
  dataMatricula: '', turno: 'Manhã', observacoes: '',
};

const PROFESSOR_INICIAL = {
  nomeCompleto: '', nomeSocial: '', dataNascimento: '', email: '', telefone: '',
  cpf: '', rg: '', etnia: '',
  endereco: '', numero: '', complemento: '', bairro: '', cidade: 'Pacatuba', estado: 'CE', cep: '',
  formacaoAcademica: '', instituicaoFormacao: '', grauFormacao: '',
  areaAtuacao: '', disciplinas: '', turmasIds: [], turno: 'Manhã',
  dataContratacao: '', observacoes: '',
};

const RESPONSAVEL_INICIAL = {
  nomeCompleto: '', cpf: '', telefone: '', email: '', parentesco: 'Mãe', alunoVinculado: '',
  endereco: '', numero: '', complemento: '', bairro: '', cidade: 'Pacatuba', estado: 'CE', cep: '', observacoes: '',
};

const FUNCIONARIO_INICIAL = {
  nomeCompleto: '', cpf: '', telefone: '', email: '', cargo: '', dataContratacao: '',
  endereco: '', numero: '', complemento: '', bairro: '', cidade: 'Pacatuba', estado: 'CE', cep: '', observacoes: '',
};

export default function NovoCadastroModal({ onClose, onSaveAluno, onSaveProfessor, onSaveResponsavel, onSaveFuncionario }) {
  const [tipo, setTipo] = useState(null);
  const [alunoForm, setAlunoForm] = useState(ALUNO_INICIAL);
  const [professorForm, setProfessorForm] = useState(PROFESSOR_INICIAL);
  const [responsavelForm, setResponsavelForm] = useState(RESPONSAVEL_INICIAL);
  const [funcionarioForm, setFuncionarioForm] = useState(FUNCIONARIO_INICIAL);
  const [errors, setErrors] = useState({});
  const [saved, setSaved] = useState(false);

  const idadeAluno = calcularIdade(alunoForm.dataNascimento);
  const idadeProfessor = calcularIdade(professorForm.dataNascimento);

  const turmasDisponiveisAluno = TURMAS.filter(
    (t) => (!alunoForm.ano || t.ano === alunoForm.ano) && (!alunoForm.cursoId || t.cursoId === alunoForm.cursoId)
  );

  const updateAluno = (patch) => setAlunoForm((f) => ({ ...f, ...patch }));
  const updateProfessor = (patch) => setProfessorForm((f) => ({ ...f, ...patch }));
  const updateResponsavel = (patch) => setResponsavelForm((f) => ({ ...f, ...patch }));
  const updateFuncionario = (patch) => setFuncionarioForm((f) => ({ ...f, ...patch }));

  const toggleTurmaProfessor = (turmaId) => {
    setProfessorForm((f) => ({
      ...f,
      turmasIds: f.turmasIds.includes(turmaId)
        ? f.turmasIds.filter((id) => id !== turmaId)
        : [...f.turmasIds, turmaId],
    }));
  };

  const cursosDoProfessor = useMemo(() => {
    const cursoIds = new Set(professorForm.turmasIds.map((id) => TURMAS.find((t) => t.id === id)?.cursoId));
    return CURSOS.filter((c) => cursoIds.has(c.id)).map((c) => c.nome);
  }, [professorForm.turmasIds]);

  const anosDoProfessor = useMemo(() => {
    const anos = new Set(professorForm.turmasIds.map((id) => TURMAS.find((t) => t.id === id)?.ano));
    return ANOS.filter((a) => anos.has(a));
  }, [professorForm.turmasIds]);

  const validateAluno = () => {
    const next = {};
    if (!alunoForm.nomeCompleto.trim()) next.nomeCompleto = 'Informe o nome completo do aluno.';
    if (!alunoForm.dataNascimento) next.dataNascimento = 'Informe a data de nascimento.';
    if (!alunoForm.cpf.trim()) next.cpf = 'Informe o CPF do aluno.';
    if (!alunoForm.ano) next.ano = 'Selecione o ano.';
    if (!alunoForm.cursoId) next.cursoId = 'Selecione o curso.';
    if (!alunoForm.turmaId) next.turmaId = 'Selecione a turma.';
    if (!alunoForm.responsavelPrincipal.trim()) next.responsavelPrincipal = 'Informe o responsável principal.';
    setErrors(next);
    return Object.keys(next).length === 0;
  };

  const validateProfessor = () => {
    const next = {};
    if (!professorForm.nomeCompleto.trim()) next.nomeCompleto = 'Informe o nome completo do professor.';
    if (!professorForm.dataNascimento) next.dataNascimento = 'Informe a data de nascimento.';
    if (!professorForm.cpf.trim()) next.cpf = 'Informe o CPF do professor.';
    if (!professorForm.areaAtuacao.trim()) next.areaAtuacao = 'Informe a área de atuação.';
    if (!professorForm.disciplinas.trim()) next.disciplinas = 'Informe ao menos uma disciplina.';
    if (professorForm.turmasIds.length === 0) next.turmasIds = 'Selecione ao menos uma turma.';
    setErrors(next);
    return Object.keys(next).length === 0;
  };

  const validateResponsavel = () => {
    const next = {};
    if (!responsavelForm.nomeCompleto.trim()) next.nomeCompleto = 'Informe o nome completo.';
    if (!responsavelForm.cpf.trim()) next.cpf = 'Informe o CPF.';
    if (!responsavelForm.alunoVinculado.trim()) next.alunoVinculado = 'Informe o nome do aluno vinculado.';
    setErrors(next);
    return Object.keys(next).length === 0;
  };

  const validateFuncionario = () => {
    const next = {};
    if (!funcionarioForm.nomeCompleto.trim()) next.nomeCompleto = 'Informe o nome completo.';
    if (!funcionarioForm.cpf.trim()) next.cpf = 'Informe o CPF.';
    if (!funcionarioForm.cargo.trim()) next.cargo = 'Informe o cargo.';
    setErrors(next);
    return Object.keys(next).length === 0;
  };

  const handleSave = () => {
    if (tipo === 'aluno') {
      if (!validateAluno()) return;
      onSaveAluno({ ...alunoForm });
    } else if (tipo === 'professor') {
      if (!validateProfessor()) return;
      onSaveProfessor({ ...professorForm });
    } else if (tipo === 'responsavel') {
      if (!validateResponsavel()) return;
      onSaveResponsavel({ ...responsavelForm });
    } else {
      if (!validateFuncionario()) return;
      onSaveFuncionario({ ...funcionarioForm });
    }
    setSaved(true);
    setTimeout(onClose, 800);
  };

  const handleClear = () => {
    setErrors({});
    if (tipo === 'aluno') setAlunoForm(ALUNO_INICIAL);
    else if (tipo === 'professor') setProfessorForm(PROFESSOR_INICIAL);
    else if (tipo === 'responsavel') setResponsavelForm(RESPONSAVEL_INICIAL);
    else setFuncionarioForm(FUNCIONARIO_INICIAL);
  };

  const titulo = {
    aluno: 'Cadastrar aluno', professor: 'Cadastrar professor', responsavel: 'Cadastrar responsável', funcionario: 'Cadastrar funcionário',
  }[tipo] ?? 'Novo cadastro';

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 p-4">
      <div className="flex max-h-[92vh] w-full max-w-3xl flex-col rounded-2xl bg-white shadow-xl">
        <div className="flex items-center justify-between border-b border-slate-100 px-6 py-4">
          <div className="flex items-center gap-2">
            {tipo && !saved && (
              <button
                onClick={() => { setTipo(null); setErrors({}); }}
                className="rounded-full p-1 text-slate-400 hover:bg-slate-100"
                aria-label="Voltar"
              >
                <ChevronLeft className="h-4 w-4" />
              </button>
            )}
            <div>
              <p className="text-[11px] font-semibold uppercase tracking-wide text-brand-mid">Cadastros</p>
              <h2 className="font-display text-lg font-semibold text-brand-navy">{titulo}</h2>
            </div>
          </div>
          <button onClick={onClose} className="rounded-full p-1.5 text-slate-400 hover:bg-slate-100" aria-label="Fechar">
            <X className="h-5 w-5" />
          </button>
        </div>

        {saved ? (
          <div className="flex flex-col items-center gap-3 px-6 py-14 text-center">
            <span className="flex h-12 w-12 items-center justify-center rounded-full bg-emerald-50 text-emerald-600">
              <Check className="h-6 w-6" />
            </span>
            <p className="text-sm font-semibold text-slate-800">
              {tipo === 'aluno' ? 'Aluno cadastrado com sucesso.' : tipo === 'professor' ? 'Professor cadastrado com sucesso.' : tipo === 'responsavel' ? 'Responsável cadastrado com sucesso.' : 'Funcionário cadastrado com sucesso.'}
            </p>
            <p className="text-xs text-slate-400">Já disponível na listagem de Cadastros.</p>
          </div>
        ) : !tipo ? (
          <div className="grid grid-cols-1 gap-3 px-6 py-8 sm:grid-cols-2">
            <button
              onClick={() => setTipo('aluno')}
              className="rounded-xl border border-slate-200 p-5 text-left transition-colors hover:border-brand-action hover:bg-brand-action/5"
            >
              <span className="flex h-10 w-10 items-center justify-center rounded-lg bg-brand-action/10 text-brand-action">
                <UserPlus className="h-5 w-5" />
              </span>
              <p className="mt-3 text-sm font-semibold text-slate-800">Aluno</p>
              <p className="mt-1 text-xs text-slate-500">Ficha completa de matrícula, dados pessoais e responsáveis.</p>
            </button>
            <button
              onClick={() => setTipo('professor')}
              className="rounded-xl border border-slate-200 p-5 text-left transition-colors hover:border-brand-action hover:bg-brand-action/5"
            >
              <span className="flex h-10 w-10 items-center justify-center rounded-lg bg-brand-action/10 text-brand-action">
                <UsersIcon className="h-5 w-5" />
              </span>
              <p className="mt-3 text-sm font-semibold text-slate-800">Professor</p>
              <p className="mt-1 text-xs text-slate-500">Ficha completa de formação, atuação e turmas.</p>
            </button>
            <button
              onClick={() => setTipo('responsavel')}
              className="rounded-xl border border-slate-200 p-5 text-left transition-colors hover:border-brand-action hover:bg-brand-action/5"
            >
              <span className="flex h-10 w-10 items-center justify-center rounded-lg bg-brand-action/10 text-brand-action"><Heart className="h-5 w-5" /></span>
              <p className="mt-3 text-sm font-semibold text-slate-800">Responsável</p>
              <p className="mt-1 text-xs text-slate-500">Dados de contato e vínculo com o aluno.</p>
            </button>
            <button
              onClick={() => setTipo('funcionario')}
              className="rounded-xl border border-slate-200 p-5 text-left transition-colors hover:border-brand-action hover:bg-brand-action/5"
            >
              <span className="flex h-10 w-10 items-center justify-center rounded-lg bg-brand-action/10 text-brand-action"><Briefcase className="h-5 w-5" /></span>
              <p className="mt-3 text-sm font-semibold text-slate-800">Funcionário</p>
              <p className="mt-1 text-xs text-slate-500">Cargo, contato e data de contratação.</p>
            </button>
          </div>
        ) : tipo === 'aluno' ? (
          <div className="flex-1 space-y-6 overflow-y-auto px-6 py-5">
            <FormSection title="Dados pessoais">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="Nome completo" error={errors.nomeCompleto} required span2>
                  <input type="text" value={alunoForm.nomeCompleto} onChange={(e) => updateAluno({ nomeCompleto: e.target.value })} className={inputCls(errors.nomeCompleto)} autoFocus />
                </Field>
                <Field label="Nome social"><input type="text" value={alunoForm.nomeSocial} onChange={(e) => updateAluno({ nomeSocial: e.target.value })} className="input" /></Field>
                <Field label="Data de nascimento" error={errors.dataNascimento} required>
                  <input type="date" value={alunoForm.dataNascimento} onChange={(e) => updateAluno({ dataNascimento: e.target.value })} className={inputCls(errors.dataNascimento)} />
                </Field>
                <Field label="Idade"><input type="text" value={idadeAluno} disabled className="input" placeholder="Calculada automaticamente" /></Field>
                <Field label="Sexo/gênero">
                  <select value={alunoForm.sexoGenero} onChange={(e) => updateAluno({ sexoGenero: e.target.value })} className="input">
                    <option value="">Selecione</option>
                    {SEXO_GENERO.map((s) => <option key={s} value={s}>{s}</option>)}
                  </select>
                </Field>
                <Field label="Etnia">
                  <select value={alunoForm.etnia} onChange={(e) => updateAluno({ etnia: e.target.value })} className="input">
                    <option value="">Selecione</option>
                    {ETNIAS.map((e2) => <option key={e2} value={e2}>{e2}</option>)}
                  </select>
                </Field>
                <Field label="CPF" error={errors.cpf} required><input type="text" value={alunoForm.cpf} onChange={(e) => updateAluno({ cpf: e.target.value })} placeholder="000.000.000-00" className={inputCls(errors.cpf)} /></Field>
                <Field label="RG"><input type="text" value={alunoForm.rg} onChange={(e) => updateAluno({ rg: e.target.value })} className="input" /></Field>
                <Field label="E-mail"><input type="email" value={alunoForm.email} onChange={(e) => updateAluno({ email: e.target.value })} className="input" /></Field>
                <Field label="Telefone"><input type="text" value={alunoForm.telefone} onChange={(e) => updateAluno({ telefone: e.target.value })} placeholder="(85) 90000-0000" className="input" /></Field>
              </div>
            </FormSection>

            <FormSection title="Endereço">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="CEP"><input type="text" value={alunoForm.cep} onChange={(e) => updateAluno({ cep: e.target.value })} className="input" /></Field>
                <Field label="Estado">
                  <select value={alunoForm.estado} onChange={(e) => updateAluno({ estado: e.target.value })} className="input">
                    {ESTADOS.map((s) => <option key={s} value={s}>{s}</option>)}
                  </select>
                </Field>
                <Field label="Cidade"><input type="text" value={alunoForm.cidade} onChange={(e) => updateAluno({ cidade: e.target.value })} className="input" /></Field>
                <Field label="Bairro"><input type="text" value={alunoForm.bairro} onChange={(e) => updateAluno({ bairro: e.target.value })} className="input" /></Field>
                <Field label="Rua/Avenida" span2><input type="text" value={alunoForm.endereco} onChange={(e) => updateAluno({ endereco: e.target.value })} className="input" /></Field>
                <Field label="Número"><input type="text" value={alunoForm.numero} onChange={(e) => updateAluno({ numero: e.target.value })} className="input" /></Field>
                <Field label="Complemento"><input type="text" value={alunoForm.complemento} onChange={(e) => updateAluno({ complemento: e.target.value })} className="input" /></Field>
              </div>
            </FormSection>

            <FormSection title="Responsável">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="Nome do responsável principal" error={errors.responsavelPrincipal} required span2>
                  <input type="text" value={alunoForm.responsavelPrincipal} onChange={(e) => updateAluno({ responsavelPrincipal: e.target.value })} className={inputCls(errors.responsavelPrincipal)} />
                </Field>
                <Field label="Parentesco">
                  <select value={alunoForm.parentesco} onChange={(e) => updateAluno({ parentesco: e.target.value })} className="input">
                    {PARENTESCOS.map((p) => <option key={p} value={p}>{p}</option>)}
                  </select>
                </Field>
                <Field label="Telefone do responsável"><input type="text" value={alunoForm.telefoneResponsavel} onChange={(e) => updateAluno({ telefoneResponsavel: e.target.value })} className="input" /></Field>
                <Field label="E-mail do responsável" span2><input type="email" value={alunoForm.emailResponsavel} onChange={(e) => updateAluno({ emailResponsavel: e.target.value })} className="input" /></Field>
              </div>
            </FormSection>

            <FormSection title="Matrícula">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
                <Field label="Ano" error={errors.ano} required>
                  <select value={alunoForm.ano} onChange={(e) => updateAluno({ ano: e.target.value, turmaId: '' })} className={inputCls(errors.ano)}>
                    <option value="">Selecione</option>
                    {ANOS.map((a) => <option key={a} value={a}>{a}</option>)}
                  </select>
                </Field>
                <Field label="Curso" error={errors.cursoId} required>
                  <select value={alunoForm.cursoId} onChange={(e) => updateAluno({ cursoId: e.target.value, turmaId: '' })} className={inputCls(errors.cursoId)}>
                    <option value="">Selecione</option>
                    {CURSOS.map((c) => <option key={c.id} value={c.id}>{c.nome}</option>)}
                  </select>
                </Field>
                <Field label="Turma" error={errors.turmaId} required>
                  <select value={alunoForm.turmaId} onChange={(e) => updateAluno({ turmaId: e.target.value })} disabled={!alunoForm.ano || !alunoForm.cursoId} className={inputCls(errors.turmaId)}>
                    <option value="">Selecione</option>
                    {turmasDisponiveisAluno.map((t) => <option key={t.id} value={t.id}>{t.nome}</option>)}
                  </select>
                </Field>
                <Field label="Data de matrícula"><input type="date" value={alunoForm.dataMatricula} onChange={(e) => updateAluno({ dataMatricula: e.target.value })} className="input" /></Field>
                <Field label="Turno">
                  <select value={alunoForm.turno} onChange={(e) => updateAluno({ turno: e.target.value })} className="input">
                    {TURNOS.map((t) => <option key={t} value={t}>{t}</option>)}
                  </select>
                </Field>
              </div>
              <Field label="Observações">
                <textarea value={alunoForm.observacoes} onChange={(e) => updateAluno({ observacoes: e.target.value })} rows={2} className="input resize-none" placeholder="Informações adicionais relevantes..." />
              </Field>
            </FormSection>
          </div>
        ) : tipo === 'professor' ? (
          <div className="flex-1 space-y-6 overflow-y-auto px-6 py-5">
            <FormSection title="Dados pessoais">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="Nome completo" error={errors.nomeCompleto} required span2>
                  <input type="text" value={professorForm.nomeCompleto} onChange={(e) => updateProfessor({ nomeCompleto: e.target.value })} className={inputCls(errors.nomeCompleto)} autoFocus />
                </Field>
                <Field label="Nome social"><input type="text" value={professorForm.nomeSocial} onChange={(e) => updateProfessor({ nomeSocial: e.target.value })} className="input" /></Field>
                <Field label="Data de nascimento" error={errors.dataNascimento} required>
                  <input type="date" value={professorForm.dataNascimento} onChange={(e) => updateProfessor({ dataNascimento: e.target.value })} className={inputCls(errors.dataNascimento)} />
                </Field>
                <Field label="Idade"><input type="text" value={idadeProfessor} disabled className="input" placeholder="Calculada automaticamente" /></Field>
                <Field label="Etnia">
                  <select value={professorForm.etnia} onChange={(e) => updateProfessor({ etnia: e.target.value })} className="input">
                    <option value="">Selecione</option>
                    {ETNIAS.map((e2) => <option key={e2} value={e2}>{e2}</option>)}
                  </select>
                </Field>
                <Field label="CPF" error={errors.cpf} required><input type="text" value={professorForm.cpf} onChange={(e) => updateProfessor({ cpf: e.target.value })} placeholder="000.000.000-00" className={inputCls(errors.cpf)} /></Field>
                <Field label="RG"><input type="text" value={professorForm.rg} onChange={(e) => updateProfessor({ rg: e.target.value })} className="input" /></Field>
                <Field label="E-mail"><input type="email" value={professorForm.email} onChange={(e) => updateProfessor({ email: e.target.value })} className="input" /></Field>
                <Field label="Telefone"><input type="text" value={professorForm.telefone} onChange={(e) => updateProfessor({ telefone: e.target.value })} placeholder="(85) 90000-0000" className="input" /></Field>
              </div>
            </FormSection>

            <FormSection title="Endereço">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="CEP"><input type="text" value={professorForm.cep} onChange={(e) => updateProfessor({ cep: e.target.value })} className="input" /></Field>
                <Field label="Estado">
                  <select value={professorForm.estado} onChange={(e) => updateProfessor({ estado: e.target.value })} className="input">
                    {ESTADOS.map((s) => <option key={s} value={s}>{s}</option>)}
                  </select>
                </Field>
                <Field label="Cidade"><input type="text" value={professorForm.cidade} onChange={(e) => updateProfessor({ cidade: e.target.value })} className="input" /></Field>
                <Field label="Bairro"><input type="text" value={professorForm.bairro} onChange={(e) => updateProfessor({ bairro: e.target.value })} className="input" /></Field>
                <Field label="Rua/Avenida" span2><input type="text" value={professorForm.endereco} onChange={(e) => updateProfessor({ endereco: e.target.value })} className="input" /></Field>
                <Field label="Número"><input type="text" value={professorForm.numero} onChange={(e) => updateProfessor({ numero: e.target.value })} className="input" /></Field>
                <Field label="Complemento"><input type="text" value={professorForm.complemento} onChange={(e) => updateProfessor({ complemento: e.target.value })} className="input" /></Field>
              </div>
            </FormSection>

            <FormSection title="Formação acadêmica">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="Formação acadêmica"><input type="text" value={professorForm.formacaoAcademica} onChange={(e) => updateProfessor({ formacaoAcademica: e.target.value })} placeholder="Ex.: Licenciatura em Matemática" className="input" /></Field>
                <Field label="Instituição de formação"><input type="text" value={professorForm.instituicaoFormacao} onChange={(e) => updateProfessor({ instituicaoFormacao: e.target.value })} className="input" /></Field>
                <Field label="Grau de formação">
                  <select value={professorForm.grauFormacao} onChange={(e) => updateProfessor({ grauFormacao: e.target.value })} className="input">
                    <option value="">Selecione</option>
                    {GRAUS_FORMACAO.map((g) => <option key={g} value={g}>{g}</option>)}
                  </select>
                </Field>
                <Field label="Data de contratação"><input type="date" value={professorForm.dataContratacao} onChange={(e) => updateProfessor({ dataContratacao: e.target.value })} className="input" /></Field>
              </div>
            </FormSection>

            <FormSection title="Atuação">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="Área de atuação" error={errors.areaAtuacao} required span2>
                  <input type="text" value={professorForm.areaAtuacao} onChange={(e) => updateProfessor({ areaAtuacao: e.target.value })} placeholder="Ex.: Curso técnico · Informática" className={inputCls(errors.areaAtuacao)} />
                </Field>
                <Field label="Disciplinas que leciona" error={errors.disciplinas} required span2>
                  <input type="text" value={professorForm.disciplinas} onChange={(e) => updateProfessor({ disciplinas: e.target.value })} placeholder="Separe por vírgula" className={inputCls(errors.disciplinas)} />
                </Field>
                <Field label="Turno">
                  <select value={professorForm.turno} onChange={(e) => updateProfessor({ turno: e.target.value })} className="input">
                    {TURNOS.map((t) => <option key={t} value={t}>{t}</option>)}
                  </select>
                </Field>
              </div>

              <div>
                <p className="mb-1.5 text-xs font-semibold text-slate-500">
                  Turmas em que atua {errors.turmasIds && <span className="text-rose-500">— {errors.turmasIds}</span>}
                </p>
                <div className="grid grid-cols-2 gap-1.5 sm:grid-cols-4">
                  {TURMAS.map((t) => (
                    <label key={t.id} className={`flex items-center gap-1.5 rounded-lg border p-2 text-xs ${professorForm.turmasIds.includes(t.id) ? 'border-brand-action bg-brand-action/5' : 'border-slate-200'}`}>
                      <input type="checkbox" checked={professorForm.turmasIds.includes(t.id)} onChange={() => toggleTurmaProfessor(t.id)} className="h-3.5 w-3.5 rounded border-slate-300 text-brand-action focus:ring-brand-action" />
                      {t.nome}
                    </label>
                  ))}
                </div>
              </div>

              {(cursosDoProfessor.length > 0 || anosDoProfessor.length > 0) && (
                <div className="flex flex-wrap gap-1.5 rounded-lg bg-surface-muted p-3 text-xs text-slate-500">
                  {cursosDoProfessor.length > 0 && <span>Cursos: <strong className="text-slate-700">{cursosDoProfessor.join(', ')}</strong></span>}
                  {anosDoProfessor.length > 0 && <span className="ml-3">Anos: <strong className="text-slate-700">{anosDoProfessor.join(', ')}</strong></span>}
                </div>
              )}

              <Field label="Observações">
                <textarea value={professorForm.observacoes} onChange={(e) => updateProfessor({ observacoes: e.target.value })} rows={2} className="input resize-none" placeholder="Informações adicionais relevantes..." />
              </Field>
            </FormSection>
          </div>
        ) : tipo === 'responsavel' ? (
          <div className="flex-1 space-y-6 overflow-y-auto px-6 py-5">
            <FormSection title="Dados do responsável">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="Nome completo" error={errors.nomeCompleto} required span2>
                  <input type="text" value={responsavelForm.nomeCompleto} onChange={(e) => updateResponsavel({ nomeCompleto: e.target.value })} className={inputCls(errors.nomeCompleto)} autoFocus />
                </Field>
                <Field label="CPF" error={errors.cpf} required><input type="text" value={responsavelForm.cpf} onChange={(e) => updateResponsavel({ cpf: e.target.value })} className={inputCls(errors.cpf)} /></Field>
                <Field label="Telefone"><input type="text" value={responsavelForm.telefone} onChange={(e) => updateResponsavel({ telefone: e.target.value })} className="input" /></Field>
                <Field label="E-mail" span2><input type="email" value={responsavelForm.email} onChange={(e) => updateResponsavel({ email: e.target.value })} className="input" /></Field>
              </div>
            </FormSection>
            <FormSection title="Vínculo">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="Parentesco"><select value={responsavelForm.parentesco} onChange={(e) => updateResponsavel({ parentesco: e.target.value })} className="input">{PARENTESCOS.map((p) => <option key={p} value={p}>{p}</option>)}</select></Field>
                <Field label="Nome do aluno vinculado" error={errors.alunoVinculado} required><input type="text" value={responsavelForm.alunoVinculado} onChange={(e) => updateResponsavel({ alunoVinculado: e.target.value })} className={inputCls(errors.alunoVinculado)} /></Field>
              </div>
            </FormSection>
            <FormSection title="Endereço">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="CEP"><input type="text" value={responsavelForm.cep} onChange={(e) => updateResponsavel({ cep: e.target.value })} className="input" /></Field>
                <Field label="Estado"><select value={responsavelForm.estado} onChange={(e) => updateResponsavel({ estado: e.target.value })} className="input">{ESTADOS.map((s) => <option key={s} value={s}>{s}</option>)}</select></Field>
                <Field label="Cidade"><input type="text" value={responsavelForm.cidade} onChange={(e) => updateResponsavel({ cidade: e.target.value })} className="input" /></Field>
                <Field label="Endereço"><input type="text" value={responsavelForm.endereco} onChange={(e) => updateResponsavel({ endereco: e.target.value })} className="input" /></Field>
                <Field label="Número"><input type="text" value={responsavelForm.numero} onChange={(e) => updateResponsavel({ numero: e.target.value })} className="input" /></Field>
              </div>
              <Field label="Observações"><textarea value={responsavelForm.observacoes} onChange={(e) => updateResponsavel({ observacoes: e.target.value })} rows={2} className="input resize-none" /></Field>
            </FormSection>
          </div>
        ) : (
          <div className="flex-1 space-y-6 overflow-y-auto px-6 py-5">
            <FormSection title="Dados do funcionário">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="Nome completo" error={errors.nomeCompleto} required span2>
                  <input type="text" value={funcionarioForm.nomeCompleto} onChange={(e) => updateFuncionario({ nomeCompleto: e.target.value })} className={inputCls(errors.nomeCompleto)} autoFocus />
                </Field>
                <Field label="CPF" error={errors.cpf} required><input type="text" value={funcionarioForm.cpf} onChange={(e) => updateFuncionario({ cpf: e.target.value })} className={inputCls(errors.cpf)} /></Field>
                <Field label="Telefone"><input type="text" value={funcionarioForm.telefone} onChange={(e) => updateFuncionario({ telefone: e.target.value })} className="input" /></Field>
                <Field label="E-mail"><input type="email" value={funcionarioForm.email} onChange={(e) => updateFuncionario({ email: e.target.value })} className="input" /></Field>
                <Field label="Cargo" error={errors.cargo} required><select value={funcionarioForm.cargo} onChange={(e) => updateFuncionario({ cargo: e.target.value })} className={inputCls(errors.cargo)}><option value="">Selecione</option>{CARGOS_FUNCIONARIO.map((cargo) => <option key={cargo} value={cargo}>{cargo}</option>)}</select></Field>
                <Field label="Data de contratação"><input type="date" value={funcionarioForm.dataContratacao} onChange={(e) => updateFuncionario({ dataContratacao: e.target.value })} className="input" /></Field>
              </div>
            </FormSection>
            <FormSection title="Endereço">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <Field label="CEP"><input type="text" value={funcionarioForm.cep} onChange={(e) => updateFuncionario({ cep: e.target.value })} className="input" /></Field>
                <Field label="Estado"><select value={funcionarioForm.estado} onChange={(e) => updateFuncionario({ estado: e.target.value })} className="input">{ESTADOS.map((s) => <option key={s} value={s}>{s}</option>)}</select></Field>
                <Field label="Cidade"><input type="text" value={funcionarioForm.cidade} onChange={(e) => updateFuncionario({ cidade: e.target.value })} className="input" /></Field>
                <Field label="Endereço"><input type="text" value={funcionarioForm.endereco} onChange={(e) => updateFuncionario({ endereco: e.target.value })} className="input" /></Field>
              </div>
              <Field label="Observações"><textarea value={funcionarioForm.observacoes} onChange={(e) => updateFuncionario({ observacoes: e.target.value })} rows={2} className="input resize-none" /></Field>
            </FormSection>
          </div>
        )}

        {tipo && !saved && (
          <div className="flex items-center justify-between border-t border-slate-100 px-6 py-4">
            <button onClick={handleClear} className="text-sm font-semibold text-slate-400 hover:text-slate-600">
              Limpar dados
            </button>
            <div className="flex gap-2">
              <button onClick={onClose} className="rounded-lg border border-slate-200 px-4 py-2.5 text-sm font-semibold text-slate-600 hover:bg-surface-muted">
                Cancelar
              </button>
              <button onClick={handleSave} className="rounded-lg bg-brand-action px-4 py-2.5 text-sm font-semibold text-white hover:bg-blue-600">
                Salvar cadastro
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

function inputCls(error) {
  return `input ${error ? 'border-rose-300' : ''}`;
}

function FormSection({ title, children }) {
  return (
    <div>
      <p className="mb-3 text-[11px] font-semibold uppercase tracking-wide text-brand-mid">{title}</p>
      <div className="space-y-4">{children}</div>
    </div>
  );
}

function Field({ label, error, required, span2, children }) {
  return (
    <label className={`block ${span2 ? 'sm:col-span-2' : ''}`}>
      <span className="mb-1.5 block text-xs font-semibold text-slate-500">
        {label}
        {required && <span className="text-rose-500"> *</span>}
      </span>
      {children}
      {error && <span className="mt-1 block text-xs font-medium text-rose-500">{error}</span>}
    </label>
  );
}