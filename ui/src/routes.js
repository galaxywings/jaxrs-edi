import customerRoutes from './views/customer/routes'
import serviceRoutes from './views/service/routes'
import fileRoutes from './views/file/routes'

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
      {
        path: 'task',
        component: {
          render (c) { return c('router-view') }
        },
        children: [
          {
            path: '',
            name: 'taskIndex',
            redirect: {name: 'taskProcessList'}
          },
          {
            path: 'processes',
            name: 'process.index',
            component: require('./views/process/index')
          },
          {
            path: 'processes/:id/edit',
            name: 'process.edit',
            props: true,
            component: require('./views/process/edit')
          },
          {
            path: 'processes/create',
            name: 'process.create',
            component: require('./views/process/create')
          }
        ]
      },
      serviceRoutes,
      fileRoutes
    ]
  },
  {
    path: '*',
    redirect: { name: 'misc.404' }
  }
]
