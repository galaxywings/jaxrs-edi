<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task'}">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task/processes'}">Process管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑Process</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="processForm" :rules="rules" ref="processForm" label-width="120px" >
      <el-form-item label="Customer" prop="customer">
        <el-select
          v-model="processForm.customer"
          filterable
          :remote="true"
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
      <el-form-item
        v-for="(step, index) in processForm.steps"
        :key="step.key"
        :label="'Step ' + index"
        :prop="'steps.' + index"
        :rules="{
          validator: stepValidator, trigger: 'input'
        }"
        >
          <el-button @click.prevent="removeStep(index)" type="danger">Delete</el-button>
          <json-editor
            :schema="step.params_schema"
            :options="editorOptions"
            v-model="step.params_value"
            @invalid="onStepInvalid(step, $event)">
          </json-editor>
      </el-form-item>
      <el-form-item
        label="Service Options">
        <el-select
          v-model="selectedService"
          :remote="true"
          placeholder="Service"
          :remote-method="querySearchService"
          :loading="isServiceLoading">
          <el-option
            v-for="item in serviceOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
        <el-button @click.prevent="addStep()">Add</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click.native="handleSubmit">Submit</el-button>
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
        active: false,
        steps: []
      },
      oldProcess: {},
      isCustomerLoading: false,
      customerOptions: [],
      isServiceLoading: false,
      serviceOptions: [],
      selectedService: null,
      editorOptions: {},
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
    apiEndpoint: function () {
      return `/api/task/processes/${this.$route.params.id}/`
    }
  },
  methods: {
    handleReset () {
      this.$refs.processForm.resetFields()
    },
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
    querySearchService: _.debounce(function (queryString) {
      this.searchServices(queryString)
    }, 500),
    searchServices (q = '') {
      this.isServiceLoading = true
      let url = '/api/v1/task/services/'
      return this.$http.get(url, {
        params: {q: q}
      }).then((response) => {
        // use response.body to avoid nested hell
        let result = response.body.results
        this.serviceOptions = result
        return result
      }, (response) => {
        this.$notify.error({
          title: response.statusText,
          message: response.body
        })
      }).finally(() => {
        this.isServiceLoading = false
      })
    },
    assignStepKey (step, force) {
      if (step.key && !force) {
        return
      }
      // refer to http://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript
      step.key = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        let r = Math.random() * 16 | 0
        let v = c === 'x' ? r : (r & 0x3 | 0x8)
        return v.toString(16)
      })
    },
    assignStepsKey (process) {
      let steps = process.steps || []
      for (let step of steps) {
        this.assignStepKey(step)
      }
    },
    assignStepSchema (step, serviceMap) {
      step.params_schema = serviceMap[ step.service ].params_schema
    },
    assignStepsSchema (process, services) {
      let steps = process.steps || []
      let serviceMap = {}
      for (let service of services) {
        serviceMap[ service.id ] = service
      }
      for (let step of steps) {
        this.assignStepSchema(step, serviceMap)
      }
    },
    removeStep (index) {
      this.processForm.steps.splice(index, 1)
    },
    addStep (index) {
      if (this.selectedService === null) {
        this.$notify.warning({
          title: 'Invalid Service',
          message: 'Please select a valid service'
        })
        return
      }
      let step = {
        service: this.selectedService,
        params_value: {},
        errors: [],
        seq: this.processForm.steps.length
      }
      let serviceMap = {}
      for (let service of this.serviceOptions) {
        serviceMap[ service.id ] = service
      }
      this.assignStepSchema(step, serviceMap)
      this.assignStepKey(step)
      this.processForm.steps.push(step)
    },
    onStepInvalid (step, errors) {
      // although we name it `$event` in the template,
      // it seems needs to be `$event` that the value could be passed through
      step.errors = errors
    },
    stepValidator (rule, value, callback) {
      if (value.errors && value.errors.length > 0) {
        callback(new Error(''))
      } else {
        callback()
      }
    },
    saveProcessPromise () {
      let params = _.clone(this.processForm)
      delete params.steps
      // if (true) { console.info('to be done') }
      let httpMethod = params.id ? this.$http.post : this.$http.put
      return httpMethod('/api/v1/task/processes/', params)
    },
    handleSubmit: _.debounce(function (ev) {
      this.$refs.processForm.validate((valid) => {
        console.info('validate', arguments)
        if (!valid) {
          this.$notify.error({
            title: 'Error',
            message: 'Please correct the outstanding error(s)'
          })
          return false
        }
        if (this.processForm.steps.length === 0) {
          this.$notify.error({
            title: 'Error',
            message: 'At least one step is required'
          })
          return false
        }
        let params = _.cloneDeep(this.processForm)
        delete params.steps
        let processRequest = this.$http.put(this.apiEndpoint, params)
        params = this.processForm.steps
        let stepsRequest = this.$http.post(`${this.apiEndpoint}save-steps/`, params)
        Promise.all([processRequest, stepsRequest])
          .then((response) => {
            this.initFetchData()
          }, (response) => {
            this.$notify.error({
              title: response.statusText,
              message: response.body.detail
            })
          })
      })
    }, 500),
    initFetchData () {
      this.searchServices().then((serviceOptions) => {
        this.$http.get(this.apiEndpoint).then((response) => {
          this.processForm = response.body
          this.searchCustomers('', this.processForm.cutomer)
          this.assignStepsSchema(this.processForm, serviceOptions)
          this.assignStepsKey(this.processForm)
          this.oldProcess = _.cloneDeep(this.processForm)
        }, (response) => {
          if (response.status === 404) {
            this.$notify.warning({
              title: response.statusText,
              message: 'The process cannot be found'
            })
            this.$router.push({name: 'taskProcessList'})
          }
        })
      })
    }
  },
  mounted () {
    this.initFetchData()
  }
}
</script>
