import React from 'react';
import { Pencil, FileText, Download, MapPin } from 'lucide-react';
import Card from '../components/Card';
import { student, school } from '../data/mockData';

export default function StudentInfo() {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between gap-3">
        <h2 className="text-2xl font-extrabold text-slate-900">Dados Pessoais</h2>
        <span className="inline-flex items-center rounded-full border border-slate-200 bg-slate-50 px-2.5 py-1 text-[10px] font-bold uppercase tracking-[0.14em] text-slate-500">
          Perfil do aluno
        </span>
      </div>

      <div className="grid gap-6 lg:grid-cols-2">
        <Card title="INFORMAÇÕES DO ALUNO">
          <div className="mb-5 flex items-start gap-4 rounded-xl border border-slate-100 bg-slate-50/60 p-3">
            <div className="flex h-16 w-16 shrink-0 items-center justify-center rounded-full bg-slate-900 text-lg font-bold text-white">
              {student.avatarInitials}
            </div>
            <div className="space-y-1 text-sm text-slate-600">
              <p><span className="font-semibold text-slate-800">Matrícula:</span> {student.registration}</p>
              <p><span className="font-semibold text-slate-800">Nome:</span> {student.name}</p>
              <p><span className="font-semibold text-slate-800">Nascimento:</span> {student.birthDate}</p>
            </div>
          </div>

          <div className="mb-4 rounded-lg border border-slate-200 bg-slate-50 p-3 text-xs text-slate-600">
            Mantenha seu e-mail pessoal atualizado para recuperar sua conta institucional quando
            precisar. Atualize o e-mail pessoal abaixo clicando no ícone de editar.
          </div>

          <FieldRow label="E-mail pessoal" value={student.personalEmail} editable />
          <FieldRow label="E-mail institucional" value={student.institutionalEmail} />
          <FieldRow label="Turma" value={student.className} />
          <FieldRow label="Pai" value={student.father} />
          <FieldRow label="Mãe" value={student.mother} />
          <FieldRow label="Responsável" value={student.guardian} />

          <div className="mt-4 flex items-center gap-2 text-sm text-slate-700">
            <FileText size={16} />
            <span>Declaração de Matrícula</span>
          </div>
          <button className="mt-2 flex items-center gap-2 text-sm font-semibold text-slate-600 transition-colors hover:text-slate-900">
            <Download size={16} />
            Baixar declaração
          </button>
        </Card>

        <Card title="INFORMAÇÕES DA ESCOLA">
          <div className="mb-4 space-y-2 text-sm text-slate-600">
            <p><span className="font-semibold text-slate-800">INEP:</span> {school.inep}</p>
            <p><span className="font-semibold text-slate-800">Escola:</span> {school.name}</p>
            <p><span className="font-semibold text-slate-800">Endereço:</span> {school.address}</p>
            <div className="flex flex-wrap gap-x-6">
              <p><span className="font-semibold text-slate-800">CEP:</span> {school.cep}</p>
              <p><span className="font-semibold text-slate-800">Telefone:</span> {school.phone}</p>
            </div>
            <p><span className="font-semibold text-slate-800">E-mail:</span> {school.email}</p>
          </div>

          <div className="relative h-48 w-full overflow-hidden rounded-xl border border-slate-200 bg-slate-100">
            <div className="absolute inset-0 opacity-40 bg-[linear-gradient(45deg,theme(colors.slate.200)_25%,transparent_25%,transparent_75%,theme(colors.slate.200)_75%),linear-gradient(45deg,theme(colors.slate.200)_25%,transparent_25%,transparent_75%,theme(colors.slate.200)_75%)] bg-[length:20px_20px] bg-[position:0_0,10px_10px]" />
            <div className="relative flex h-full flex-col items-center justify-center text-slate-400">
              <MapPin size={28} className="mb-1 text-slate-500" />
              <span className="text-xs">Mapa da localização da escola</span>
            </div>
          </div>
        </Card>
      </div>
    </div>
  );
}

function FieldRow({ label, value, editable = false }) {
  return (
    <div className="flex items-center justify-between border-b border-slate-100 py-2 text-sm">
      <div>
        <p className="text-xs text-slate-400">{label}</p>
        <p className="text-slate-700 font-medium">{value}</p>
      </div>
      {editable && (
        <button
          aria-label={`Editar ${label}`}
          className="p-1.5 rounded-full text-slate-400 hover:text-brand-400 hover:bg-brand-50 active:bg-brand-50 transition-colors"
        >
          <Pencil size={14} />
        </button>
      )}
    </div>
  );
}
