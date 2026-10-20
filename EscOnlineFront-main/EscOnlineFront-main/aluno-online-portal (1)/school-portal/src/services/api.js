const API_URL = import.meta.env.VITE_API_URL?.replace(/\/+$/, '');

export async function apiFetch(endpoint, options = {}) {
  if (!API_URL) {
    throw new Error('Configure VITE_API_URL com a URL base do backend antes de usar os cadastros.');
  }

  const headers = new Headers(options.headers);
  const isFormData = typeof FormData !== 'undefined' && options.body instanceof FormData;
  if (options.body && !isFormData && !headers.has('Content-Type')) {
    headers.set('Content-Type', 'application/json');
  }

  const response = await fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers,
  });
  const responseText = await response.text();

  if (!response.ok) {
    let message = responseText;
    try {
      const errorBody = JSON.parse(responseText);
      message = errorBody.message || errorBody.error || errorBody.title || responseText;
      if (errorBody.errors && Array.isArray(errorBody.errors)) {
        message = errorBody.errors.map((error) => error.defaultMessage || error.message).filter(Boolean).join('; ');
      }
    } catch {
      // Mantém o texto original quando o backend não retorna JSON.
    }
    throw new Error(message || `Erro ${response.status}: ${response.statusText}`);
  }

  if (response.status === 204 || !responseText) return null;

  try {
    return JSON.parse(responseText);
  } catch {
    throw new Error('O backend retornou uma resposta inválida.');
  }
}
