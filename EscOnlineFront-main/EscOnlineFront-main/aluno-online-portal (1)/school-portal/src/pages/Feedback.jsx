import React, { useState } from 'react';
import { Send, CheckCircle2 } from 'lucide-react';
import Button from '../components/Button';

export default function Feedback() {
  const [message, setMessage] = useState('');
  const [sent, setSent] = useState(false);

  function handleSubmit(e) {
    e.preventDefault();
    if (!message.trim()) return;
    setSent(true);
    setMessage('');
  }

  return (
    <div className="space-y-6">
      <div>
        <p className="text-xs text-slate-400">Início / Críticas ou Sugestões</p>
        <h2 className="text-2xl font-extrabold text-brand-900">Críticas ou Sugestões</h2>
      </div>

      <div className="grid gap-6 lg:grid-cols-2">
        <div className="bg-white rounded-xl2 shadow-card p-6">
          <h3 className="font-bold text-slate-700 mb-2">Canal do Aluno</h3>
          <p className="text-sm text-slate-500 mb-4">
            Use este canal para críticas, sugestões, elogios ou relatos de problemas sobre a
            plataforma. Inclua o máximo de detalhes possível e, se necessário, anexe imagens.
          </p>

          {sent && (
            <div className="flex items-center gap-2 bg-emerald-50 text-emerald-700 text-sm rounded-lg px-4 py-3 mb-4">
              <CheckCircle2 size={18} />
              Mensagem enviada com sucesso! Obrigado pelo retorno.
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label htmlFor="feedback" className="block text-xs font-semibold text-slate-500 mb-1">
                Sua mensagem
              </label>
              <textarea
                id="feedback"
                rows={5}
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Escreva sua crítica ou sugestão aqui..."
                className="w-full rounded-lg border border-slate-300 px-4 py-3 text-sm outline-none resize-none transition-colors focus:border-brand-400 focus:ring-2 focus:ring-brand-400/30"
                required
              />
            </div>
            <Button type="submit" variant="primary" icon={Send}>
              Enviar mensagem
            </Button>
          </form>
        </div>

        <div className="bg-brand-900 text-white rounded-xl2 shadow-card p-6 flex flex-col justify-center">
          <h3 className="font-bold text-lg mb-2">Precisa de outro tipo de ajuda?</h3>
          <p className="text-sm text-white/70">
            Para questões relacionadas a notas, frequência ou documentos, entre em contato
            diretamente com a secretaria da sua escola através dos canais de atendimento
            disponíveis na página inicial.
          </p>
        </div>
      </div>
    </div>
  );
}
