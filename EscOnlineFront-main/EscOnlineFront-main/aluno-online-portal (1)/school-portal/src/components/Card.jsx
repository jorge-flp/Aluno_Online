import React from 'react';

export default function Card({ title, children, className = '', headerClassName = '' }) {
  return (
    <div
      className={`bg-white rounded-xl2 border border-slate-100 shadow-card overflow-hidden flex flex-col ${className}`}
    >
      {title && (
        <div
          className={`px-5 py-4 border-b border-slate-100 text-sm font-bold text-slate-700 tracking-wide ${headerClassName}`}
        >
          {title}
        </div>
      )}
      <div className="p-5 flex-1">{children}</div>
    </div>
  );
}
