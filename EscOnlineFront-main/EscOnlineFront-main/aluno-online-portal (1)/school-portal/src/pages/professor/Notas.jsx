import React, { useState } from 'react';
import { Star, User } from 'lucide-react';

const alunosIniciais = [
  { nome: 'Amanda Felix Veras', n1: '8,5', n2: '9,0', atividade: '10' },
  { nome: 'Anna Narah Queiroz Silva', n1: '7,0', n2: '6,5', atividade: '8,0' },
  { nome: 'Antonia Ticyane Oliveira', n1: '9,5', n2: '9,0', atividade: '10' },
  { nome: 'Antonio Eduardo da Silva', n1: '4,0', n2: '5,5', atividade: '6,0' },
  { nome: 'Danyelle Batista Alencar', n1: '8,0', n2: '8,5', atividade: '9,0' },
  { nome: 'Eduardo Franklin da Silva', n1: '6,0', n2: '7,0', atividade: '7,5' },
  { nome: 'Estela Garcia da Silva', n1: '10', n2: '9,5', atividade: '10' },
  { nome: 'Gustavo Silva Goncalves', n1: '5,0', n2: '4,5', atividade: '6,0' },
  { nome: 'Heitor Goncalves Teixeira', n1: '7,5', n2: '8,0', atividade: '8,5' },
  { nome: 'Joao Victor da Silva Guedes', n1: '6,5', n2: '7,0', atividade: '7,0' },
];

function calcularMedia(n1, n2, atividade) {
  const valores = [n1, n2, atividade].map((v) => parseFloat(v.replace(',', '.')));
  const media = valores.reduce((a, b) => a + b, 0) / valores.length;
  return media.toFixed(1).replace('.', ',');
}

export default function Notas() {
  const [alunos, setAlunos] = useState(alunosIniciais);

  function atualizarNota(index, campo, valor) {
    const novosAlunos = [...alunos];
    novosAlunos[index][campo] = valor;
    setAlunos(novosAlunos);
  }

  return (
    <div>
      <div className="flex flex-wrap items-center justify-between gap-4 mb-6">
        <h2 className="flex items-center gap-2 text-lg font-bold text-slate-800">
          <Star size={20} className="text-brand-400" />
          Lançar Notas
        </h2>

        <div className="flex flex-wrap gap-2">
          <FiltroChip label="3ª Série J" />
          <FiltroChip label="Matemática" />
          <FiltroChip label="2º Bimestre" active />
        </div>
      </div>

      <div className="bg-white rounded-xl2 shadow-card overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-slate-100 text-left text-slate-400">
                <th className="px-5 py-3 font-semibold">Aluno</th>
                <th className="px-4 py-3 font-semibold text-center">Nota 1</th>
                <th className="px-4 py-3 font-semibold text-center">Nota 2</th>
                <th className="px-4 py-3 font-semibold text-center">Atividade</th>
                <th className="px-4 py-3 font-semibold text-center">Média</th>
              </tr>
            </thead>
            <tbody>
              {alunos.map((aluno, index) => {
                const media = calcularMedia(aluno.n1, aluno.n2, aluno.atividade);
                const mediaBaixa = parseFloat(media.replace(',', '.')) < 6;

                return (
                  <tr key={aluno.nome} className="border-b border-slate-50 hover:bg-slate-50">
                    <td className="px-5 py-3">
                      <div className="flex items-center gap-2.5">
                        <div className="w-8 h-8 rounded-full bg-brand-50 flex items-center justify-center shrink-0">
                          <User size={15} className="text-brand-400" />
                        </div>
                        <span className="font-medium text-slate-700">{aluno.nome}</span>
                      </div>
                    </td>
                    <td className="px-4 py-3 text-center">
                      <NotaInput value={aluno.n1} onChange={(v) => atualizarNota(index, 'n1', v)} />
                    </td>
                    <td className="px-4 py-3 text-center">
                      <NotaInput value={aluno.n2} onChange={(v) => atualizarNota(index, 'n2', v)} />
                    </td>
                    <td className="px-4 py-3 text-center">
                      <NotaInput value={aluno.atividade} onChange={(v) => atualizarNota(index, 'atividade', v)} />
                    </td>
                    <td className={`px-4 py-3 text-center font-bold ${mediaBaixa ? 'text-red-500' : 'text-emerald-600'}`}>
                      {media}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>

        <div className="flex flex-wrap items-center justify-between gap-3 px-5 py-4 border-t border-slate-100">
          <span className="text-xs text-slate-400">
            {alunos.length} de 28 alunos exibidos · última alteração salva às 10:42
          </span>
          <button className="bg-brand-400 hover:bg-brand-500 text-white text-sm font-semibold px-5 py-2 rounded-lg transition-colors">
            Salvar notas
          </button>
        </div>
      </div>
    </div>
  );
}

function FiltroChip({ label, active }) {
  return (
    <button
      className={`px-3.5 py-1.5 rounded-lg text-xs font-semibold transition-colors ${
        active
          ? 'bg-brand-400 text-white'
          : 'bg-slate-100 text-slate-500 hover:bg-slate-200'
      }`}
    >
      {label}
    </button>
  );
}

function NotaInput({ value, onChange }) {
  return (
    <input
      value={value}
      onChange={(e) => onChange(e.target.value)}
      className="w-16 text-center rounded-lg border border-slate-200 px-2 py-1.5 text-sm font-medium outline-none focus:border-brand-400 focus:ring-2 focus:ring-brand-400/20"
    />
  );
}