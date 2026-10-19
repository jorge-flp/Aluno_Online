import React from 'react';
import { ExternalLink } from 'lucide-react';
import Button from '../components/Button';
import { partners } from '../data/mockData';

export default function Partners() {
  return (
    <div className="space-y-6">
      <div>
        <p className="text-xs text-slate-400">Início / Parceiros</p>
        <h2 className="text-2xl font-extrabold text-brand-900">Parceiros</h2>
      </div>

      <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3">
        {partners.map((partner) => (
          <div
            key={partner.name}
            className="bg-white rounded-xl2 shadow-card hover:shadow-card-hover transition-shadow overflow-hidden flex flex-col"
          >
            <div className={`h-24 ${partner.color} flex items-center justify-center`}>
              <span className="text-white font-extrabold text-lg drop-shadow-sm text-center px-4">
                {partner.name}
              </span>
            </div>
            <div className="p-5 flex-1 flex flex-col">
              <p className="text-sm text-slate-500 flex-1">{partner.description}</p>
              <Button variant="outline" size="sm" icon={ExternalLink} className="mt-4 self-start">
                Acessar
              </Button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
