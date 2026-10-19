import React from 'react';
import { Inbox } from 'lucide-react';

export default function EmptyState({ message = 'Nenhum registro encontrado' }) {
  return (
    <div className="flex flex-col items-center justify-center text-center py-14 text-slate-500">
      <p className="text-sm mb-4">{message}</p>
      <Inbox size={40} strokeWidth={1.5} />
    </div>
  );
}
