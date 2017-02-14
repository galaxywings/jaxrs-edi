export default {
  customer: [
    { type: 'number', required: true, message: '请选择客户', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入FTP名称', trigger: 'blur' },
    { min: 3, message: '长度至少3个字符', trigger: 'blur' }
  ],
  username: [
    { required: true, message: '请输入FTP用户名', trigger: 'blur' },
    { min: 1, message: '长度至少为1个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入FTP密码', trigger: 'blur' }
  ],
  host: [
    { required: true, message: '请输入FTP地址', trigger: 'blur' }
  ],
  port: [
    // after debugging, {required: } should also specify `type` if type is not string
    { type: 'number', required: true, message: '请输入FTP端口数字', trigger: 'blur' }
  ],
  path: [
    { required: true, message: '请输入FTP路径', trigger: 'blur' }
  ],
  extra_schema: [
    { type: 'number', required: true, message: '请选择配置', trigger: 'blur' }
  ]
}
