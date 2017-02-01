<template>
  <div>
    <el-form :model="form" :rules="rules" ref="form" label-width="120px"
      @keydown.native="form.errors.clear($event.target.name)"
      >
      <el-form-item label="名称" prop="name"
       :show-message="!!form.errors.get('name')" :error="form.errors.get('name')">
        <el-input v-model="form.name" name="name"></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="username"
       :show-message="!!form.errors.get('username')" :error="form.errors.get('username')">
        <el-input v-model="form.username" name="username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password"
       :show-message="!!form.errors.get('password')" :error="form.errors.get('password')">
         <el-input v-model="form.password" name="password"></el-input>
      </el-form-item>
      <el-form-item label="服务器" prop="host"
       :show-message="!!form.errors.get('host')" :error="form.errors.get('host')">
         <el-input v-model="form.host" name="host"></el-input>
      </el-form-item>
      <el-form-item label="端口" prop="port"
       :show-message="!!form.errors.get('port')" :error="form.errors.get('port')">
         <el-input v-model="form.port" name="port"></el-input>
      </el-form-item>
      <el-form-item label="路径" prop="path"
       :show-message="!!form.errors.get('path')" :error="form.errors.get('path')">
         <el-input v-model="form.path" name="path"></el-input>
      </el-form-item>
      <el-form-item label="配置" prop="extra_schema"
       :show-message="!!form.errors.get('extra_schema')" :error="form.errors.get('extra_schema')">
        <el-select v-model="form.extra_schema" name="extra_schema">
          <el-option v-for="item in schemas" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit" :disabled="form.errors.any()">提交</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Form from 'src/models/Form'
import Ftp from 'src/models/Ftp'
export default {
  props: ['initFormData'],
  data () {
    console.log(this.initFormData)
    return {
      form: new Form(this.initFormData),
      rules: Ftp.rules,
      schemas: ''
    }
  },
  methods: {
    handleReset () {
      this.$refs.form.resetFields()
    },
    handleSubmit (ev) {
      let method = this.form.id ? 'put' : 'post'
      let url = this.form.id ? `/api/v1/service/ftps/${this.form.id}/` : `/api/v1/service/ftps/`
      let mode = this.form.id ? '修改' : '添加'
      this.form[method](url)
        .then((response) => {
          this.$notify.success({
            title: '成功',
            message: `FTP${mode}成功!`
          })
          this.$router.push({name: 'settings.ftps.edit', params: { id: response.id }})
        },
        ({ body, data }) => {
          this.$notify.error({
            title: '错误',
            message: `FTP${mode}失败!`
          })
        })
    },
    loadServiceSchema () {
      this.$http.get('/api/v1/service/schemas/')
        .then(
          ({data}) => { this.schemas = data.results },
          (error) => console.log(error))
    }
  },
  mounted () {
    this.loadServiceSchema()
  }
}
</script>
