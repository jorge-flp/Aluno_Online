import React from 'react';
import { subjects } from '../../data/mockData';

export default function Boletim() {
  return (
    <div className="bg-white rounded-xl2 shadow-card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100 text-left text-slate-400">
              <th className="px-5 py-3 font-semibold">Disciplina</th>
              <th className="px-4 py-3 font-semibold text-center">1º Bim</th>
              <th className="px-4 py-3 font-semibold text-center">2º Bim</th>
              <th className="px-4 py-3 font-semibold text-center">3º Bim</th>
              <th className="px-4 py-3 font-semibold text-center">4º Bim</th>
            </tr>
          </thead>
          <tbody>
            {subjects.map((s) => (
              <tr key={s.name} className="border-b border-slate-50 hover:bg-slate-50">
                <td className="px-5 py-3 font-medium text-slate-700">{s.name}</td>
                <td className="px-4 py-3 text-center font-bold text-brand-600">{s.b1}</td>
                <td className="px-4 py-3 text-center text-slate-400">{s.b2}</td>
                <td className="px-4 py-3 text-center text-slate-400">{s.b3}</td>
                <td className="px-4 py-3 text-center text-slate-400">{s.b4}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}