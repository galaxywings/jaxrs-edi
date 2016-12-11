<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task'}">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task/processes'}">Process管理</el-breadcrumb-item>
      <el-breadcrumb-item>创建Process</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="processForm" :rules="rules" ref="processForm" label-width="120px" >
      <el-form-item label="Customer" prop="customer">
        <el-select
          v-model="processForm.customer"
          filterable
          remote
          placeholder="Customer Code / Name"
          :remote-method="querySearchCustomer"
          :loading="isCustomerLoading">
          <el-option
            v-for="item in customerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Name" prop="name">
        <el-input v-model="processForm.name"></el-input>
      </el-form-item>
      <el-form-item label="Interval" prop="interval">
        <el-input v-model="processForm.interval"></el-input>
      </el-form-item>
      <el-form-item label="Active" prop="active">
         <el-switch on-text="Yes" off-text="No" v-model="processForm.active"></el-switch>
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
    return {
      processForm: {
        customer: null,
        name: '',
        interval: 0,
        active: false
      },
      isCustomerLoading: false,
      customerOptions: [],
      rules: {
        customer: [
          { type: 'integer', min: 1, required: true, message: 'Customer is required', trigger: 'change' }
        ],
        name: [
          { required: true, message: 'Name is required', trigger: 'blur' },
          { min: 3, max: 128, message: 'Length should be 3 to 128', trigger: 'blur' }
        ],
        interval: [
          { type: 'integer', min: 60, message: 'Interval better greater than 60 secs', trigger: 'input' }
        ]
      }
    }
  },
  methods: {
    querySearchCustomer: _.debounce(function (queryString) {
      this.searchCustomers(queryString)
    }, 500),
    handleReset () {
      this.$refs.processForm.resetFields()
    },
    searchCustomers (q) {
      this.isCustomerLoading = true
      this.$http.get('/api/v1/customer/customers/', {
        params: {q: q}
      }).then((response) => {
        // use response.body to avoid nested hell
        let result = response.body.results
        // this.$set(this, 'customerOptions', result)
        this.customerOptions = result
        // console.info('customerOptions', this.customerOptions)
      }, (response) => {
        this.$notify.error({
          title: response.statusText,
          message: response.body
        })
      }).finally(() => {
        this.isCustomerLoading = false
      })
    },
    handleSubmit: _.debounce(function (ev) {
      this.$refs.processForm.validate((valid) => {
        if (!valid) {
          this.$notify.error({
            title: 'Error',
            message: 'Please correct the outstanding error(s)'
          })
          return false
        }
        this.$http.post('/api/v1/task/processes/', this.processForm)
          .then((response) => {
            let data = response.body
            this.$router.push({name: 'taskProcessEdit', params: { id: data.id }})
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
    this.searchCustomers('')
  }
}
</script>
