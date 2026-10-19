import React from 'react';

export default function Button({
  children,
  variant = 'primary',
  size = 'md',
  icon: Icon,
  className = '',
  ...props
}) {
  const base =
    'inline-flex items-center justify-center gap-2 font-semibold rounded-full transition-all duration-150 disabled:opacity-50 disabled:cursor-not-allowed select-none';

  const variants = {
    primary:
      'bg-brand-400 text-white shadow-card hover:bg-brand-700 active:bg-brand-900 active:scale-[0.98]',
    outline:
      'border-2 border-brand-400 text-brand-700 bg-white hover:bg-brand-50 active:bg-brand-50 active:scale-[0.98]',
    ghost:
      'text-brand-700 hover:bg-brand-50 active:bg-brand-50/80',
    danger:
      'bg-red-500 text-white hover:bg-red-600 active:bg-red-700 active:scale-[0.98]',
  };

  const sizes = {
    sm: 'text-xs px-3 py-1.5',
    md: 'text-sm px-5 py-2.5',
    lg: 'text-base px-6 py-3',
  };

  return (
    <button
      className={`${base} ${variants[variant]} ${sizes[size]} ${className}`}
      {...props}
    >
      {Icon && <Icon size={size === 'lg' ? 20 : 16} />}
      {children}
    </button>
  );
}
