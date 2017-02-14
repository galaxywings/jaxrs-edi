import customerRoutes from './views/customer/routes'
import serviceRoutes from './views/service/routes'
import fileRoutes from './views/file/routes'
import taskRoutes from './views/task/routes'

export default [
  {
    path: '/',
    component: require('./views/misc/template'),
    children: [
      {
        path: '',
        redirect: 'dashboard'
      },
      {
        path: '/login',
        name: 'login',
        component: require('./views/auth/login')
      },
      {
        path: '/404',
        name: 'misc.404',
        component: require('./views/misc/404')
      },
      {
        path: '/403',
        name: 'misc.403',
        component: require('./views/misc/403')
      }
    ]
  },
  {
    path: '/',
    meta: {
      auth: true
    },
    component: require('./views/templates/base'),
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: require('./views/dashboard/index')
      },
      customerRoutes,
      serviceRoutes,
      fileRoutes,
      taskRoutes
    ]
  },
  {
    path: '*',
    redirect: { name: 'misc.404' }
  }
]
