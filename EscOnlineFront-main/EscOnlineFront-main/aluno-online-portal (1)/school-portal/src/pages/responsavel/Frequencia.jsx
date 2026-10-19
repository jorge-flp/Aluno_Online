import React from 'react';
import { attendance } from '../../data/mockData';

export default function Frequencia() {
  return (
    <div className="bg-white rounded-xl2 shadow-card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100 text-left text-slate-400">
              <th className="px-5 py-3 font-semibold">Disciplina</th>
              <th className="px-4 py-3 font-semibold text-center">Faltas</th>
            </tr>
          </thead>
          <tbody>
            {attendance.map((a) => (
              <tr key={a.subject} className="border-b border-slate-50 hover:bg-slate-50">
                <td className="px-5 py-3 font-medium text-slate-700 uppercase text-xs">{a.subject}</td>
                <td className={`px-4 py-3 text-center font-bold ${a.absences > 0 ? 'text-red-500' : 'text-slate-700'}`}>
                  {a.absences}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}