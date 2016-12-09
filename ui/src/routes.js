
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
        path: 'customer',
        meta: {
          auth: true
        },
        component: {
          render (c) { return c('router-view') }
        },
        children: [
          {
            path: '',
            name: 'customerIndex',
            redirect: { name: 'customerCustomerList' }
          },
          {
            path: 'customers',
            name: 'customerCustomerList',
            component: require('./pages/modules/customer/customer-list')
          },
          {
            path: 'customers/:id/edit',
            name: 'customerCustomerEdit',
            component: require('./pages/modules/customer/customer-edit')
          },
          {
            path: 'customers/new',
            name: 'customerCustomerNew',
            component: require('./pages/modules/customer/customer-new')
          }
        ]
      },
      {
        path: 'task',
        meta: {
          auth: true
        },
        // create a container component
        // please refer to https://github.com/vuejs/vue-router/issues/745
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
            component: require('./pages/modules/task/service-list')
          },
          {
            path: 'services/:id/edit',
            name: 'taskServiceEdit',
            component: require('./pages/modules/task/service-edit')
          },
          {
            path: 'processes',
            name: 'taskProcessList',
            component: require('./pages/modules/task/process-list')
          },
          {
            path: 'processes/:id/edit',
            name: 'taskProcessEdit',
            component: require('./pages/modules/task/process-edit')
          },
          {
            path: 'processes/new',
            name: 'taskProcessNew',
            component: require('./pages/modules/task/process-new')
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
