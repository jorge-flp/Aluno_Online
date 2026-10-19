import React from 'react';
import { ChevronRight } from 'lucide-react';

export default function Breadcrumb({ items, variant = 'light' }) {
  const isDark = variant === 'dark';

  return (
    <nav
      className={`flex items-center gap-1.5 text-xs font-medium mb-4 ${
        isDark ? 'text-white/90' : 'text-slate-400'
      }`}
      aria-label="breadcrumb"
    >
      {items.map((item, i) => {
        const isLast = i === items.length - 1;
        return (
          <span key={item} className="flex items-center gap-1.5">
            {i > 0 && <ChevronRight size={12} className="opacity-60" />}
            <span
              className={
                isLast
                  ? isDark
                    ? 'text-white font-semibold'
                    : 'text-brand-700 font-semibold'
                  : ''
              }
            >
              {item}
            </span>
          </span>
        );
      })}
    </nav>
  );
}
