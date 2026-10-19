import React from 'react';
import {
  Plus, FileBarChart, GraduationCap, UserCheck, Activity, BookOpen, ArrowUpRight,
  ChevronRight, CalendarDays, ClipboardList, Megaphone,
} from 'lucide-react';
import { eventosEscola, matriculasEscola, noticiasEscola } from '../../data/mockData';

const STATS = [
  { icon: GraduationCap, label: 'Alunos matriculados', value: '384', delta: '↗ 4,2%', caption: 'vs. mesmo período de 2025' },
  { icon: UserCheck, label: 'Professores ativos', value: '34', delta: '↗ 2,4%', caption: '73% em sala neste momento' },
  { icon: Activity, label: 'Frequência média', value: '93,6%', delta: '↗ 1,8%', caption: 'acima da meta de 92%' },
  { icon: BookOpen, label: 'Desempenho médio', value: '7,8', delta: '↗ 0,3', caption: 'média geral · 2º bimestre' },
];

const MONTH_NAMES = ['jan', 'fev', 'mar', 'abr', 'mai', 'jun', 'jul', 'ago', 'set', 'out', 'nov', 'dez'];

const PRESENCE_BY_STAGE = [
  { stage: '1º ano', value: 94.4 },
  { stage: '2º ano', value: 93.1 },
  { stage: '3º ano', value: 92.8 },
];

