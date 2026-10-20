import React from 'react';
import Breadcrumb from '../components/Breadcrumb';
import { teacher, teacherClasses } from '../data/mockData';
import { Users, ClipboardCheck, Star, NotebookPen } from 'lucide-react';

export default function MinhasTurmasProfessor() {
  return (
    <div>
      <Breadcrumb items={['Início', 'Minhas Turmas']} />

      {/* Cabeçalho com dados do professor */}
      <div className="flex items-center gap-4 mb-6">
        <div className="w-14 h-14 rounded-full bg-brand-400 text-white flex items-center justify-center text-lg font-bold shadow-card">
          {teacher.avatarInitials}
        </div>
        <div>
          <h2 className="text-lg font-bold text-slate-800">{teacher.name}</h2>
          <p className="text-sm text-slate-400">{teacher.subject}</p>
        </div>
      </div>

      {/* Grade de turmas */}
      <div className="grid gap-5 md:grid-cols-2 lg:grid-cols-3">
        {teacherClasses.map((turma) => (
          <div
            key={turma.id}
            className="bg-white rounded-xl2 shadow-card hover:shadow-card-hover transition-shadow overflow-hidden"
          >
            <div className="bg-brand-400 text-white px-5 py-3">
              <h3 className="font-bold text-sm">{turma.name}</h3>
              <p className="text-xs text-white/80 mt-0.5">{turma.info}</p>
            </div>

            <div className="p-5 space-y-3">
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

function Stat({ icon: Icon, label, value }) {
  return (
    <div className="flex items-center justify-between text-sm">
      <span className="flex items-center gap-2 text-slate-500">
        <Icon size={16} className="text-brand-400" />
        {label}
      </span>
      <span className="font-bold text-slate-800">{value}</span>
    </div>
  );
}

function ActionLink({ icon: Icon, label }) {
  return (
    <button className="flex-1 flex items-center justify-center gap-1.5 py-2.5 text-xs font-semibold text-slate-500 hover:bg-brand-50 hover:text-brand-700 transition-colors">
      <Icon size={14} />
      {label}
    </button>
  );
}