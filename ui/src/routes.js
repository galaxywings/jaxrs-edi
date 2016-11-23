
export default [
  {
    path: '/login',
    name: 'login',
    component: require('./pages/login')
  },
  {
    path: '/about',
    component: require('./pages/about')
  },
  {
    path: '/404',
    name: 'notFound',
    component: require('./pages/404')
  },
  {
    path: '/403',
    name: 'forbidden',
    component: require('./pages/403')
  },
  {
    path: '/',
    name: 'baseView',
    component: require('./pages/base'),
    children: [
      {
        path: '',
        redirect: {name: 'dashboard'}
      },
      {
        path: 'dashboard',
        name: 'dashboard',
        component: require('./pages/modules/dashboard')
      },
      {
        path: 'task',
        meta: {
          auth: true
        },
        children: [
          {
            path: '',
            name: 'taskIndex',
            component: require('./pages/modules/task/service_list')
          },
          {
            path: 'services',
            name: 'taskServiceList',
            component: require('./pages/modules/task/service_list')
          },
          {
            path: 'processes',
            name: 'taskProcessList',
            component: require('./pages/modules/task/process_list')
          },
          {
            path: 'processes/:id',
            name: 'taskProcessEdit',
            component: require('./pages/modules/task/process_edit')
          },
          {
            path: 'processes/new',
            name: 'taskProcessNew',
            component: require('./pages/modules/task/process_new')
          }
        ]
      },
      {
        path: 'nested',
        name: 'nested',
        component: require('./pages/modules/nested/index'),
        children: [
          {
            path: 'test1',
            name: 'test1',
            // here default is the default component within dashboard not root
            component: require('./pages/modules/nested/test1')
          },
          {
            path: 'test2',
            name: 'test2',
            meta: {
              auth: true
            },
            components: {
              test: require('./pages/modules/nested/test2')
            }
          }
        ]

      }
    ]
  },
  {
    path: '*',
    redirect: { name: 'notFound' }
  }
]
