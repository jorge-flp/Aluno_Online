import React, { useState } from 'react';
import { Plus, ArrowUpRight } from 'lucide-react';
import PageHeader from '../../components/PageHeader';
import StatusBadge from '../../components/StatusBadge';
import FooterStats from '../../components/FooterStats';
import { ANOS, TURMAS, getCurso, getAlunosDaTurma } from '../../data/SchoolData';
import { DEFAULT_FOOTER_STATS } from '../../data/mockData';

export default function Turmas() {
  const [anoFiltro, setAnoFiltro] = useState('Todos');
  const turmasFiltradas = anoFiltro === 'Todos' ? TURMAS : TURMAS.filter((t) => t.ano === anoFiltro);

  return (
    <>
      <PageHeader
        eyebrow="Ensino Médio / Turmas"
        title="Turmas"
        description="3 anos · 4 cursos técnicos · 12 turmas. Veja composição, curso e alunos de cada turma."
        actionLabel="Adicionar Item"
        actionIcon={Plus}
        onAction={() => alert('Ação "Adicionar Item" ainda não implementada neste protótipo.')}
      />

      <div className="mb-6 inline-flex flex-wrap gap-1.5 rounded-lg bg-surface-muted p-1">
        {['Todos', ...ANOS].map((a) => (
          <button
            key={a}
            onClick={() => setAnoFiltro(a)}
            className={`rounded-md px-3.5 py-2 text-sm font-medium transition-colors ${
              anoFiltro === a ? 'bg-white text-brand-navy shadow-card' : 'text-slate-500 hover:text-slate-700'
            }`}
          >
            {a}
          </button>
        ))}
      </div>

      <section className="rounded-2xl border border-slate-200 bg-surface-card p-5 shadow-card sm:p-6">
        <div className="mb-5">
          <p className="text-[11px] font-semibold uppercase tracking-wide text-brand-mid">Visão geral</p>
          <h2 className="mt-1 font-display text-xl font-semibold text-brand-navy">
            Turmas cadastradas · {turmasFiltradas.length}
          </h2>
        </div>

        <div className="hidden overflow-x-auto md:block">
          <table className="w-full min-w-[640px] border-collapse text-sm">
            <thead>
              <tr className="border-b border-slate-100 text-left text-[11px] font-semibold uppercase tracking-wide text-slate-400">
                <th className="pb-3 pr-4">Turma</th>
                <th className="pb-3 pr-4">Ano · Curso</th>
                <th className="pb-3 pr-4">Professor(es)</th>
                <th className="pb-3 pr-4">Alunos</th>
                <th className="pb-3 pr-4">Status</th>
                <th className="pb-3">Ação</th>
              </tr>
            </thead>
            <tbody>
              {turmasFiltradas.map((t) => {
                const curso = getCurso(t.cursoId);
                const alunos = getAlunosDaTurma(t.id);
                return (
                  <tr key={t.id} className="border-b border-slate-50 last:border-0 hover:bg-slate-50/60">
                    <td className="py-3.5 pr-4">
                      <p className="font-medium text-slate-800">{t.nome}</p>
                      <p className="text-xs text-slate-400">Atualizado há 2 horas</p>
                    </td>
                    <td className="py-3.5 pr-4 text-slate-500">
                      {t.ano} · {curso?.nome}
                    </td>
                    <td className="py-3.5 pr-4 text-slate-500">{t.professores.join(', ')}</td>
                    <td className="py-3.5 pr-4 text-slate-500">{alunos.length} alunos</td>
                    <td className="py-3.5 pr-4">
                      <StatusBadge status={t.status} />
                    </td>
                    <td className="py-3.5">
                      <button
                        onClick={() => alert(`Detalhes de ${t.nome} — em breve.`)}
                        className="inline-flex items-center gap-1 text-sm font-semibold text-brand-action hover:underline"
                      >
                        Abrir
                        <ArrowUpRight className="h-3.5 w-3.5" />
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>

        <ul className="space-y-3 md:hidden">
          {turmasFiltradas.map((t) => {
            const curso = getCurso(t.cursoId);
            const alunos = getAlunosDaTurma(t.id);
            return (
              <li key={t.id} className="rounded-xl border border-slate-100 p-4">
                <div className="mb-2 flex items-start justify-between gap-2">
                  <p className="font-medium text-slate-800">{t.nome}</p>
                  <StatusBadge status={t.status} />
                </div>
                <p className="text-xs text-slate-500">{t.ano} · {curso?.nome}</p>
                <p className="text-xs text-slate-500">{t.professores.join(', ')} · {alunos.length} alunos</p>
                <button
                  onClick={() => alert(`Detalhes de ${t.nome} — em breve.`)}
                  className="mt-3 inline-flex items-center gap-1 text-sm font-semibold text-brand-action"
                >
                  Abrir
                  <ArrowUpRight className="h-3.5 w-3.5" />
                </button>
              </li>
            );
          })}
        </ul>
      </section>

      <FooterStats items={DEFAULT_FOOTER_STATS} />
    </>
  );
}