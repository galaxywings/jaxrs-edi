export default {
  path: 'service',
  component: require('../templates/blank'),
  children: [
    {
      path: 'schemas',
      meta: {
        name: 'Schemas设置'
      },
      component: require('./schemas/template'),
      children: [
        {
          path: '',
          name: 'service.schemas.list',
          meta: {
            name: 'Schemas列表'
          },
          component: require('./schemas/index')
        },
        {
          path: ':id/edit',
          name: 'service.schemas.edit',
          props: true,
          meta: {
            name: 'Schemas配置'
          },
          component: require('./schemas/edit')
        }
      ]
    },
    {
      path: 'ftps',
      meta: {
        name: 'FTP设置'
      },
      component: require('./ftps/template'),
      children: [
        {
          path: '',
          name: 'service.ftps.list',
          meta: {
            name: 'FTP列表'
          },
          component: require('./ftps/index')
        },
        {
          path: 'create',
          name: 'service.ftps.create',
          meta: {
            name: 'FTP创建'
          },
          component: require('./ftps/create')
        },
        {
          path: ':id/edit',
          name: 'service.ftps.edit',
          props: true,
          meta: {
            name: 'FTP编辑'
          },
          component: require('./ftps/edit')
        }
      ]
    }
  ]
}
