export default {
  rules: {
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
      { required: true, message: '请输入FTP端口', trigger: 'blur' },
      { type: 'number', message: '端口号应为数字', trigger: 'blur' }
    ],
    path: [
      { required: true, message: '请输入FTP路径', trigger: 'blur' }
    ]
  }
}
