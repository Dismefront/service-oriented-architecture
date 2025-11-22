<script setup lang="ts"></script>

<template>
  <section class="navigation navbar">
    <div class="routeLink">
      <RouterLink to="/routes">Routes</RouterLink>
    </div>
    <div class="routeLink">
      <RouterLink to="/navigator">Navigator</RouterLink>
    </div>
  </section>
</template>

<style scoped>
.navbar {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background-color: #2c3e50;
  color: white;
}

.routeLink a {
  color: #ffffff;
  text-decoration: none;
  font-weight: bold;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background 0.3s;
}

.routeLink a:hover {
  background: #3a5169;
}
</style>
