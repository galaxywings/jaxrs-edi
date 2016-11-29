<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/customer'}">客户管理</el-breadcrumb-item>
      <el-breadcrumb-item>创建客户</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="customerForm" :rules="rules" ref="customerForm" label-width="120px" >
      <el-form-item label="Code" prop="code">
        <el-input v-model="customerForm.code"></el-input>
      </el-form-item>
      <el-form-item label="Name" prop="name">
        <el-input v-model="customerForm.name"></el-input>
      </el-form-item>
      <el-form-item label="Active" prop="active">
         <el-switch on-text="Yes" off-text="No" v-model="customerForm.active"></el-switch>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click.native="handleSubmit">Submit</el-button>
        <el-button @click.native="handleReset">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import _ from 'lodash'
export default {
  data () {
    let validateCode = _.debounce((rule, value, callback) => {
      this.$http.get('/api/v1/customer/customers/validate-code/', {
        params: {q: value}
      }).then((response) => {
        // use response.body to avoid nested hell
        let data = response.body
        if (data.detail.trim().toUpperCase() === 'OK') {
          callback()
        } else {
          callback(new Error(data.detail))
        }
      }, (response) => {
        this.$notify.error({
          title: response.statusText,
          message: response.body
        })
        callback()
      })
    })
    return {
      customerForm: {
        code: '',
        name: '',
        active: false
      },
      rules: {
        code: [
          { required: true, message: 'Code is required', trigger: 'blur' },
          { min: 3, max: 128, message: 'Length should be 3 to 128', trigger: 'blur' },
          { validator: validateCode, trigger: 'blur' }
        ],
        name: [
          { required: true, message: 'Name is required', trigger: 'blur' },
          { min: 3, max: 128, message: 'Length should be 3 to 128', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleReset () {
      this.$refs.customerForm.resetFields()
    },
    handleSubmit: _.debounce(function (ev) {
      this.$refs.customerForm.validate((valid) => {
        if (!valid) {
          this.$notify.error({
            title: 'Error',
            message: 'Please correct the outstanding error(s)'
          })
          return false
        }
        this.$http.post('/api/v1/customer/customers/',
          this.customerForm).then((response) => {
            let data = response.body
            this.$router.push({name: 'customerCustomerEdit', params: { id: data.id }})
          }, (response) => {
            this.$notify.error({
              title: response.statusText,
              message: response.body.detail
            })
          })
      })
    })
  }
}
</script>
