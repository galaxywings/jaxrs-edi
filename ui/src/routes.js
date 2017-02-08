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
      // service
      {
        path: 'service',
        component: require('./views/templates/blank'),
        children: [
          {
            path: 'ftps',
            meta: {
              name: 'FTP设置'
            },
            component: require('./views/service/ftps/template'),
            children: [
              {
                path: '',
                name: 'service.ftps.list',
                meta: {
                  name: 'FTP列表'
                },
                component: require('./views/service/ftps/index')
              },
              {
                path: 'create',
                name: 'service.ftps.create',
                meta: {
                  name: '新建FTP配置'
                },
                component: require('./views/service/ftps/create')
              },
              {
                path: ':id/edit',
                name: 'service.ftps.edit',
                props: true,
                meta: {
                  name: '修改FTP配置'
                },
                component: require('./views/service/ftps/edit')
              }
            ]
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
