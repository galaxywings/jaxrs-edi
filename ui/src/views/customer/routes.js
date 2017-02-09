export default {
  path: 'customer',
  component: require('../templates/blank'),
  children: [
    {
      path: 'customers',
      meta: {
        name: '客户管理'
      },
      component: require('./customers/template'),
      children: [
        {
          path: '',
          name: 'customer.customers.list',
          meta: {
            name: '客户列表'
          },
          component: require('./customers/index')
        },
        {
          path: ':id/edit',
          name: 'customer.customers.edit',
          props: true,
          meta: {
            name: '客户编辑'
          },
          component: require('./customers/edit')
        },
        {
          path: 'create',
          name: 'customer.customers.create',
          meta: {
            name: '客户创建'
          },
          component: require('./customers/create')
        }
      ]
    }
  ]
}
