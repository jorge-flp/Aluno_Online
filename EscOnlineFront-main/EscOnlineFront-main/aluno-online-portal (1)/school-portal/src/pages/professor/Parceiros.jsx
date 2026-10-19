import React from 'react';
import { GraduationCap, FileEdit, BarChart3, Award, BookOpen, Video, ExternalLink } from 'lucide-react';

const parceiros = [
  { icon: GraduationCap, nome: 'Google Classroom', desc: 'Organize tarefas, aumente a colaboração e melhore a comunicação com suas turmas.' },
  { icon: FileEdit, nome: 'Enem Mix', desc: 'Simulados, corretor de redação e mais de 1.000 aulas em diferentes cursos para apoiar seus alunos.' },
  { icon: BarChart3, nome: 'SISEDU', desc: 'Sistema Online de Avaliação, Suporte e Acompanhamento Educacional.' },
  { icon: Award, nome: 'SIC', desc: 'Plataforma para gerenciar inscrições, acompanhar andamento e emitir certificados dos cursos ofertados.' },
  { icon: BookOpen, nome: 'Conexão Educação', desc: 'Sistema de compartilhamento de conteúdos educativos: videoaulas, podcasts e guias para alunos e professores.' },
  { icon: Video, nome: 'ENEM na rede', desc: 'Projeto para orientar os alunos através de videoaulas na preparação para o ENEM.' },
];

export default function Parceiros() {
  return (
    <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3">
      {parceiros.map(({ icon: Icon, nome, desc }) => (
        <div
          key={nome}
          className="bg-white rounded-xl2 shadow-card hover:shadow-card-hover transition-shadow p-6 flex flex-col"
        >
          <div className="w-11 h-11 rounded-xl bg-brand-50 flex items-center justify-center mb-4">
            <Icon size={22} className="text-brand-500" />
          </div>

          <h3 className="font-bold text-slate-800 mb-2">{nome}</h3>
          <p className="text-sm text-slate-500 leading-relaxed flex-1">{desc}</p>

          <button className="mt-4 flex items-center gap-1.5 text-sm font-semibold text-brand-500 hover:text-brand-700 transition-colors self-start">
            Acessar
            <ExternalLink size={14} />
          </button>
        </div>
      ))}
    </div>
  );
}