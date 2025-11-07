import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory('/'),
  routes: [
    {
      path: '/',
      name: 'routes',
      component: () => import('../views/Route/RoutesView.vue')
    },
    {
      path: '/navigator',
      name: 'navigator',
      component: () => import('../views/Navigator/NavigatorView.vue')
    }
  ],
})

export default router