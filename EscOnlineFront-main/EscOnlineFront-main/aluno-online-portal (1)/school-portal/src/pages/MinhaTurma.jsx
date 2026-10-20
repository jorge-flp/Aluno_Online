import React from 'react';
import Breadcrumb from '../components/Breadcrumb';
import { classInfo, classmates } from '../data/mockData';

export default function MinhaTurma() {
  return (
    <div>
      <Breadcrumb items={['Início', 'Minha Turma']} />

      <div className="bg-white border border-slate-100 rounded-xl2 shadow-card px-5 py-4 mb-6 grid gap-x-8 gap-y-1.5 sm:grid-cols-2 lg:grid-cols-4 text-sm">
        <p><span className="font-semibold text-slate-400">CREDE:</span> <span className="text-slate-700">{classInfo.crede}</span></p>
        <p><span className="font-semibold text-slate-400">ESCOLA:</span> <span className="text-slate-700">{classInfo.school}</span></p>
        <p><span className="font-semibold text-slate-400">TURMA:</span> <span className="text-slate-700">{classInfo.className}</span></p>
        <div className="flex gap-6">
          <p><span className="font-semibold text-slate-400">ANO:</span> <span className="text-slate-700">{classInfo.year}</span></p>
          <p><span className="font-semibold text-slate-400">LETIVO:</span> <span className="text-slate-700">{classInfo.status}</span></p>
        </div>
      </div>

      <div className="grid gap-4 grid-cols-2 sm:grid-cols-3 lg:grid-cols-4">
        {classmates.map((name) => (
          <button
            key={name}
            className="bg-white rounded-xl2 shadow-card hover:shadow-card-hover hover:-translate-y-0.5 active:translate-y-0 transition-all p-4 flex flex-col items-center text-center gap-2"
          >
            <div className="w-16 h-16 rounded-full overflow-hidden bg-gradient-to-b from-sky-200 to-emerald-300" />
            <span className="text-xs font-semibold text-slate-700 uppercase">{name}</span>
          </button>
        ))}
      </div>
    </div>
  );
}
