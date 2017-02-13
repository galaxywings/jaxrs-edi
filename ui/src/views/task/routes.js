export default {
  path: 'task',
  component: require('../templates/blank'),
  children: [
    {
      path: 'processes',
      meta: {
        name: 'Process设置'
      },
      component: require('./processes/template'),
      children: [
        {
          path: '',
          name: 'task.processes.list',
          meta: {
            name: 'Process列表'
          },
          component: require('./processes/index')
        },
        {
          path: 'create',
          name: 'task.processes.create',
          meta: {
            name: 'Process创建'
          },
          component: require('./processes/create')
        },
        {
          path: ':id/edit',
          name: 'task.processes.edit',
          props: true,
          meta: {
            name: 'Process编辑'
          },
          component: require('./processes/edit')
        }
      ]
    }
  ]
}
