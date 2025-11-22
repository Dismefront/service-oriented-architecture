import { createRouter, createWebHistory } from 'vue-router';
import RoutePage from '@/pages/RoutePage.vue';
import NavigatorPage from '@/pages/NavigatorPage.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/routes',
      name: 'routes',
      component: RoutePage,
    },
    {
      path: '/navigator',
      name: 'navigator',
      component: NavigatorPage,
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'notFound',
      redirect: 'routes',
    },
  ],
});

export default router;
