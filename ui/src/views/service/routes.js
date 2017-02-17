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
    },
    {
      path: 'protocols',
      meta: {
        name: '协议设置'
      },
      component: require('./protocols/template'),
      children: [
        {
          path: '',
          name: 'service.protocols.list',
          meta: {
            name: '协议列表'
          },
          component: require('./protocols/index')
        },
        {
          path: 'create',
          name: 'service.protocols.create',
          meta: {
            name: '协议创建'
          },
          component: require('./protocols/create')
        },
        {
          path: ':id/edit',
          name: 'service.protocols.edit',
          props: true,
          meta: {
            name: '协议编辑'
          },
          component: require('./protocols/edit')
        }
      ]
    },
    {
      path: 'templates',
      meta: {
        name: '模板设置'
      },
      component: require('./templates/template'),
      children: [
        {
          path: '',
          name: 'service.templates.list',
          meta: {
            name: '模板列表'
          },
          component: require('./templates/index')
        },
        {
          path: 'create',
          name: 'service.templates.create',
          meta: {
            name: '模板创建'
          },
          component: require('./templates/create')
        },
        {
          path: ':id/edit',
          name: 'service.templates.edit',
          props: true,
          meta: {
            name: '模板编辑'
          },
          component: require('./templates/edit')
        }
      ]
    }
  ]
}
