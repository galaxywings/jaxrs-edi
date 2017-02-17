export default {
  // methods: {
  //   validateParams (rule, value, callback) {
  //     console.log('now into validaeParams!')
  //     let errors = this.$refs.editor.validate()
  //     console.log('now into validaeParams!')
  //     if (errors.length > 0) {
  //       callback(new Error('Json Editor 错误'))
  //     } else {
  //       callback()
  //     }
  //   }
  // },
  name: [
    { required: true, message: '请输入模板名称', trigger: 'blur' },
    { min: 3, message: '长度至少3个字符', trigger: 'blur' }
  ],
  filename: [
    { required: true, message: '请输入模板名称', trigger: 'blur' },
    { min: 6, max: 64, message: '长度至少6个字符, 最大64个字符', trigger: 'blur' }
  ],
  extra_schema: [
    { type: 'number', required: true, message: '请选择配置', trigger: 'blur' }
  ]
  // extra_params: [
  //   { validator: validateParams, trigger: 'blur' }
  // ]
}
