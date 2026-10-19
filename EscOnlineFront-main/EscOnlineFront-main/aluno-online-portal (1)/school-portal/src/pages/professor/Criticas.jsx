import React from 'react';
import { Paperclip, Mail, Phone, MessageCircle, Instagram } from 'lucide-react';

export default function Criticas() {
  return (
    <div className="grid lg:grid-cols-[1fr_340px] gap-6">
      <div className="bg-white rounded-xl2 shadow-card p-6 md:p-7">
        <h2 className="text-lg font-bold text-slate-800 mb-2">Críticas ou Sugestões</h2>
        <p className="text-sm text-slate-500 leading-relaxed mb-6">
          Use este canal para críticas, sugestões, elogios ou relatos de problemas sobre a plataforma. Inclua o máximo de detalhes possível e, se necessário, anexe imagens.
        </p>

        <div className="space-y-4">
          <div>
            <label className="block text-xs font-semibold text-slate-500 mb-1.5">Assunto</label>
            <select className="w-full rounded-lg border border-slate-200 px-4 py-2.5 text-sm outline-none focus:border-brand-400 focus:ring-2 focus:ring-brand-400/20">
              <option>Selecione um assunto</option>
              <option>Crítica</option>
              <option>Sugestão</option>
              <option>Elogio</option>
              <option>Relato de problema</option>
            </select>
          </div>

          <div>
            <label className="block text-xs font-semibold text-slate-500 mb-1.5">Mensagem</label>
            <textarea
              placeholder="Descreva sua crítica ou sugestão..."
              rows={5}
              className="w-full rounded-lg border border-slate-200 px-4 py-2.5 text-sm outline-none focus:border-brand-400 focus:ring-2 focus:ring-brand-400/20 resize-none"
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-slate-500 mb-1.5">Anexo (opcional)</label>
            <button className="w-full flex items-center justify-center gap-2 border border-dashed border-slate-300 rounded-lg py-3 text-sm text-slate-400 hover:border-brand-400 hover:text-brand-500 transition-colors">
              <Paperclip size={16} />
              Clique para anexar uma imagem
            </button>
          </div>

          <button className="w-full bg-brand-400 hover:bg-brand-500 text-white font-semibold py-3 rounded-lg transition-colors">
            Enviar mensagem
          </button>
        </div>
      </div>

      <div className="space-y-5">
        <div className="bg-white rounded-xl2 shadow-card p-5">
          <h3 className="font-bold text-slate-800 text-sm mb-2">Canal do Professor</h3>
          <p className="text-sm text-slate-500 leading-relaxed">
            Suas mensagens são encaminhadas diretamente à Secretaria da Educação. O retorno chega no seu e-mail institucional em até 5 dias úteis.
          </p>
        </div>

        <div className="bg-white rounded-xl2 shadow-card p-5">
          <h3 className="font-bold text-slate-800 text-sm mb-3">Outros Atendimentos</h3>
          <div className="space-y-3">
            <ContactRow icon={Mail} label="suporte@seduc.ce.gov.br" />
            <ContactRow icon={Phone} label="0800 000 0000" />
            <ContactRow icon={MessageCircle} label="Chat de atendimento" />
            <ContactRow icon={Instagram} label="@seduc.ceara" />
          </div>
        </div>
      </div>
    </div>
  );
}

function ContactRow({ icon: Icon, label }) {
  return (
    <div className="flex items-center gap-2.5 text-sm text-slate-600">
      <Icon size={16} className="text-brand-400 shrink-0" />
      {label}
    </div>
  );
}