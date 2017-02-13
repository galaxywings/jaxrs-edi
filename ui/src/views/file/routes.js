export default {
  path: 'file',
  component: require('../templates/blank'),
  children: [
    {
      path: 'files',
      meta: {
        name: '映射文件管理'
      },
      component: require('./files/template'),
      children: [
        {
          path: '',
          name: 'file.files.list',
          meta: {
            name: '文件列表'
          },
          component: require('./files/index')
        },
        {
          path: ':id/view',
          name: 'file.files.view',
          props: true,
          meta: {
            name: '文件内容'
          },
          component: require('./files/view')
        }
      ]
    }
  ]
}
