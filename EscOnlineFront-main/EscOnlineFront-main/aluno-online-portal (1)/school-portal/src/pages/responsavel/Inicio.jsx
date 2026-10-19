import React from 'react';
import { GraduationCap, Star, ClipboardCheck } from 'lucide-react';
import { student, guardian, subjects, attendance } from '../../data/mockData';

export default function Inicio() {
  const mediaGeral = (
    subjects.reduce((soma, s) => soma + (parseFloat(s.b1?.replace(',', '.')) || 0), 0) / subjects.length
  ).toFixed(1);

  const totalFaltas = attendance.reduce((soma, a) => soma + a.absences, 0);

  return (
    <div className="space-y-6">
      <div className="flex items-center gap-4 rounded-2xl border border-slate-200 bg-white p-6 shadow-card">
        <div className="flex h-14 w-14 shrink-0 items-center justify-center rounded-full bg-slate-900 text-lg font-bold text-white">
          {student.avatarInitials}
        </div>
        <div>
          <p className="text-xs uppercase tracking-[0.14em] text-slate-400">Acompanhando</p>
          <h2 className="text-lg font-bold text-slate-800">{student.name}</h2>
          <p className="text-sm text-slate-500">{student.className}</p>
        </div>
      </div>

      <div className="grid gap-5 sm:grid-cols-2">
        <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card">
          <div className="mb-2 flex items-center gap-2 text-sm text-slate-500">
            <span className="flex h-8 w-8 items-center justify-center rounded-lg bg-slate-100 text-slate-700">
              <Star size={16} />
            </span>
            Média geral (1º bimestre)
          </div>
          <p className="text-3xl font-bold text-slate-800">{mediaGeral}</p>
        </div>

        <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card">
          <div className="mb-2 flex items-center gap-2 text-sm text-slate-500">
            <span className="flex h-8 w-8 items-center justify-center rounded-lg bg-slate-100 text-slate-700">
              <ClipboardCheck size={16} />
            </span>
            Faltas no período
          </div>
          <p className="text-3xl font-bold text-slate-800">{totalFaltas}</p>
        </div>
      </div>
    </div>
  );
}