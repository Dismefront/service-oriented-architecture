// Utility functions for formatting data

export function formatDate(dateString) {
  if (!dateString) return 'N/A';
  const date = new Date(dateString);
  return date.toLocaleString();
}

export function formatCoordinates(coordinates) {
  if (!coordinates) return 'N/A';
  return `X: ${coordinates.x}, Y: ${coordinates.y}`;
}

export function formatLocation(location, label) {
  if (!location) return `${label}: N/A`;
  
  const parts = [];
  if (location.x !== undefined) parts.push(`X: ${location.x}`);
  if (location.y !== undefined) parts.push(`Y: ${location.y}`);
  if (location.z !== undefined) parts.push(`Z: ${location.z}`);
  
  return `${label}: ${parts.join(', ') || 'N/A'}`;
}

export function formatRouteForDisplay(route) {
  if (!route) return 'No route data';
  
  return `
    Route ID: ${route.id || 'N/A'}
    Name: ${route.name || 'N/A'}
    Distance: ${route.distance || 'N/A'}
    Creation Date: ${formatDate(route.creationDate)}
    Coordinates: ${formatCoordinates(route.coordinates)}
    From: ${formatLocation(route.from, 'Location')}
    To: ${formatLocation(route.to, 'Location')}
  `.trim();
}