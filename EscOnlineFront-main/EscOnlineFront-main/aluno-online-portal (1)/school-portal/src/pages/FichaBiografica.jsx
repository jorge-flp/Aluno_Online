import React, { useState } from 'react';
import { ArrowRight } from 'lucide-react';
import Breadcrumb from '../components/Breadcrumb';
import Button from '../components/Button';

const tabs = ['Dados Pessoais', 'Responsável', 'Vida Escolar', 'Saúde | Alimentação'];

const tabFields = {
  0: {
    notice:
      'Todas as informações de dados pessoais são migradas do sistema de matrícula da rede estadual de ensino. Caso algum dado esteja desatualizado, procure a secretaria da escola.',
    fields: [
      ['Nome', 'Email'],
      ['Nascimento', 'Município Nascimento', 'Cor/Raça'],
      ['Sexo', 'Celular', 'CPF'],
      ['NIS', 'RG', 'RG Órgão Expeditor', 'RG Data Expedição'],
    ],
    hasAddress: true,
  },
  1: {
    notice:
      'Algumas informações não podem ser editadas por aqui, pois alguns dados estão vindo diretamente da sua matrícula escolar. Caso alguma dessas informações esteja desatualizada, procure a secretaria da sua escola.',
    fields: [
      ['Parentesco', 'Nome'],
      ['Local de Trabalho', 'Email'],
      ['Nascimento', 'Profissão', 'Escolaridade'],
      ['Telefone'],
    ],
    hasAddress: true,
  },
  2: {
    notice: 'Informações sobre a trajetória escolar do aluno em anos anteriores.',
    fields: [
      ['Escola Anterior', 'Ano de Conclusão'],
      ['Necessidades Especiais', 'Observações'],
    ],
    hasAddress: false,
  },
  3: {
    notice: 'Informações de saúde e alimentação relevantes para a rotina escolar.',
    fields: [
      ['Tipo Sanguíneo', 'Alergias'],
      ['Restrições Alimentares', 'Uso Contínuo de Medicamento'],
    ],
    hasAddress: false,
  },
};

const gridColsClass = {
  1: 'sm:grid-cols-1',
  2: 'sm:grid-cols-2',
  3: 'sm:grid-cols-3',
  4: 'sm:grid-cols-4',
};

export default function FichaBiografica() {
  const [activeTab, setActiveTab] = useState(0);
  const current = tabFields[activeTab];
  const isLastTab = activeTab === tabs.length - 1;

  function goNext() {
    if (!isLastTab) setActiveTab((t) => t + 1);
  }

  return (
    <div>
      <Breadcrumb items={['Início', 'Diretor de Turma', 'Ficha Biográfica']} />

      <div className="bg-white rounded-xl2 shadow-card p-5 md:p-6">
        <div className="flex gap-6 border-b border-slate-100 mb-4 overflow-x-auto">
          {tabs.map((tab, i) => (
            <button
              key={tab}
              onClick={() => setActiveTab(i)}
              className={`pb-3 text-sm font-semibold whitespace-nowrap border-b-2 transition-colors ${
                activeTab === i
                  ? 'border-slate-800 text-slate-800'
                  : 'border-transparent text-slate-400 hover:text-slate-600'
              }`}
            >
              {tab}
            </button>
          ))}
        </div>

        <div className="border border-slate-200 bg-slate-50 text-slate-600 text-xs rounded-lg p-3 mb-5">
          {current.notice}
        </div>

        <div className="space-y-4">
          {current.fields.map((row, i) => (
            <div key={i} className={`grid gap-4 ${gridColsClass[Math.min(row.length, 4)]}`}>
              {row.map((field) => (
                <FormField key={field} label={field} />
              ))}
            </div>
          ))}
        </div>

        {current.hasAddress && (
          <div className="mt-8">
            <div className="flex items-center gap-3 mb-4">
              <h3 className="font-bold text-slate-700 text-sm tracking-wide">ENDEREÇO</h3>
              <div className="h-0.5 flex-1 bg-slate-200" />
            </div>
            <div className="grid gap-4 sm:grid-cols-3">
              <FormField label="CEP" />
              <FormField label="Rua" />
              <FormField label="Nº" />
            </div>
            <div className="grid gap-4 sm:grid-cols-3 mt-4">
              <FormField label="Complemento" />
              <FormField label="Estado" />
              <FormField label="Município" />
            </div>
            <div className="grid gap-4 sm:grid-cols-3 mt-4">
              <FormField label="Bairro" />
            </div>
          </div>
        )}

        <div className="flex items-center justify-between mt-8">
          <Button
            variant="ghost"
            disabled={activeTab === 0}
            onClick={() => setActiveTab((t) => Math.max(0, t - 1))}
          >
            Anterior
          </Button>
          {isLastTab ? (
            <Button variant="primary">Salvar</Button>
          ) : (
            <Button variant="primary" icon={ArrowRight} onClick={goNext}>
              Próximo
            </Button>
          )}
        </div>
      </div>
    </div>
  );
}

function FormField({ label }) {
  return (
    <div>
      <input
        type="text"
        placeholder={label.toUpperCase()}
        className="w-full rounded-lg border border-slate-200 bg-slate-50 px-4 py-2.5 text-xs text-slate-600 placeholder:text-slate-400 outline-none transition-colors focus:bg-white focus:border-slate-400 focus:ring-2 focus:ring-slate-200"
      />
    </div>
  );
}
