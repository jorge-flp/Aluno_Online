# Aluno Online — Portal (Mock Frontend)

Recriação em React + Vite + Tailwind CSS do layout enviado, com dados fictícios
no lugar de qualquer informação pessoal real presente nos prints originais.

## Como rodar

```bash
npm install
npm run dev
```

Abra http://localhost:5173

## Build de produção

```bash
npm run build
npm run preview
```

## Estrutura

```
src/
  components/   -> Button, Card, Navbar, Sidebar, Modal (reutilizáveis)
  pages/        -> Login, Dashboard, StudentInfo, Partners, Feedback
  data/         -> mockData.js (dados fictícios de demonstração)
  App.jsx       -> orquestra autenticação e navegação (useState, sem router)
  index.css     -> Tailwind + estilos base
```

## Paleta de cores

| Token       | Hex       | Uso                                   |
|-------------|-----------|----------------------------------------|
| brand-900   | `#1e478f` | headers, texto de destaque             |
| brand-700   | `#2250c8` | botões primários, hover forte          |
| brand-500   | `#4a77c6` | elementos secundários                  |
| brand-400   | `#0058f3` | CTAs, ícones ativos, cabeçalhos de card|
| brand-50    | `#eef4ff` | fundos suaves, hover leve              |

> Nota: o hex `#250c8` informado no briefing tem 5 dígitos (inválido em
> CSS). Foi interpretado como `#2250c8`, o valor coerente com a escala de
> azuis fornecida (`#4a77c6`, `#1e478f`, `#0058f3`). Ajuste em
> `tailwind.config.js` se o valor pretendido era outro.

## Dados

Todos os dados de aluno, escola, notícias e boletim em `src/data/mockData.js`
são fictícios — nenhuma informação real de pessoa, matrícula, e-mail ou
endereço dos prints originais foi reproduzida no código.

## Observações de acessibilidade e estados

- Todos os botões têm estados `hover`/`active` (ver `components/Button.jsx`).
- Inputs têm anel de foco visível (`focus:ring-2`) e outline de teclado global
  em `index.css`.
- Menu lateral vira drawer com overlay em telas `< md` (mobile-first).
