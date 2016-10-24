import index from './pages/index.vue'
import dashboard from './pages/dashboard.vue'
export default [
  {
    path: '/',
    name: 'index',
    component: index
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: dashboard
  }
]
