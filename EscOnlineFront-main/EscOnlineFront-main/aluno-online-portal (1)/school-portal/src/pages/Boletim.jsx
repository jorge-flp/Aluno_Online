import React, { useState } from 'react';
import { ChevronDown } from 'lucide-react';
import Breadcrumb from '../components/Breadcrumb';
import { subjects } from '../data/mockData';

const bimesters = ['1º Bimestre', '2º Bimestre', '3º Bimestre', '4º Bimestre'];

export default function Boletim() {
  const [activeBimester, setActiveBimester] = useState(0);
  const [expanded, setExpanded] = useState({});

  function toggle(name) {
    setExpanded((prev) => ({ ...prev, [name]: !prev[name] }));
  }

  return (
    <div>
      <Breadcrumb items={['Início', 'Boletim']} />

      <div className="bg-white rounded-xl2 shadow-card p-5 md:p-6">
        <div className="flex flex-wrap items-center justify-between gap-3 mb-4">
          <h2 className="text-lg font-bold text-slate-700">Notas</h2>
          <select className="text-sm border border-slate-200 rounded-lg px-3 py-1.5 text-slate-600 outline-none focus:border-brand-400 focus:ring-2 focus:ring-brand-400/30">
            <option>2026</option>
            <option>2025</option>
          </select>
        </div>

        <div className="flex gap-6 border-b border-slate-100 mb-2 overflow-x-auto">
          {bimesters.map((label, i) => (
            <button
              key={label}
              onClick={() => setActiveBimester(i)}
              className={`pb-3 text-sm font-semibold whitespace-nowrap border-b-2 transition-colors ${
                activeBimester === i
                  ? 'border-brand-400 text-brand-700'
                  : 'border-transparent text-slate-400 hover:text-slate-600'
              }`}
            >
              {label}
            </button>
          ))}
        </div>

        <ul className="divide-y divide-slate-100">
          {subjects.map((s) => {
            const grade = [s.b1, s.b2, s.b3, s.b4][activeBimester];
            const isOpen = !!expanded[s.name];
            return (
              <li key={s.name}>
                <button
                  onClick={() => toggle(s.name)}
                  className="w-full flex items-center justify-between py-3 text-left hover:bg-slate-50 active:bg-slate-100 transition-colors rounded-lg px-2"
                >
                  <span className="flex items-center gap-3">
                    <span className="text-brand-700 font-bold w-8 text-center">{grade}</span>
                    <span className="font-semibold text-slate-700 text-sm">{s.name}</span>
                  </span>
                  <ChevronDown
                    size={18}
                    className={`text-slate-400 transition-transform ${isOpen ? 'rotate-180' : ''}`}
                  />
                </button>
                {isOpen && (
                  <div className="px-2 pb-3 text-xs text-slate-500">
                    Detalhamento de avaliações desta disciplina ainda não disponível.
                  </div>
                )}
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
}
