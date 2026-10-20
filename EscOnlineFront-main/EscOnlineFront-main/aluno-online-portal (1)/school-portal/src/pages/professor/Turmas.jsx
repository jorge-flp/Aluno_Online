import React from 'react';
import { teacherClasses } from '../../data/mockData';
import { Users, ClipboardCheck, Star, NotebookPen } from 'lucide-react';

const info = {
  crede: 'CREDE 16',
  escola: 'EEEP ALFREDO NUNES DE MELO',
  disciplina: 'Matemática',
  ano: '2026',
};

export default function Turmas() {
  return (
    <div>
      <div className="mb-6 flex flex-wrap gap-x-8 gap-y-2 rounded-2xl border border-slate-200 bg-white px-5 py-4 text-sm shadow-card">
        <InfoItem label="CREDE" value={info.crede} />
        <InfoItem label="Escola" value={info.escola} />
        <InfoItem label="Disciplina" value={info.disciplina} />
        <InfoItem label="Ano letivo" value={info.ano} />
      </div>

      <div className="grid gap-5 md:grid-cols-2 lg:grid-cols-3">
        {teacherClasses.map((turma) => (
          <div
            key={turma.id}
            className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-card transition-shadow hover:shadow-card-hover"
          >
            <div className="border-b border-slate-100 px-5 py-4">
              <h3 className="text-sm font-bold text-slate-800">{turma.name}</h3>
              <p className="mt-0.5 text-xs text-slate-400">{turma.info}</p>
            </div>

            <div className="space-y-3 p-5">
              <Stat icon={Users} label="Alunos" value={turma.students} />
              <Stat icon={ClipboardCheck} label="Frequência média" value={`${turma.avgAttendance}%`} />
              <Stat icon={Star} label="Média da turma" value={turma.avgGrade} />
            </div>

            <div className="flex border-t border-slate-100">
              <ActionLink icon={NotebookPen} label="Diário" />
              <ActionLink icon={Star} label="Notas" />
              <ActionLink icon={ClipboardCheck} label="Frequência" />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

function InfoItem({ label, value }) {
  return (
    <div className="flex items-center gap-1.5">
      <span className="font-medium text-slate-400">{label}:</span>
      <span className="font-semibold text-slate-700">{value}</span>
    </div>
  );
}

function Stat({ icon: Icon, label, value }) {
  return (
    <div className="flex items-center justify-between text-sm">
      <span className="flex items-center gap-2 text-slate-500">
        <Icon size={16} className="text-slate-600" />
        {label}
      </span>
      <span className="font-bold text-slate-800">{value}</span>
    </div>
  );
}

function ActionLink({ icon: Icon, label }) {
  return (
    <button className="flex flex-1 items-center justify-center gap-1.5 py-2.5 text-xs font-semibold text-slate-500 transition-colors hover:bg-slate-50 hover:text-slate-800">
      <Icon size={14} />
      {label}
    </button>
  );
}