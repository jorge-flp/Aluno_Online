import React from 'react';
import { Megaphone } from 'lucide-react';
import { comunicadosResponsavel } from '../../data/mockData';

export default function Comunicados() {
  return (
    <div className="space-y-4">
      {comunicadosResponsavel.map((c) => (
        <div key={c.id} className="bg-white rounded-xl2 shadow-card p-5">
          <div className="flex items-start gap-3">
            <div className="w-9 h-9 rounded-full bg-brand-50 flex items-center justify-center shrink-0">
              <Megaphone size={16} className="text-brand-400" />
            </div>
            <div>
              <p className="text-xs text-slate-400 mb-1">{c.data}</p>
              <h3 className="font-bold text-slate-800 text-sm mb-1">{c.titulo}</h3>
              <p className="text-sm text-slate-500">{c.resumo}</p>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}