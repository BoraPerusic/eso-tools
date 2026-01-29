import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/DashboardView.vue'),
    },
    {
      path: '/stock',
      name: 'stock',
      component: () => import('../views/DashboardView.vue'), // Re-using dashboard for now
    },
    {
      path: '/orders',
      name: 'orders',
      component: () => import('../views/OrdersView.vue'),
    },
    {
      path: '/returns',
      name: 'returns',
      component: () => import('../views/ReturnsView.vue'),
    },
    {
      path: '/agent',
      name: 'agent',
      component: () => import('../views/AgentView.vue'),
    },
  ],
})

export default router
