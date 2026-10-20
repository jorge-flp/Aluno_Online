import React from 'react';
import { Clock } from 'lucide-react';

const dias = [
  { d: 'SEG', data: '17/08', aulas: ['1ª Série B|Matemática', '1ª Série B|Matemática', '2ª Série A|Matemática', 'Planejamento', '3ª Série J|Matemática', '3ª Série J|Matemática'] },
  { d: 'TER', data: '18/08', aulas: ['3ª Série J|Matemática', '3ª Série J|Matemática', '2ª Série A|Matemática', 'Planejamento', '1ª Série B|Matemática', '2ª Série A|Matemática'] },
  { d: 'QUA', data: '19/08', aulas: ['2ª Série A|Matemática', '2ª Série A|Matemática', 'Coordenação', '3ª Série J|Matemática', '1ª Série B|Matemática', '1ª Série B|Matemática'] },
  { d: 'QUI', data: '20/08', aulas: ['3ª Série J|Matemática', '2ª Série A|Matemática', '2ª Série A|Matemática', 'Planejamento', '1ª Série B|Matemática', '—'] },
  { d: 'SEX', data: '21/08', aulas: ['1ª Série B|Matemática', '3ª Série J|Matemática', '3ª Série J|Matemática', 'Planejamento', '2ª Série A|Matemática', '—'] },
];

export default function Horario() {
  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h2 className="flex items-center gap-2 text-lg font-bold text-slate-800">
          <Clock size={20} className="text-brand-400" />
          Meu Horário
        </h2>
        <button className="bg-slate-100 hover:bg-slate-200 text-slate-600 text-xs font-semibold px-3.5 py-2 rounded-lg transition-colors">
          2026
        </button>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
        {dias.map((col) => (
          <div key={col.d} className="bg-white rounded-xl2 shadow-card overflow-hidden">
            <div className="bg-brand-50 px-4 py-3 text-center">
              <p className="font-bold text-brand-700 text-sm">{col.d}</p>
              <p className="text-xs text-slate-400">{col.data}</p>
            </div>

            <div className="p-3 space-y-2">
              {col.aulas.map((a, i) => {
                const [turma, disciplina] = a.split('|');
                const livre = !disciplina;

                return (
                  <div
                    key={i}
                    className={`flex items-center gap-2.5 rounded-lg px-3 py-2.5 ${
                      livre ? 'bg-slate-50' : 'bg-brand-50/60'
                    }`}
                  >
                    <span
                      className={`w-5 h-5 rounded-full flex items-center justify-center text-[10px] font-bold shrink-0 ${
                        livre ? 'bg-slate-200 text-slate-500' : 'bg-brand-400 text-white'
                      }`}
                    >
                      {i + 1}
                    </span>
                    <div className="min-w-0">
                      <p className={`text-xs font-semibold truncate ${livre ? 'text-slate-400' : 'text-slate-700'}`}>
                        {turma}
                      </p>
                      {disciplina && (
                        <p className="text-[11px] text-slate-400 truncate">{disciplina}</p>
                      )}
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}