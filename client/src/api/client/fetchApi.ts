export function fetchApi(url: string, options: RequestInit = {}) {
  return fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((text) => {
          throw new Error(text || `HTTP error! status: ${response.status}`);
        });
      }

      if (response.headers.get('content-type') === 'application/json' && response.status !== 204) {
        return response.json();
      }
      return null;
    })
    .catch((error) => {
      throw new Error(error.message || 'An unknown error occurred');
    });
}
