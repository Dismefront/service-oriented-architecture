<template>
  <div class="navigator">
    <h2>Navigator</h2>

    <!-- Find routes form -->
    <div class="find-routes-form">
      <h3>Find Routes Between Locations</h3>

      <div class="form-group">
        <label for="idFrom">From Location ID:</label>
        <input
          id="idFrom"
          v-model="findForm.idFrom"
          type="number"
          min="1"
          placeholder="Enter from location ID"
        />
      </div>

      <div class="form-group">
        <label for="idTo">To Location ID:</label>
        <input
          id="idTo"
          v-model="findForm.idTo"
          type="number"
          min="1"
          placeholder="Enter to location ID"
        />
      </div>

      <div class="form-group">
        <label for="orderBy">Order By:</label>
        <select id="orderBy" v-model="findForm.orderBy">
          <option value="id">ID</option>
          <option value="name">Name</option>
          <option value="creationDate">Creation Date</option>
          <option value="distance">Distance</option>
          <option value="coordinates.x">Coordinates X</option>
          <option value="coordinates.y">Coordinates Y</option>
          <option value="from.x">From X</option>
          <option value="from.y">From Y</option>
          <option value="from.z">From Z</option>
          <option value="to.x">To X</option>
          <option value="to.y">To Y</option>
          <option value="to.z">To Z</option>
        </select>
      </div>

      <div class="form-group">
        <label for="page">Page:</label>
        <input
          id="page"
          v-model.number="findForm.page"
          type="number"
          min="0"
          placeholder="Page number"
        />
      </div>

      <div class="form-group">
        <label for="size">Size:</label>
        <input
          id="size"
          v-model.number="findForm.size"
          type="number"
          min="1"
          placeholder="Items per page"
        />
      </div>

      <button @click="findRoutes" :disabled="findingRoutes">
        {{ findingRoutes ? 'Finding...' : 'Find Routes' }}
      </button>
    </div>

    <!-- Add route form -->
    <div class="add-route-form">
      <h3>Add New Route</h3>

      <div class="form-group">
        <label for="addIdFrom">From Location ID:</label>
        <input
          id="addIdFrom"
          v-model="addForm.idFrom"
          type="number"
          min="1"
          placeholder="Enter from location ID"
        />
      </div>

      <div class="form-group">
        <label for="addIdTo">To Location ID:</label>
        <input
          id="addIdTo"
          v-model="addForm.idTo"
          type="number"
          min="1"
          placeholder="Enter to location ID"
        />
      </div>

      <div class="form-group">
        <label for="distance">Distance:</label>
        <input
          id="distance"
          v-model.number="addForm.distance"
          type="number"
          min="2"
          placeholder="Enter distance"
        />
      </div>

      <button @click="addRoute" :disabled="addingRoute">
        {{ addingRoute ? 'Adding...' : 'Add Route' }}
      </button>
    </div>

    <!-- Error message -->
    <div v-if="error" class="error">
      {{ error }}
    </div>

    <!-- Results -->
    <div v-if="routes.length > 0" class="results">
      <h3>Found Routes</h3>

      <table class="routes-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Distance</th>
            <th>Creation Date</th>
            <th>Coordinates</th>
            <th>From Location</th>
            <th>To Location</th>
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
          </tr>
        </tbody>
      </table>

      <!-- Pagination for results -->
      <div class="pagination" v-if="totalPages > 1">
        <button @click="goToPage(currentPage - 1)" :disabled="currentPage === 0">Previous</button>

        <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>

        <button @click="goToPage(currentPage + 1)" :disabled="currentPage === totalPages - 1">
          Next
        </button>
      </div>
    </div>

    <div v-else-if="routesLoaded" class="no-results">No routes found for the given criteria.</div>
  </div>
</template>

<script>
import { navigatorService } from '../../api/apiClient'

export default {
  name: 'Navigator',
  data() {
    return {
      findForm: {
        idFrom: '',
        idTo: '',
        orderBy: 'id',
        page: 0,
        size: 10,
      },

      addForm: {
        idFrom: '',
        idTo: '',
        distance: '',
      },

      findingRoutes: false,
      addingRoute: false,
      error: null,

      routes: [],
      routesLoaded: false,
      currentPage: 0,
      totalPages: 1,
      totalElements: 0,
    }
  },

  methods: {
    async findRoutes() {
      // Validate form
      if (!this.findForm.idFrom || !this.findForm.idTo) {
        this.error = 'Please enter both from and to location IDs'
        return
      }

      this.findingRoutes = true
      this.error = null

      try {
        const response = await navigatorService.findRoutesBetweenLocations(
          parseInt(this.findForm.idFrom),
          parseInt(this.findForm.idTo),
          this.findForm.orderBy,
          this.findForm.page,
          this.findForm.size,
        )

        this.routes = response.routes || []
        this.routesLoaded = true
        this.currentPage = response.currentPage || 0
        this.totalPages = response.totalPages || 1
        this.totalElements = response.totalElements || 0
      } catch (err) {
        this.error = err.message || 'Failed to find routes'
        console.error('Error finding routes:', err)
      } finally {
        this.findingRoutes = false
      }
    },

    async addRoute() {
      // Validate form
      if (!this.addForm.idFrom || !this.addForm.idTo || !this.addForm.distance) {
        this.error = 'Please enter all required fields'
        return
      }

      this.addingRoute = true
      this.error = null

      try {
        const response = await navigatorService.addNewRoute(
          parseInt(this.addForm.idFrom),
          parseInt(this.addForm.idTo),
          parseInt(this.addForm.distance),
        )

        // Add the new route to the list
        this.routes.unshift(response)

        // Reset form
        this.addForm.idFrom = ''
        this.addForm.idTo = ''
        this.addForm.distance = ''

        // Show success message
        this.error = 'Route added successfully'
        setTimeout(() => {
          this.error = null
        }, 3000)
      } catch (err) {
        this.error = err.message || 'Failed to add route'
        console.error('Error adding route:', err)
      } finally {
        this.addingRoute = false
      }
    },

    // Format date for display
    formatDate(dateString) {
      if (!dateString) return 'N/A'
      return dateString
    },

    // Pagination controls
    goToPage(page) {
      if (page >= 0 && page < this.totalPages) {
        this.findForm.page = page
        this.findRoutes()
      }
    },
  },
}
</script>

<style scoped>
.navigator {
  padding: 20px;
}

.find-routes-form,
.add-route-form {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #f9f9f9;
}

.find-routes-form h3,
.add-route-form h3 {
  margin-top: 0;
}

.form-group {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.form-group label {
  width: 150px;
  display: inline-block;
}

.form-group input,
.form-group select {
  padding: 5px;
  width: 200px;
}

.form-group button {
  margin-left: 155px;
  padding: 8px 15px;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.error {
  color: red;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid red;
  border-radius: 4px;
}

.results {
  margin-top: 20px;
}

.routes-table {
  width: 100%;
  border-collapse: collapse;
  margin: 10px 0;
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

.no-results {
  text-align: center;
  padding: 20px;
  font-style: italic;
  color: #666;
}
</style>
