import React, { useState } from 'react';
import Breadcrumb from '../components/Breadcrumb';
import { calendarEvents, months } from '../data/mockData';

export default function Calendario() {
  const [activeMonth, setActiveMonth] = useState(7);

  return (
    <div>
      <Breadcrumb items={['Início', 'Calendário']} />

      <div className="bg-white rounded-xl2 shadow-card p-5 md:p-6">
        <div className="flex flex-wrap items-center justify-between gap-3 mb-4">
          <h2 className="text-lg font-bold text-slate-700">Calendário</h2>
          <select className="text-sm border border-slate-200 rounded-lg px-3 py-1.5 text-slate-600 outline-none focus:border-brand-400 focus:ring-2 focus:ring-brand-400/30">
            <option>2026</option>
            <option>2025</option>
          </select>
        </div>

        <div className="flex gap-1 overflow-x-auto mb-5 border-b border-slate-100 pb-1">
          {months.map((m, i) => (
            <button
              key={m}
              onClick={() => setActiveMonth(i)}
              className={`px-3 py-1.5 text-xs font-bold rounded-lg whitespace-nowrap transition-colors ${
                activeMonth === i
                  ? 'bg-slate-900 text-white'
                  : 'text-slate-500 hover:bg-slate-100 hover:text-slate-800'
              }`}
            >
              {m}
            </button>
          ))}
        </div>

        <div className="space-y-2">
          {calendarEvents.map((ev, i) => (
            <div
              key={i}
              className="flex items-center gap-4 bg-slate-50 hover:bg-brand-50 rounded-xl px-4 py-3 transition-colors"
            >
              <div className="flex flex-col items-center justify-center w-12 shrink-0">
                <span className="font-extrabold text-slate-700 leading-none">{ev.day}</span>
                <span className="text-[10px] font-bold text-slate-400">{ev.weekday}</span>
              </div>
              <div>
                <p className="font-semibold text-slate-700 text-sm">{ev.title}</p>
                <p className="text-xs text-slate-400">{ev.info}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
