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
      // Customer
      {
        path: 'customers',
        component: require('./views/customers/template'),
        children: [
          {
            path: '/',
            name: 'customers.list',
            component: require('./views/customers/index')
          },
          {
            path: ':id/edit',
            name: 'customers.edit',
            component: require('./views/customers/edit')
          },
          {
            path: 'create',
            name: 'customers.create',
            component: require('./views/customers/create')
          }
        ]
      },
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
            path: 'services',
            name: 'taskServiceList',
            component: require('./views/modules/task/service-list')
          },
          {
            path: 'services/:id/edit',
            name: 'taskServiceEdit',
            component: require('./views/modules/task/service-edit')
          },
          {
            path: 'processes',
            name: 'taskProcessList',
            component: require('./views/modules/task/process-list')
          },
          {
            path: 'processes/:id/edit',
            name: 'taskProcessEdit',
            component: require('./views/modules/task/process-edit')
          },
          {
            path: 'processes/new',
            name: 'taskProcessNew',
            component: require('./views/modules/task/process-new')
          }
        ]
      }
    ]
  },
  {
    path: '*',
    redirect: { name: 'misc.404' }
  }
]
