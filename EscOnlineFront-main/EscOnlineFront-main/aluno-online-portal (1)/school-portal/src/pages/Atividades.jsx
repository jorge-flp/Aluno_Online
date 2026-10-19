import React, { useState } from 'react';
import { CheckCircle2, AlertTriangle, ListChecks } from 'lucide-react';
import Breadcrumb from '../components/Breadcrumb';
import EmptyState from '../components/EmptyState';

const tabs = ['Ativas', 'Inativas'];

export default function Atividades() {
  const [activeTab, setActiveTab] = useState(0);

  const stats = { sent: 0, notSent: 0, total: 0 };

  return (
    <div>
      <div className="mb-6 overflow-hidden rounded-xl2 border border-slate-100 bg-white shadow-card">
        <Breadcrumb items={['Início', 'Atividades']} />
      </div>

      <div className="grid gap-6 lg:grid-cols-3">
        <div className="lg:col-span-2 bg-white rounded-xl2 shadow-card overflow-hidden">
          <div className="flex border-b border-slate-100">
            {tabs.map((tab, i) => (
              <button
                key={tab}
                onClick={() => setActiveTab(i)}
                className={`flex-1 py-3 text-sm font-semibold border-b-2 transition-colors ${
                  activeTab === i
                    ? 'border-slate-900 text-slate-800'
                    : 'border-transparent text-slate-400 hover:text-slate-600'
                }`}
              >
                {tab}
              </button>
            ))}
          </div>
          <EmptyState message="Nenhuma atividade encontrada" />
        </div>

        <div className="bg-white rounded-xl2 shadow-card overflow-hidden h-fit">
          <div className="border-b border-slate-100 bg-slate-50 px-5 py-3 font-bold text-sm text-slate-700">
            Desempenho de Atividades
          </div>
          <div className="grid grid-cols-3 divide-x divide-slate-100 text-center py-6">
            <StatItem icon={CheckCircle2} color="text-emerald-500" label="Enviadas" value={stats.sent} />
            <StatItem icon={AlertTriangle} color="text-amber-500" label="Não enviadas" value={stats.notSent} />
            <StatItem icon={ListChecks} color="text-slate-500" label="Total" value={stats.total} />
          </div>
        </div>
      </div>
    </div>
  );
}

function StatItem({ icon: Icon, color, label, value }) {
  return (
    <div className="flex flex-col items-center gap-1 px-2">
      <Icon size={22} className={color} />
      <span className="text-xs font-semibold text-slate-600">{label}</span>
      <span className="text-xl font-extrabold text-slate-800">{value}</span>
    </div>
  );
}
