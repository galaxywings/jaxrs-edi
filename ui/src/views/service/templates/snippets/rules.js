export default {
  name: [
    { required: true, message: '请输入模板名称', trigger: 'blur' },
    { min: 3, message: '长度至少3个字符', trigger: 'blur' }
  ],
  extra_schema: [
    { type: 'number', required: true, message: '请选择配置', trigger: 'blur' }
  ]
}
