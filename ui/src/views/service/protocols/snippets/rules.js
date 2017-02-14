export default {
  name: [
    { required: true, message: '请输入名称', trigger: 'blur' },
    { min: 3, max: 128, message: '长度3~128个字符', trigger: 'blur' }
  ],
  sender: [
    { type: 'number', required: true, message: '请选择发送方', trigger: 'blur' }
  ],
  src_format: [
    { required: true, message: '请输入发送格式', trigger: 'blur' },
    { max: 16, message: '长度最长为16个字符', trigger: 'blur' }
  ],
  receiver: [
    { type: 'number', required: true, message: '请选择接收方', trigger: 'blur' }
  ],
  dest_format: [
    { required: true, message: '请输入接收格式', trigger: 'blur' },
    { max: 16, message: '长度最长为16个字符', trigger: 'blur' }
  ],
  filename: [
    { required: true, message: '请设置附件', trigger: 'change' },
    { min: 3, max: 128, message: '长度3~128个字符', trigger: 'change' }
  ],
  extra_schema: [
    { type: 'number', required: true, message: '请选择配置', trigger: 'blur' }
  ]
}
