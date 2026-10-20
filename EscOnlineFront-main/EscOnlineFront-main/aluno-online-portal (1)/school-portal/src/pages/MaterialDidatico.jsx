import React from 'react';
import Breadcrumb from '../components/Breadcrumb';
import EmptyState from '../components/EmptyState';

export default function MaterialDidatico() {
  return (
    <div>
      <Breadcrumb items={['Início', 'Material didático']} />

      <div className="bg-white rounded-xl2 shadow-card overflow-hidden">
        <div className="px-5 py-3 border-b border-slate-100 font-bold text-sm text-slate-700">Arquivos</div>
        <EmptyState message="Nenhum material encontrado" />
      </div>
    </div>
  );
}
