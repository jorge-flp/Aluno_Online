import React from 'react';
import { X } from 'lucide-react';
import { getCurso, getTurma } from '../data/SchoolData';

const LABELS_ALUNO = {
  nomeCompleto: 'Nome completo', nomeSocial: 'Nome social', dataNascimento: 'Data de nascimento',
  sexoGenero: 'Sexo/gênero', etnia: 'Etnia', cpf: 'CPF', rg: 'RG', email: 'E-mail', telefone: 'Telefone',
  endereco: 'Rua/Avenida', numero: 'Número', complemento: 'Complemento', bairro: 'Bairro',
  cidade: 'Cidade', estado: 'Estado', cep: 'CEP',
  responsavelPrincipal: 'Responsável principal', parentesco: 'Parentesco',
  telefoneResponsavel: 'Telefone do responsável', emailResponsavel: 'E-mail do responsável',
  matricula: 'Matrícula', dataMatricula: 'Data de matrícula', turno: 'Turno', observacoes: 'Observações',
};

const LABELS_PROFESSOR = {
  nomeCompleto: 'Nome completo', nomeSocial: 'Nome social', dataNascimento: 'Data de nascimento',
  etnia: 'Etnia', cpf: 'CPF', rg: 'RG', email: 'E-mail', telefone: 'Telefone',
  endereco: 'Rua/Avenida', numero: 'Número', complemento: 'Complemento', bairro: 'Bairro',
  cidade: 'Cidade', estado: 'Estado', cep: 'CEP',
  formacaoAcademica: 'Formação acadêmica', instituicaoFormacao: 'Instituição de formação',
  grauFormacao: 'Grau de formação', areaAtuacao: 'Área de atuação', disciplinas: 'Disciplinas que leciona',
  turno: 'Turno', dataContratacao: 'Data de contratação', matricula: 'Registro', observacoes: 'Observações',
};

export default function ViewFichaModal({ tipo, registro, onClose }) {
  if (!registro) return null;
  const isAluno = tipo === 'aluno';
  const labels = isAluno ? LABELS_ALUNO : LABELS_PROFESSOR;
  const curso = isAluno ? getCurso(registro.cursoId) : null;
  const turma = isAluno ? getTurma(registro.turmaId) : null;
  const turmasProfessor = !isAluno ? (registro.turmasIds ?? []).map((id) => getTurma(id)?.nome).filter(Boolean) : [];

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 p-4">
      <div className="flex max-h-[90vh] w-full max-w-2xl flex-col rounded-2xl bg-white shadow-xl">
        <div className="flex items-center justify-between border-b border-slate-100 px-6 py-4">
          <div>
            <p className="text-[11px] font-semibold uppercase tracking-wide text-brand-mid">
              Ficha de {isAluno ? 'aluno' : 'professor'}
            </p>
            <h2 className="font-display text-lg font-semibold text-brand-navy">{registro.nomeCompleto}</h2>
          </div>
          <button onClick={onClose} className="rounded-full p-1.5 text-slate-400 hover:bg-slate-100" aria-label="Fechar">
            <X className="h-5 w-5" />
          </button>
        </div>

        <div className="flex-1 overflow-y-auto px-6 py-5">
          {isAluno && (
            <div className="mb-5 flex flex-wrap gap-1.5">
              {curso && <span className="rounded-full bg-brand-action/10 px-2.5 py-1 text-xs font-medium text-brand-action">{curso.nome}</span>}
              {turma && <span className="rounded-full bg-brand-action/10 px-2.5 py-1 text-xs font-medium text-brand-action">{turma.nome}</span>}
            </div>
          )}
          {!isAluno && turmasProfessor.length > 0 && (
            <div className="mb-5 flex flex-wrap gap-1.5">
              {turmasProfessor.map((nome) => (
                <span key={nome} className="rounded-full bg-brand-action/10 px-2.5 py-1 text-xs font-medium text-brand-action">{nome}</span>
              ))}
            </div>
          )}

          <div className="grid grid-cols-1 gap-x-6 gap-y-4 sm:grid-cols-2">
            {Object.entries(labels).map(([key, label]) => {
              const value = registro[key];
              if (!value) return null;
              return (
                <div key={key}>
                  <p className="text-xs font-semibold text-slate-500">{label}</p>
                  <p className="mt-0.5 text-sm text-slate-800">{value}</p>
                </div>
              );
            })}
          </div>
        </div>

        <div className="flex justify-end border-t border-slate-100 px-6 py-4">
          <button onClick={onClose} className="rounded-lg border border-slate-200 px-4 py-2.5 text-sm font-semibold text-slate-600 hover:bg-surface-muted">
            Fechar
          </button>
        </div>
      </div>
    </div>
  );
}