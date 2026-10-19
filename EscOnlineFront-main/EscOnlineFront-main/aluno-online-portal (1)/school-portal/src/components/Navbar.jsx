import React from 'react';
import { Menu } from 'lucide-react';

export default function Navbar({ title, onMenuClick }) {
  return (
    <header className="sticky top-0 z-20 bg-white border-b border-slate-100 flex items-center gap-3 px-4 py-4 md:px-8">
      <button
        onClick={onMenuClick}
        className="md:hidden rounded-lg p-1.5 hover:bg-slate-100 text-slate-600 transition-colors"
        aria-label="Abrir menu"
      >
        <Menu size={22} />
      </button>
      <h1 className="text-lg font-bold text-slate-800">{title}</h1>
    </header>
  );
}
