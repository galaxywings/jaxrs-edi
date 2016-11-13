
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
        path: '/dashboard',
        name: 'dashboard',
        component: require('./pages/modules/dashboard')
      }
    ]
  },
  {
    path: '*',
    redirect: { name: 'notFound' }
  }
]
