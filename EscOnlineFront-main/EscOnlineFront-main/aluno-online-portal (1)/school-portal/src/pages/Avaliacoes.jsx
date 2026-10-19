import React from 'react';
import Breadcrumb from '../components/Breadcrumb';
import EmptyState from '../components/EmptyState';

export default function Avaliacoes() {
  return (
    <div>
      <Breadcrumb items={['Início', 'Avaliações']} />

      <div className="bg-white rounded-xl2 shadow-card overflow-hidden">
        <div className="bg-brand-400 text-white px-5 py-3 font-bold text-sm">Avaliações</div>
        <EmptyState message="Nenhum registro de avaliação encontrado" />
      </div>
    </div>
  );
}
