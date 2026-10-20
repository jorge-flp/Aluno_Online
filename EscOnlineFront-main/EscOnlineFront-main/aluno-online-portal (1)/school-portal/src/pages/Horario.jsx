import React from 'react';
import { Clock } from 'lucide-react';
import Breadcrumb from '../components/Breadcrumb';
import { weekDays, schedule } from '../data/mockData';

export default function Horario() {
  const maxSlots = Math.max(...Object.values(schedule).map((d) => d.length));

  return (
    <div>
      <Breadcrumb items={['Início', 'Horários']} />

      <div className="flex flex-wrap items-center justify-between gap-3 mb-4">
        <span className="flex items-center gap-2 text-brand-700 font-bold">
          <Clock size={18} />
          20 / 08 / 2026
        </span>
        <select className="text-sm border border-slate-200 rounded-lg px-3 py-1.5 text-slate-600 outline-none focus:border-brand-400 focus:ring-2 focus:ring-brand-400/30">
          <option>2026</option>
          <option>2025</option>
        </select>
      </div>

      <div className="overflow-x-auto -mx-4 px-4">
        <div className="grid grid-cols-5 gap-3 min-w-[640px]">
          {weekDays.map((day) => (
            <div key={day.key} className="bg-white rounded-xl2 shadow-card p-3">
              <p className="text-center font-bold text-slate-700 text-sm">
                {day.label} <span className="text-xs font-normal text-slate-400">{day.date}</span>
              </p>
              <ul className="mt-3 space-y-2">
                {Array.from({ length: maxSlots }).map((_, i) => {
                  const subject = schedule[day.key][i];
                  return (
                    <li
                      key={i}
                      className="flex items-center gap-2 text-xs text-slate-600 border-b border-slate-50 pb-2"
                    >
                      <span className="w-5 h-5 flex items-center justify-center rounded-full bg-brand-50 text-brand-700 font-bold text-[10px] shrink-0">
                        {i + 1}
                      </span>
                      {subject ?? <span className="text-slate-300">—</span>}
                    </li>
                  );
                })}
              </ul>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
