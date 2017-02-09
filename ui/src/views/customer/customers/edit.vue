<template>
  <div>
    <!--<el-breadcrumb separator="/" class="breadcrumb-bar">-->
      <!--<el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>-->
      <!--<el-breadcrumb-item :to="{ path: '/customer'}">客户管理</el-breadcrumb-item>-->
      <!--<el-breadcrumb-item>客户编辑</el-breadcrumb-item>-->
    <!--</el-breadcrumb>-->
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
  props: ['id'],
  data () {
    let validateCode = _.debounce((rule, value, callback) => {
      if (value === this.oldCustomer.code) {
        // the same customers is allowed
        callback()
        return
      }
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
        id: 0,
        code: '',
        name: '',
        active: false
      },
      oldCustomer: {},
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
  computed: {
    apiEndpoint: function () {
      return `/api/customer/customers/${this.id}/`
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
        this.$http.put(this.apiEndpoint,
          this.customerForm).then((response) => {
            this.$notify.success({
              title: response.statusText,
              message: 'Update success'
            })
          }, (response) => {
            this.$notify.error({
              title: response.statusText,
              message: response.body.detail
            })
          })
      })
    }, 500)
  },
  mounted () {
    this.$http.get(this.apiEndpoint).then((response) => {
      this.$set(this, 'customerForm', response.body)
      this.oldCustomer = response.body
    }, (response) => {
      if (response.status === 404) {
        this.$notify.warning({
          title: response.statusText,
          message: 'The customers cannot be found'
        })
        this.$router.push({name: 'customer。customers.list'})
      }
    })
  }
}
</script>
