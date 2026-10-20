import React from 'react';
import Breadcrumb from '../components/Breadcrumb';
import { news, latestPosts } from '../data/mockData';

export default function Noticias() {
  const allPosts = [...latestPosts, ...news.map((n) => ({ title: n.title, date: n.date }))];

  return (
    <div>
      <Breadcrumb items={['Início', 'Notícias']} />

      <div className="bg-white rounded-xl2 shadow-card overflow-hidden">
        <div className="bg-brand-400 text-white px-5 py-3 font-bold text-sm">Notícias</div>
        <ul className="divide-y divide-slate-100">
          {allPosts.map((post, i) => (
            <li key={i} className="p-5 hover:bg-slate-50 transition-colors">
              <a href="#" className="font-semibold text-brand-700 hover:underline">
                {post.title}
              </a>
              <p className="text-xs text-slate-400 mt-1">{post.date}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
