import React, { useState } from 'react';
import { GraduationCap, Presentation, Heart, Building2, ChevronRight, ChevronLeft } from 'lucide-react';
import Button from '../components/Button';

const roles = [
  {
    key: 'aluno',
    label: 'Aluno',
    icon: GraduationCap,
    classes: 'border-[#b67ef9] text-[#7c3aed] hover:bg-[#b67ef9] hover:border-[#b67ef9] hover:text-white',
    ring: 'focus:ring-[#b67ef9]/30 focus:border-[#b67ef9]',
    button: 'bg-[#b67ef9] hover:bg-[#a563f2]',
  },
  {
    key: 'professor',
    label: 'Professor',
    icon: Presentation,
    classes: 'border-[#4ba3f7] text-[#2f7dd6] hover:bg-[#4ba3f7] hover:border-[#4ba3f7] hover:text-white',
    ring: 'focus:ring-[#4ba3f7]/30 focus:border-[#4ba3f7]',
    button: 'bg-[#4ba3f7] hover:bg-[#358de0]',
  },
  {
    key: 'responsavel',
    label: 'Responsável',
    icon: Heart,
    classes: 'border-[#00c46c] text-[#049a57] hover:bg-[#00c46c] hover:border-[#00c46c] hover:text-white',
    ring: 'focus:ring-[#00c46c]/30 focus:border-[#00c46c]',
    button: 'bg-[#00c46c] hover:bg-[#00ad5f]',
  },
  {
    key: 'gestao',
    label: 'Gestão escolar',
    icon: Building2,
    classes: 'border-[#eeb318] text-[#b8880c] hover:bg-[#eeb318] hover:border-[#eeb318] hover:text-white',
    ring: 'focus:ring-[#eeb318]/30 focus:border-[#eeb318]',
    button: 'bg-[#eeb318] hover:bg-[#d9a30f]',
  },
];

export default function Login({ onLogin }) {
  const [selectedRole, setSelectedRole] = useState(null);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  function handleSubmit(e) {
    e.preventDefault();
    onLogin(selectedRole);
  }

  const role = roles.find((r) => r.key === selectedRole);

  return (
    <div
      className="min-h-screen flex items-center justify-center p-4"
      style={{
        backgroundColor: '#f7f7fb',
        backgroundImage:
          'linear-gradient(to right, rgba(26,18,53,0.12) 1px, transparent 1px), linear-gradient(to bottom, rgba(26,18,53,0.12) 1px, transparent 1px)',
        backgroundSize: '24px 24px',
      }}
    >
      <div className="w-full max-w-md">
        <div className="flex items-center gap-2 mb-6 px-1">
          <GraduationCap size={22} className="text-slate-700" />
          <span className="font-bold text-slate-800">ESC Online</span>
        </div>

        <div className="bg-white rounded-xl2 shadow-card-hover p-7 sm:p-8">
          {!selectedRole ? (
            <>
              <h1 className="text-xl font-bold text-slate-800 mb-1">Quem está acessando?</h1>
              <p className="text-sm text-slate-400 mb-6">Escolha seu perfil pra continuar.</p>

              <div className="space-y-2.5">
                {roles.map(({ key, label, icon: Icon, classes }) => (
                  <button
                    key={key}
                    onClick={() => setSelectedRole(key)}
                    className={`w-full flex items-center justify-between gap-3 bg-white border-2 rounded-xl px-4 py-3.5 font-semibold text-sm transition-colors duration-150 ${classes}`}
                  >
                    <span className="flex items-center gap-3 whitespace-normal text-left">
                      <Icon size={18} className="shrink-0" />
                      {label}
                    </span>
                    <ChevronRight size={16} className="shrink-0 opacity-60" />
                  </button>
                ))}
              </div>
            </>
          ) : (
            <form onSubmit={handleSubmit} className="space-y-4">
              <button
                type="button"
                onClick={() => setSelectedRole(null)}
                className="flex items-center gap-1 text-xs font-semibold text-slate-400 hover:text-slate-600 mb-1"
              >
                <ChevronLeft size={14} />
                Voltar
              </button>

              <div>
                <h1 className="text-xl font-bold text-slate-800">Entrar como {role.label.toLowerCase()}</h1>
                <p className="text-sm text-slate-400 mt-0.5">Digite seus dados de acesso.</p>
              </div>

              <div>
                <label className="block text-xs font-semibold text-slate-500 mb-1" htmlFor="username">
                  Usuário ou matrícula
                </label>
                <input
                  id="username"
                  type="text"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  className={`w-full rounded-lg border border-slate-300 px-4 py-2.5 text-sm outline-none transition-colors focus:ring-2 ${role.ring}`}
                  placeholder="Digite sua matrícula"
                  required
                />
              </div>

              <div>
                <label className="block text-xs font-semibold text-slate-500 mb-1" htmlFor="password">
                  Senha
                </label>
                <input
                  id="password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className={`w-full rounded-lg border border-slate-300 px-4 py-2.5 text-sm outline-none transition-colors focus:ring-2 ${role.ring}`}
                  placeholder="••••••••"
                  required
                />
              </div>

              <button
                type="submit"
                className={`w-full text-white font-semibold py-2.5 rounded-lg transition-colors ${role.button}`}
              >
                Entrar
              </button>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}
