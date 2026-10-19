import React, { useState } from 'react';
import { NotebookPen, User, RefreshCw } from 'lucide-react';
import { diarioTurma } from '../../data/mockData';

const turmasDisponiveis = Object.keys(diarioTurma);

export default function Diario() {
  const [turmaAtiva, setTurmaAtiva] = useState(turmasDisponiveis[0]);
  const alunos = diarioTurma[turmaAtiva] ?? [];

  return (
    <div>
      <div className="flex flex-wrap items-center justify-between gap-4 mb-6">
        <h2 className="flex items-center gap-2 text-lg font-bold text-slate-800">
          <NotebookPen size={20} className="text-brand-400" />
          Diário
        </h2>

        <div className="flex flex-wrap gap-2">
          {turmasDisponiveis.map((turma) => (
            <button
              key={turma}
              onClick={() => setTurmaAtiva(turma)}
              className={`px-3.5 py-1.5 rounded-lg text-xs font-semibold transition-colors ${
                turmaAtiva === turma
                  ? 'bg-brand-400 text-white'
                  : 'bg-slate-100 text-slate-500 hover:bg-slate-200'
              }`}
            >
              {turma}
            </button>
          ))}
        </div>
      </div>

      <div className="bg-white rounded-xl2 shadow-card overflow-hidden">
        <div className="flex items-center gap-2 px-5 py-3 bg-brand-50 text-brand-700 text-xs font-medium">
          <RefreshCw size={13} />
          Nota e frequência sincronizadas automaticamente do sistema de correção
        </div>

        <div className="overflow-x-auto">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-slate-100 text-left text-slate-400">
                <th className="px-5 py-3 font-semibold">Aluno</th>
                <th className="px-4 py-3 font-semibold text-center">Nota</th>
                <th className="px-4 py-3 font-semibold text-center">Faltas</th>
              </tr>
            </thead>
            <tbody>
              {alunos.map((a) => (
                <tr key={a.aluno} className="border-b border-slate-50 hover:bg-slate-50">
                  <td className="px-5 py-3">
                    <div className="flex items-center gap-2.5">
                      <div className="w-8 h-8 rounded-full bg-brand-50 flex items-center justify-center shrink-0">
                        <User size={15} className="text-brand-400" />
                      </div>
                      <span className="font-medium text-slate-700">{a.aluno}</span>
                    </div>
                  </td>
                  <td className={`px-4 py-3 text-center font-bold ${a.status === 'atencao' ? 'text-red-500' : 'text-emerald-600'}`}>
                    {a.nota}
                  </td>
                  <td className={`px-4 py-3 text-center font-semibold ${a.faltas >= 6 ? 'text-red-500' : 'text-slate-600'}`}>
                    {a.faltas}
                  </td>
                </tr>
              ))}
              {alunos.length === 0 && (
                <tr>
                  <td colSpan={3} className="py-8 text-center text-sm text-slate-400">
                    Nenhum aluno cadastrado nesta turma.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}