export default function Home({ onNavigate }) {
  const proximosEventos = eventosEscola.slice(0, 3);
  const matriculasPendentes = matriculasEscola.filter((m) => m.status === 'Pendente' || m.status === 'Em análise');
  const comunicadosRecentes = noticiasEscola.filter((n) => n.status === 'Publicado').slice(0, 3);

  return (
    <>
      <div className="relative mb-6 overflow-hidden rounded-2xl bg-gradient-to-br from-[#7c4d00] via-[#d4a21b] to-[#8a5d00] p-6 text-white sm:mb-8 sm:p-10">
        <div
          className="pointer-events-none absolute inset-0 opacity-25 mix-blend-overlay"
          style={{
            backgroundImage:
              'radial-gradient(circle at 15% 25%, rgba(255,255,255,0.35), transparent 40%), radial-gradient(circle at 85% 75%, rgba(120,82,9,0.45), transparent 45%)',
          }}
        />
        <div className="relative flex flex-col gap-8 lg:flex-row lg:items-end lg:justify-between">
          <div className="max-w-xl">
            <span className="inline-flex items-center gap-2 rounded-full bg-white/10 px-3 py-1 text-[11px] font-semibold uppercase tracking-wide text-indigo-100">
              <span className="h-1.5 w-1.5 rounded-full bg-brand-action" />
              Quarta-feira, 26 de agosto de 2026
            </span>
            <h1 className="mt-4 font-display text-3xl font-semibold leading-tight sm:text-4xl lg:text-[42px]">
              A escola em <br />
              <span className="italic font-normal text-indigo-100">um só quadro.</span>
            </h1>
            <p className="mt-4 text-sm text-indigo-100/90 sm:text-base">
              Bom dia, equipe. Aqui está o pulso da Escola Estadual Professora Lúcia de Almeida para você acompanhar
              o que importa.
            </p>
            <div className="mt-6 flex flex-wrap gap-3">
              <button
                onClick={() => onNavigate('matriculas')}
                className="inline-flex items-center gap-2 rounded-lg bg-amber-500 px-4 py-2.5 text-sm font-semibold text-white shadow-card transition-colors hover:bg-amber-600"
              >
                <Plus className="h-4 w-4" />
                Nova matrícula
              </button>
              <button
                onClick={() => onNavigate('relatorios')}
                className="inline-flex items-center gap-2 rounded-lg border border-white/30 px-4 py-2.5 text-sm font-semibold text-white transition-colors hover:bg-white/10"
              >
                <FileBarChart className="h-4 w-4" />
                Ver indicadores
              </button>
            </div>
          </div>

          <div className="text-left lg:text-right">
            <p className="text-[11px] font-semibold uppercase tracking-wide text-indigo-300">Ano letivo</p>
            <p className="font-display text-4xl font-bold sm:text-5xl">2026</p>
            <p className="text-xs text-indigo-200">2º bimestre · 58% concluído</p>
          </div>
        </div>
      </div>

      <div className="mb-3 flex items-center justify-between">
        <div>
          <p className="text-[11px] font-semibold uppercase tracking-wide text-slate-500">Leitura rápida</p>
          <h2 className="mt-1 font-display text-xl font-semibold text-slate-900">Pulso da escola</h2>
        </div>
        <button onClick={() => onNavigate('relatorios')} className="inline-flex items-center gap-1 text-sm font-semibold text-amber-700 hover:underline">
          Ver todos os indicadores
          <ArrowUpRight className="h-3.5 w-3.5" />
        </button>
      </div>

      <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-4">
        {STATS.map(({ icon: Icon, label, value, delta, caption }) => (
          <div key={label} className="rounded-2xl border border-slate-200 bg-surface-card p-5 shadow-card">
            <div className="mb-6 flex items-center justify-between">
              <span className="text-[11px] font-semibold uppercase tracking-wide text-slate-400">{label}</span>
              <span className="flex h-8 w-8 items-center justify-center rounded-lg bg-amber-100 text-amber-700">
                <Icon className="h-4 w-4" />
              </span>
            </div>
            <div className="flex items-baseline gap-2">
              <span className="font-display text-3xl font-bold text-slate-900">{value}</span>
              <span className="text-xs font-semibold text-emerald-600">{delta}</span>
            </div>
            <p className="mt-1 text-xs text-slate-400">{caption}</p>
          </div>
        ))}
      </div>

      <div className="mt-5 grid grid-cols-1 gap-5 lg:grid-cols-3">
        <button
          onClick={() => onNavigate('matriculas')}
          className="rounded-2xl border border-slate-200 bg-surface-card p-5 shadow-card transition-shadow hover:shadow-md text-left"
        >
          <div className="mb-3 flex items-center justify-between">
            <span className="flex h-9 w-9 items-center justify-center rounded-lg bg-amber-50 text-amber-600">
              <ClipboardList className="h-4 w-4" />
            </span>
            <ArrowUpRight className="h-4 w-4 text-slate-300" />
          </div>
          <p className="font-display text-2xl font-bold text-slate-900">{matriculasPendentes.length}</p>
          <p className="text-sm text-slate-500">matrículas pendentes de documentação</p>
        </button>

        <button
          onClick={() => onNavigate('eventos')}
          className="rounded-2xl border border-slate-200 bg-surface-card p-5 shadow-card transition-shadow hover:shadow-md text-left"
        >
          <div className="mb-3 flex items-center justify-between">
            <span className="flex h-9 w-9 items-center justify-center rounded-lg bg-brand-action/10 text-brand-action">
              <CalendarDays className="h-4 w-4" />
            </span>
            <ArrowUpRight className="h-4 w-4 text-slate-300" />
          </div>
          <p className="font-display text-2xl font-bold text-brand-navy">{proximosEventos.length}</p>
          <p className="text-sm text-slate-500">eventos institucionais nos próximos dias</p>
        </button>

        <button
          onClick={() => onNavigate('noticias')}
          className="rounded-2xl border border-slate-200 bg-surface-card p-5 shadow-card transition-shadow hover:shadow-md text-left"
        >
          <div className="mb-3 flex items-center justify-between">
            <span className="flex h-9 w-9 items-center justify-center rounded-lg bg-emerald-50 text-emerald-600">
              <Megaphone className="h-4 w-4" />
            </span>
            <ArrowUpRight className="h-4 w-4 text-slate-300" />
          </div>
          <p className="font-display text-2xl font-bold text-slate-900">{comunicadosRecentes.length}</p>
          <p className="text-sm text-slate-500">comunicados publicados esta semana</p>
        </button>
      </div>

      <div className="mt-5 grid grid-cols-1 gap-5 lg:grid-cols-[1.4fr_1fr]">
        <section className="rounded-2xl border border-slate-200 bg-surface-card p-6 shadow-card">
          <p className="text-[11px] font-semibold uppercase tracking-wide text-slate-500">Acompanhamento</p>
          <h2 className="mt-1 font-display text-xl font-semibold text-slate-900">Presença por etapa</h2>

          <div className="mt-5 mb-6 flex items-center gap-6">
            <div>
              <span className="font-display text-4xl font-bold text-slate-900">93,6%</span>
              <p className="text-xs text-slate-400">média geral</p>
            </div>
            <div className="flex items-center gap-4 text-xs text-slate-500">
              <span className="flex items-center gap-1.5"><span className="h-2 w-2 rounded-full bg-amber-500" /> presença</span>
              <span className="flex items-center gap-1.5"><span className="h-2 w-2 rounded-full bg-slate-300" /> meta</span>
            </div>
          </div>

          <div className="space-y-5">
            {PRESENCE_BY_STAGE.map((s) => (
              <div key={s.stage}>
                <div className="mb-1.5 flex items-center justify-between text-sm">
                  <span className="text-slate-600">{s.stage}</span>
                  <span className="font-semibold text-slate-900">{s.value}%</span>
                </div>
                <div className="relative h-2 w-full overflow-hidden rounded-full bg-surface-muted">
                  <div className="absolute inset-y-0 left-0 w-[92%] border-r-2 border-dashed border-slate-300" />
                  <div className="h-full rounded-full bg-amber-500" style={{ width: `${s.value}%` }} />
                </div>
              </div>
            ))}
          </div>
        </section>

        <section className="rounded-2xl border border-slate-200 bg-surface-card p-6 shadow-card">
          <div className="mb-5 flex items-center justify-between">
            <div>
              <p className="text-[11px] font-semibold uppercase tracking-wide text-slate-500">Próximos dias</p>
              <h2 className="mt-1 font-display text-xl font-semibold text-slate-900">Agenda escolar</h2>
            </div>
            <span className="flex h-9 w-9 items-center justify-center rounded-lg bg-amber-100 text-amber-700">
              <CalendarDays className="h-4 w-4" />
            </span>
          </div>

          <ul className="space-y-2">
            {proximosEventos.map((item) => {
              const [, m, d] = item.data.split('-');
              return (
                <li key={item.id}>
                  <button
                    onClick={() => onNavigate('eventos')}
                    className="flex w-full items-center gap-3 rounded-xl p-2 text-left transition-colors hover:bg-surface-muted"
                  >
                    <span className="flex h-11 w-11 shrink-0 flex-col items-center justify-center rounded-lg bg-brand-action text-white">
                      <span className="text-[10px] font-semibold uppercase leading-none">{MONTH_NAMES[Number(m) - 1]}</span>
                      <span className="text-sm font-bold leading-none">{d}</span>
                    </span>
                    <span className="min-w-0 flex-1">
                      <span className="block truncate text-sm font-medium text-slate-800">{item.titulo}</span>
                      <span className="block truncate text-xs text-slate-400">
                        {item.horario ? `${item.horario} — ` : ''}{item.local}
                      </span>
                    </span>
                    <ChevronRight className="h-4 w-4 shrink-0 text-slate-300" />
                  </button>
                </li>
              );
            })}
            {proximosEventos.length === 0 && (
              <p className="py-4 text-center text-sm text-slate-400">Nenhum evento agendado.</p>
            )}
          </ul>
        </section>
      </div>
    </>
  );
}