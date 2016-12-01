
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
            component: require('./pages/modules/customer/customer_list')
          },
          {
            path: 'customers/:id/edit',
            name: 'customerCustomerEdit',
            component: require('./pages/modules/customer/customer_edit')
          },
          {
            path: 'customers/new',
            name: 'customerCustomerNew',
            component: require('./pages/modules/customer/customer_new')
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
            component: require('./pages/modules/task/service_list')
          },
          {
            path: 'processes',
            name: 'taskProcessList',
            component: require('./pages/modules/task/process_list')
          },
          {
            path: 'processes/:id/edit',
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
            // here default is the default component within nested not root
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
