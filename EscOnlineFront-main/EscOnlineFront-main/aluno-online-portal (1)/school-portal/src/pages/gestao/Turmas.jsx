import React, { useEffect, useState } from 'react';
import { Plus, ArrowUpRight } from 'lucide-react';
import PageHeader from '../../components/PageHeader';
import StatusBadge from '../../components/StatusBadge';
import FooterStats from '../../components/FooterStats';
import Modal from '../../components/Modal';
import { TURMAS, getCurso, getAlunosDaTurma } from '../../data/SchoolData';
import { DEFAULT_FOOTER_STATS } from '../../data/mockData';
import { criarTurma, listarTurmas } from '../../services/cadastros';

function mapearTurma(turma) {
  return {
    id: turma.idTurma,
    nome: turma.nome,
    ano: turma.anoSerie,
    cursoId: '',
    professores: [],
    status: 'Cadastrada',
    dadosDoBackend: true,
  };
}

export default function Turmas() {
  const [turmas, setTurmas] = useState(TURMAS);
  const [anoFiltro, setAnoFiltro] = useState('Todos');
  const [modalAberto, setModalAberto] = useState(false);
  const [nome, setNome] = useState('');
  const [anoSerie, setAnoSerie] = useState('');
  const [salvando, setSalvando] = useState(false);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState('');
  const [erroCarregamento, setErroCarregamento] = useState('');

  useEffect(() => {
    let ativo = true;
    listarTurmas()
      .then((lista) => {
        if (ativo) setTurmas(lista.map(mapearTurma));
      })
      .catch((error) => {
        if (ativo) setErroCarregamento(error.message || 'Não foi possível carregar as turmas do backend.');
      })
      .finally(() => {
        if (ativo) setCarregando(false);
      });
    return () => { ativo = false; };
  }, []);

  const anos = [...new Set(turmas.map((turma) => turma.ano))];
  const turmasFiltradas = anoFiltro === 'Todos' ? turmas : turmas.filter((turma) => turma.ano === anoFiltro);

  const salvarTurma = async (event) => {
    event.preventDefault();
    setErro('');
    setSalvando(true);
    try {
      const turmaCriada = await criarTurma({ nome, anoSerie });
      setTurmas((atuais) => [mapearTurma(turmaCriada), ...atuais]);
      setAnoFiltro('Todos');
      setNome('');
      setAnoSerie('');
      setModalAberto(false);
    } catch (error) {
      setErro(error.message || 'Não foi possível cadastrar a turma.');
    } finally {
      setSalvando(false);
    }
  };

  return (
    <>
      <PageHeader
        eyebrow="Ensino Médio / Turmas"
        title="Turmas"
        description="Consulte e cadastre turmas disponíveis para matrículas."
        actionLabel="Adicionar turma"
        actionIcon={Plus}
        onAction={() => { setErro(''); setModalAberto(true); }}
      />

      {carregando && <p role="status" className="mb-4 text-sm text-slate-500">Carregando turmas do backend...</p>}
      {erroCarregamento && <p role="alert" className="mb-4 rounded-lg bg-rose-50 px-4 py-3 text-sm text-rose-700">Não foi possível carregar a lista da API. Os dados demonstrativos continuam visíveis. {erroCarregamento}</p>}

      <div className="mb-6 inline-flex flex-wrap gap-1.5 rounded-lg bg-surface-muted p-1">
        {['Todos', ...anos].map((ano) => (
          <button
            key={ano}
            onClick={() => setAnoFiltro(ano)}
            className={`rounded-md px-3.5 py-2 text-sm font-medium transition-colors ${
              anoFiltro === ano ? 'bg-white text-brand-navy shadow-card' : 'text-slate-500 hover:text-slate-700'
            }`}
          >
            {ano}
          </button>
        ))}
      </div>

      <section className="rounded-2xl border border-slate-200 bg-surface-card p-5 shadow-card sm:p-6">
        <div className="mb-5">
          <p className="text-[11px] font-semibold uppercase tracking-wide text-brand-mid">Visão geral</p>
          <h2 className="mt-1 font-display text-xl font-semibold text-brand-navy">
            Turmas cadastradas · {turmasFiltradas.length}
          </h2>
        </div>

        <div className="hidden overflow-x-auto md:block">
          <table className="w-full min-w-[640px] border-collapse text-sm">
            <thead>
              <tr className="border-b border-slate-100 text-left text-[11px] font-semibold uppercase tracking-wide text-slate-400">
                <th className="pb-3 pr-4">Turma</th>
                <th className="pb-3 pr-4">Ano · Curso</th>
                <th className="pb-3 pr-4">Professor(es)</th>
                <th className="pb-3 pr-4">Alunos</th>
                <th className="pb-3 pr-4">Status</th>
                <th className="pb-3">Ação</th>
              </tr>
            </thead>
            <tbody>
              {turmasFiltradas.map((turma) => {
                const curso = getCurso(turma.cursoId);
                const alunos = turma.dadosDoBackend ? null : getAlunosDaTurma(turma.id);
                return (
                  <tr key={turma.id} className="border-b border-slate-50 last:border-0 hover:bg-slate-50/60">
                    <td className="py-3.5 pr-4">
                      <p className="font-medium text-slate-800">{turma.nome}</p>
                      <p className="text-xs text-slate-400">{turma.dadosDoBackend ? 'Registro do backend' : 'Atualizado há 2 horas'}</p>
                    </td>
                    <td className="py-3.5 pr-4 text-slate-500">
                      {turma.ano} · {curso?.nome ?? '—'}
                    </td>
                    <td className="py-3.5 pr-4 text-slate-500">{turma.professores?.join(', ') || '—'}</td>
                    <td className="py-3.5 pr-4 text-slate-500">{alunos === null ? 'Não informado' : `${alunos.length} alunos`}</td>
                    <td className="py-3.5 pr-4">
                      <StatusBadge status={turma.status} />
                    </td>
                    <td className="py-3.5">
                      <button
                        onClick={() => alert(`Detalhes de ${turma.nome} — em breve.`)}
                        className="inline-flex items-center gap-1 text-sm font-semibold text-brand-action hover:underline"
                      >
                        Abrir
                        <ArrowUpRight className="h-3.5 w-3.5" />
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>

        <ul className="space-y-3 md:hidden">
          {turmasFiltradas.map((turma) => {
            const curso = getCurso(turma.cursoId);
            const alunos = turma.dadosDoBackend ? null : getAlunosDaTurma(turma.id);
            return (
              <li key={turma.id} className="rounded-xl border border-slate-100 p-4">
                <div className="mb-2 flex items-start justify-between gap-2">
                  <p className="font-medium text-slate-800">{turma.nome}</p>
                  <StatusBadge status={turma.status} />
                </div>
                <p className="text-xs text-slate-500">{turma.ano} · {curso?.nome ?? '—'}</p>
                <p className="text-xs text-slate-500">{turma.professores?.join(', ') || '—'} · {alunos === null ? 'Alunos não informados' : `${alunos.length} alunos`}</p>
                <button
                  onClick={() => alert(`Detalhes de ${turma.nome} — em breve.`)}
                  className="mt-3 inline-flex items-center gap-1 text-sm font-semibold text-brand-action"
                >
                  Abrir
                  <ArrowUpRight className="h-3.5 w-3.5" />
                </button>
              </li>
            );
          })}
        </ul>
      </section>

      <FooterStats items={DEFAULT_FOOTER_STATS} />

      <Modal open={modalAberto} onClose={() => !salvando && setModalAberto(false)}>
        <form onSubmit={salvarTurma} className="space-y-4 p-6">
          <div>
            <h2 className="font-display text-xl font-semibold text-brand-navy">Cadastrar turma</h2>
            <p className="mt-1 text-sm text-slate-500">A turma ficará disponível para matrículas de alunos.</p>
          </div>
          <label className="block">
            <span className="mb-1.5 block text-xs font-semibold text-slate-500">Nome da turma *</span>
            <input required maxLength={100} value={nome} onChange={(event) => setNome(event.target.value)} className="input" />
          </label>
          <label className="block">
            <span className="mb-1.5 block text-xs font-semibold text-slate-500">Ano / série *</span>
            <input required maxLength={50} value={anoSerie} onChange={(event) => setAnoSerie(event.target.value)} className="input" />
          </label>
          {erro && <p role="alert" className="text-sm font-medium text-rose-600">{erro}</p>}
          <div className="flex justify-end gap-2">
            <button type="button" disabled={salvando} onClick={() => setModalAberto(false)} className="rounded-lg border border-slate-200 px-4 py-2.5 text-sm font-semibold text-slate-600 disabled:opacity-50">
              Cancelar
            </button>
            <button type="submit" disabled={salvando} className="rounded-lg bg-brand-action px-4 py-2.5 text-sm font-semibold text-white disabled:opacity-50">
              {salvando ? 'Salvando...' : 'Salvar turma'}
            </button>
          </div>
        </form>
      </Modal>
    </>
  );
}
