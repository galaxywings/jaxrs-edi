
export default [
  {
    path: '/login',
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
            // meta: {
            //   loginRequired: true
            // },
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
