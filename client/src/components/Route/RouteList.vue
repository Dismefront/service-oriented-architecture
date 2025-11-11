<template>
  <div class="route-list">
    <h2>Routes</h2>

    <!-- Filters and controls -->
    <div class="controls">
      <div class="filter-controls">
        <input v-model="searchTerm" placeholder="Search by name" @keyup.enter="searchRoutes" />
        <button @click="searchRoutes">Search</button>
        <button @click="clearSearch">Clear</button>
      </div>

      <div class="sort-controls">
        <label>Sort by:</label>
        <select v-model="sortField">
          <option value="id">ID</option>
          <option value="name">Name</option>
          <option value="creationDate">Creation Date</option>
          <option value="distance">Distance</option>
          <option value="coordinates.x">Coordinates X</option>
          <option value="coordinates.y">Coordinates Y</option>
        </select>

        <select v-model="sortDirection">
          <option value="asc">Ascending</option>
          <option value="desc">Descending</option>
        </select>

        <button @click="applySorting">Apply</button>
      </div>
    </div>

    <!-- Error message -->
    <div v-if="error" class="error">
      {{ error }}
    </div>

    <!-- Loading indicator -->
    <div v-if="loading" class="loading">Loading routes...</div>

    <!-- Routes table -->
    <div v-else>
      <table v-if="routes.length > 0" class="routes-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Distance</th>
            <th>Creation Date</th>
            <th>Coordinates</th>
            <th>From Location</th>
            <th>To Location</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="route in routes" :key="route.id">
            <td>{{ route.id }}</td>
            <td>{{ route.name }}</td>
            <td>{{ route.distance }}</td>
            <td>{{ formatDate(route.creationDate) }}</td>
            <td>X: {{ route.coordinates.x }}, Y: {{ route.coordinates.y }}</td>
            <td v-if="route.from">
              X: {{ route.from.x }}, Y: {{ route.from.y }}, Z: {{ route.from.z }}
            </td>
            <td v-else>N/A</td>
            <td>X: {{ route.to.x }}, Y: {{ route.to.y }}, Z: {{ route.to.z }}</td>
            <td>
              <button @click="viewRoute(route.id)">View</button>
              <button @click="editRoute(route.id)">Edit</button>
              <button @click="deleteRoute(route.id)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>

      <div v-else class="no-routes">No routes found.</div>

      <!-- Pagination -->
      <div class="pagination" v-if="totalPages > 1">
        <button @click="goToPage(currentPage - 1)" :disabled="currentPage === 0">Previous</button>

        <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>

        <button @click="goToPage(currentPage + 1)" :disabled="currentPage === totalPages - 1">
          Next
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { routeService } from '../../api/apiClient'

export default {
  name: 'RouteList',
  data() {
    return {
      routes: [],
      loading: false,
      error: null,

      // Pagination
      currentPage: 0,
      pageSize: 10,
      totalPages: 1,
      totalElements: 0,

      // Filtering
      searchTerm: '',

      // Sorting
      sortField: 'id',
      sortDirection: 'asc',
    }
  },

  mounted() {
    this.loadRoutes()
  },

  methods: {
    // Load routes with current filters/pagination
    async loadRoutes() {
      this.loading = true
      this.error = null

      try {
        let sortParam
        if (this.sortField) {
          sortParam = `${this.sortField}:${this.sortDirection}`
        }

        const response = await routeService.getAllRoutes(this.currentPage, this.pageSize, sortParam)

        this.routes = response
        // In a real implementation, pagination info would come from response headers
        this.totalPages = 1
        this.totalElements = response.length
      } catch (err) {
        this.error = err.message || 'Failed to load routes'
        console.error('Error loading routes:', err)
      } finally {
        this.loading = false
      }
    },

    // Search routes by name
    async searchRoutes() {
      if (!this.searchTerm) {
        this.loadRoutes()
        return
      }

      this.loading = true
      this.error = null

      try {
        const searchResults = await routeService.searchRoutesByName(this.searchTerm)
        this.routes = searchResults
        this.totalPages = 1
        this.totalElements = searchResults.length
        this.currentPage = 0
      } catch (err) {
        this.error = err.message || 'Failed to search routes'
        console.error('Error searching routes:', err)
      } finally {
        this.loading = false
      }
    },

    // Clear search
    clearSearch() {
      this.searchTerm = ''
      this.loadRoutes()
    },

    // Apply sorting
    applySorting() {
      this.currentPage = 0
      this.loadRoutes()
    },

    // Format date for display
    formatDate(dateString) {
      if (!dateString) return 'N/A'
      return new Date(dateString).toLocaleString()
    },

    // Pagination controls
    goToPage(page) {
      if (page >= 0 && page < this.totalPages) {
        this.currentPage = page
        this.loadRoutes()
      }
    },

    // Route actions
    viewRoute(id) {
      if (id) {
        console.log(`View route ${id}`)
        // In a full implementation, this would navigate to a route detail page
      }
    },

    editRoute(id) {
      if (id) {
        console.log(`Edit route ${id}`)
      }
    },

    async deleteRoute(id) {
      if (id && confirm(`Are you sure you want to delete route ${id}?`)) {
        try {
          await routeService.deleteRoute(id)
          // Reload the routes after deletion
          this.loadRoutes()
        } catch (err) {
          this.error = err.message || 'Failed to delete route'
          console.error('Error deleting route:', err)
        }
      }
    },
  },
}
</script>

<style scoped>
.route-list {
  padding: 20px;
}

.controls {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 10px;
}

.filter-controls,
.sort-controls {
  display: flex;
  gap: 10px;
  align-items: center;
}

.filter-controls input {
  padding: 5px;
}

.filter-controls button,
.sort-controls button {
  padding: 5px 10px;
}

.sort-controls select {
  padding: 5px;
}

.error {
  color: red;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid red;
  border-radius: 4px;
}

.loading {
  text-align: center;
  padding: 20px;
}

.routes-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.routes-table th,
.routes-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.routes-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.routes-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.routes-table button {
  margin: 0 2px;
  padding: 3px 6px;
  font-size: 12px;
}

.no-routes {
  text-align: center;
  padding: 20px;
  font-style: italic;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
}

.pagination button {
  padding: 5px 10px;
}

.pagination button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
