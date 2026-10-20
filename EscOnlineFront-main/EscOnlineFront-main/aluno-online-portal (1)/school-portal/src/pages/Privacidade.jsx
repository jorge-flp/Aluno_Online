import React from 'react';
import Breadcrumb from '../components/Breadcrumb';

export default function Privacidade() {
  return (
    <div>
      <Breadcrumb items={['Início', 'Políticas de Privacidade']} />

      <div className="bg-white rounded-xl2 shadow-card p-6 md:p-8 space-y-4 text-sm text-slate-600 leading-relaxed">
        <h2 className="text-lg font-bold text-brand-900">Políticas de Privacidade</h2>
        <p>
          Esta página descreve, em linhas gerais, como os dados do aluno são tratados dentro da
          plataforma. Este é um texto de exemplo (mock) — o conteúdo definitivo deve ser fornecido
          pela equipe jurídica/administrativa responsável pelo sistema.
        </p>
        <p>
          Os dados pessoais cadastrados são utilizados exclusivamente para fins acadêmicos e de
          comunicação entre escola, aluno e responsáveis, em conformidade com a legislação vigente
          de proteção de dados.
        </p>
      </div>
    </div>
  );
}
