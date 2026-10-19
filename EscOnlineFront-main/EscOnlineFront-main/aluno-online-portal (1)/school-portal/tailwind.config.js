export default {
  content: [
    './index.html',
    './src/**/*.{js,jsx}',
    '../../escola-estadual/src/**/*.{js,jsx}',
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          900: 'rgb(var(--accent-900) / <alpha-value>)',
          700: 'rgb(var(--accent-700) / <alpha-value>)',
          500: 'rgb(var(--accent-500) / <alpha-value>)',
          400: 'rgb(var(--accent-400) / <alpha-value>)',
          50: 'rgb(var(--accent-50) / <alpha-value>)',
        },
        status: {
          success: '#00c46c',
          info: '#4ba3f7',
          warning: '#eeb318',
          danger: '#f9281e',
        },
        neutral: {
          bg: '#f3f7f8',
        },
        'brand-navy': '#1e1b4b',
        'brand-action': '#4a77c6',
        'brand-mid': '#6366f1',
        surface: {
          card: '#ffffff',
          muted: '#f1f5f9',
        },
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
        display: ['Inter', 'system-ui', 'sans-serif'],
      },
      boxShadow: {
        card: '0 2px 10px rgba(30, 71, 143, 0.08)',
        'card-hover': '0 6px 20px rgba(18, 48, 68, 0.14)',
      },
      borderRadius: {
        xl2: '1.25rem',
      },
    },
  },
  plugins: [],
};
