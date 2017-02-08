<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task'}">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task/processes'}">Process管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑Process</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="processForm" :rules="rules" ref="processForm" label-width="120px" >
      <el-form-item label="客户" prop="customer">
        <el-select
          v-model="processForm.customer"
          filterable
          :remote="true"
          placeholder="客户代码或名称"
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
      <el-form-item label="工作流程名称" prop="name">
        <el-input v-model="processForm.name"></el-input>
      </el-form-item>
      <el-form-item label="间隔" prop="interval">
        <el-input v-model="processForm.interval"></el-input>
      </el-form-item>
      <el-form-item label="已激活" prop="active">
         <el-switch on-text="Yes" off-text="No" v-model="processForm.active"></el-switch>
      </el-form-item>
      <steps :process.sync="processForm"></steps>
      <el-form-item>
        <el-button type="primary" @click.native="handleSubmit">Submit</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import _ from 'lodash'
import Steps from './snippets/steps.vue'
export default {
  data () {
    return {
      processForm: {
        customer: null,
        name: '',
        interval: 0,
        active: false,
        steps: []
      },
      oldProcess: {},
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
  computed: {
    apiEndpoint () {
      return `/api/task/processes/${this.$route.params.id}/`
    }
  },
  components: {
    Steps
  },
  methods: {
    querySearchCustomer: _.debounce(function (queryString) {
      this.searchCustomers(queryString)
    }, 500),
    searchCustomers (q = '', id = false) {
      this.isCustomerLoading = true
      let url = '/api/v1/customer/customers/'
      if (id) {
        url = `${url}/${id}/`
      }
      this.$http.get(url, {
        params: {q: q}
      }).then((response) => {
        // use response.body to avoid nested hell
        let result = response.body.results
        this.customerOptions = result
      }, (response) => {
        this.$notify.error({
          title: response.statusText,
          message: response.body
        })
      }).finally(() => {
        this.isCustomerLoading = false
      })
    },
    handleSubmit (ev) {
//      this.$refs.processForm.validate((valid) => {
//        console.log('validate', arguments)
//        if (!valid) {
//          this.$notify.error({
//            title: 'Error',
//            message: 'Please correct the outstanding error(s)'
//          })
//          return false
//        }
      if (this.processForm.steps.length === 0) {
        this.$notify.error({
          title: 'Error',
          message: 'At least one step is required'
        })
        return false
      }

//      let params = this.processForm
      let processRequest = this.$http.put(
          this.apiEndpoint,
          this.processForm
      )
//      params = this.processForm.steps
      let stepsRequest = []
      for (let index of this.processForm.steps.keys()) {
        console.log(index)
        console.log(this.processForm.steps[index])
        let step = this.processForm.steps[index]
        stepsRequest[index] = this.$http.post(
          '/api/task/steps/',
          {
            process: this.$route.params.id,
            content_type: step.content_type_id,
            seq: index + 1,
            object_id: step.object_id
          }
        )
      }
//      let stepsRequest = this.$http.post(
//          `${this.apiEndpoint}save-steps/`,
//          this.processForm.steps
//      )
      Promise.all([processRequest, ...stepsRequest])
        .then((response) => {
          this.fetchProcess()
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body.detail
          })
        })
//      })
    },
    fetchProcess () {
      this.$http.get(this.apiEndpoint).then((response) => {
        let data = response.body
        for (let property in data) {
          this.processForm[property] = data[property]
        }
        this.searchCustomers('', this.processForm.cutomer)
        this.oldProcess = _.cloneDeep(this.processForm)
      }, (response) => {
        if (response.status === 404) {
          this.$notify.warning({
            title: response.statusText,
            message: 'The process cannot be found'
          })
          this.$router.push({name: 'process.index'})
        }
      })
    }
  },
  mounted () {
    this.fetchProcess()
  }
}
</script>

