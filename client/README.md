# Service-Oriented Architecture Client

This is a Vue.js frontend application that provides a complete interface for interacting with the Route Service and Navigator Service APIs.

## Features

- Full CRUD operations for routes
- Sorting, filtering, and pagination of route collections
- Search functionality by route name
- Navigation between locations with custom sorting
- Human-readable data display
- Error handling and user feedback

## Project Structure

```
src/
├── api/                 # API clients and data models
├── components/          # Reusable Vue components
│   └── Route/           # Route-specific components
├── router/              # Vue Router configuration
├── utils/               # Utility functions
└── views/               # Page-level components
    ├── Route/           # Route management views
    └── Navigator/       # Navigation views
```

## API Integration

The client application integrates with two backend services:

1. **Route Service** (`http://localhost:8080/api/v1`)
   - Manages route collections
   - Provides CRUD operations
   - Supports sorting, filtering, and pagination

2. **Navigator Service** (`http://localhost:8081/navigator`)
   - Finds routes between locations
   - Adds new routes between locations

## Development Setup

```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build
```

## Usage

1. **Route Management**
   - View all routes with pagination
   - Sort routes by any field (ID, name, distance, etc.)
   - Search routes by name
   - Create, update, and delete routes

2. **Navigation**
   - Find routes between specific locations
   - Add new routes between locations
   - Sort navigation results

## Error Handling

The application provides comprehensive error handling:
- Network errors
- API validation errors
- User feedback for all operations

## Data Display

All data is presented in human-readable formats:
- Tables for collections
- Detailed views for individual items
- Formatted dates and coordinates