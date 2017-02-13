<template>
  <div>
    <el-form :model="form" :rules="rules" ref="form" label-width="120px">
      <el-form-item label="客户" prop="customer">
       <el-select v-model="form.customer" name="extra_schema">
         <el-option v-for="item in customers" :label="item.name" :value="item.id"></el-option>
       </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" name="name"></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" name="username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
         <el-input v-model="form.password" name="password"></el-input>
      </el-form-item>
      <el-form-item label="服务器" prop="host">
         <el-input v-model="form.host" name="host"></el-input>
      </el-form-item>
      <el-form-item label="端口" prop="port">
         <el-input-number v-model="form.port" name="port"
          :controls="false" :max="65535"></el-input>
      </el-form-item>
      <el-form-item label="路径" prop="path">
         <el-input v-model="form.path" name="path"></el-input>
      </el-form-item>
      <el-form-item label="配置类型" prop="extra_schema">
        <el-select v-model="form.extra_schema" name="extra_schema" @change="handleSchemaChange">
          <el-option v-for="item in schemas" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="额外配置" prop="extra_params">
        <json-editor
          v-if="isExtraSchemaVisible"
          :schema="extra_schema"
          v-model="form.extra_params">
        </json-editor>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit" :disabled="isSubmitting">提交</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import rules from './rules'
import _ from 'lodash'
export default {
  props: ['initFormData'],
  data () {
    // console.log(this.initFormData)
    return {
      form: this.initFormData,
      rules: rules,
      schemas: [],
      customers: [],
      extra_schema: {},
      isSubmitting: false
    }
  },
  computed: {
    isExtraSchemaVisible: function () {
      return !_.isEmpty(this.extra_schema)
    }
  },
  methods: {
    handleReset () {
      this.$refs.form.resetFields()
    },
    handleSubmit: _.debounce(function () {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          this.$notify.error({
            title: 'Error',
            message: 'Please correct the outstanding error(s)'
          })
          return false
        }
        let method = this.form.id ? 'put' : 'post'
        let url = this.form.id ? `/api/service/ftps/${this.form.id}/` : `/api/service/ftps/`
        let action = this.form.id ? '修改' : '添加'
        this.isSubmitting = true
        this.$http[method](url, this.form)
          .then(({data}) => {
            this.$notify.success({
              title: '成功',
              message: `协议${action}成功!`
            })
            this.$router.push({name: 'service.ftps.edit',
              params: { id: data.id }})
          },
          (response) => {
            console.error(response)
            this.$notify.error({
              title: '错误',
              message: `协议${action}失败!`
            })
          }).finally(() => {
            this.isSubmitting = false
          })
      })
    }, 500),
    loadServiceSchemas () {
      return this.$http.get('/api/service/schemas/',
        {params: {
          content_type__app_label: 'service',
          content_type__model: 'ftp'
        }})
        .then(({data}) => {
          this.schemas = data.results
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body
          })
        })
    },
    loadCustomers () {
      this.$http.get('/api/customer/customers/',
        {params: {
          active: true
        }})
        .then(({data}) => {
          this.customers = data.results
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body
          })
        })
    },
    handleSchemaChange () {
      for (let schema of this.schemas) {
        if (schema.id === this.form.extra_schema) {
          this.extra_schema = schema.extra_schema
          this.form.extra_params = {}
          break
        }
      }
    }
  },
  mounted () {
    this.loadServiceSchemas()
      .then(() => {
        this.handleSchemaChange()
      })
    this.loadCustomers()
  }
}
</script>
