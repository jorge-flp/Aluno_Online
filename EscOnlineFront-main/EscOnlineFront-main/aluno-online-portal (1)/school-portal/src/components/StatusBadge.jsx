import React from 'react';

const TONE_MAP = {
  publicado: 'bg-indigo-50 text-indigo-700',
  confirmado: 'bg-indigo-50 text-indigo-700',
  ativa: 'bg-emerald-50 text-emerald-700',
  ativo: 'bg-emerald-50 text-emerald-700',
  concluída: 'bg-emerald-50 text-emerald-700',
  concluida: 'bg-emerald-50 text-emerald-700',
  concluído: 'bg-emerald-50 text-emerald-700',
  concluido: 'bg-emerald-50 text-emerald-700',
  rascunho: 'bg-slate-100 text-slate-600',
  pendente: 'bg-amber-50 text-amber-700',
  'em análise': 'bg-slate-100 text-slate-600',
  'em analise': 'bg-slate-100 text-slate-600',
  'em revisão': 'bg-slate-100 text-slate-600',
  'em revisao': 'bg-slate-100 text-slate-600',
  'em andamento': 'bg-sky-50 text-sky-700',
  'em divulgação': 'bg-slate-100 text-slate-600',
  'em divulgacao': 'bg-slate-100 text-slate-600',
  agendado: 'bg-sky-50 text-sky-700',
  planejado: 'bg-slate-100 text-slate-600',
};

export default function StatusBadge({ status }) {
  const tone = TONE_MAP[status.toLowerCase()] ?? 'bg-slate-100 text-slate-600';
  return (
    <span className={`inline-flex items-center rounded-full px-2.5 py-1 text-xs font-medium ${tone}`}>
      {status}
    </span>
  );
}