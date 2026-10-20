import React, { useState } from 'react';
import Breadcrumb from '../components/Breadcrumb';
import { months } from '../data/mockData';
import { buscarFrequencia } from '../services/Faltasservice';

export default function Frequencia() {
  const [activeMonth, setActiveMonth] = useState(7);
  const [attendance, setAttendance] = useState([]);
  const [carregando, setCarregando] = useState(true);

  React.useEffect(() => {
    async function carregarFrequencia() {
      setCarregando(true);
      const dados = await buscarFrequencia(activeMonth);
      setAttendance(dados);
      setCarregando(false);
    }

    carregarFrequencia();
  }, [activeMonth]);

  return (
    <div>
      <Breadcrumb items={['Início', 'Frequência']} />

      <div className="bg-white rounded-xl2 shadow-card overflow-hidden">
        <div className="px-5 py-3 border-b border-slate-100 font-bold text-sm text-slate-700">Frequência</div>

        <div className="p-5 md:p-6">
          <div className="flex gap-1 overflow-x-auto mb-5 border-b border-slate-100 pb-1">
            {months.map((m, i) => (
              <button
                key={m}
                onClick={() => setActiveMonth(i)}
                className={`px-3 py-1.5 text-xs font-bold rounded-lg whitespace-nowrap transition-colors ${
                  activeMonth === i
                    ? 'bg-brand-400 text-white'
                    : 'text-slate-400 hover:bg-brand-50 hover:text-brand-700'
                }`}
              >
                {m}
              </button>
            ))}
          </div>

          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="text-left text-slate-400 border-b border-slate-100">
                  <th className="py-2 pr-4 font-semibold w-32">Faltas Mensais</th>
                  <th className="py-2 px-4 font-semibold">Disciplinas</th>
                </tr>
              </thead>
              <tbody>
                {attendance.map((row) => (
                  <React.Fragment key={row.subject}>
                    <tr className="hover:bg-slate-50">
                      <td
                        className={`py-2 pr-4 font-bold ${
                          row.absences > 0 ? 'text-red-500' : 'text-slate-700'
                        }`}
                      >
                        {row.absences}
                      </td>
                      <td className="py-2 px-4 text-slate-600 uppercase text-xs font-semibold">
                        {row.subject}
                      </td>
                    </tr>
                    {row.dividerAfter && (
                      <tr>
                        <td colSpan={2}>
                          <hr className="border-brand-400" />
                        </td>
                      </tr>
                    )}
                  </React.Fragment>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}
