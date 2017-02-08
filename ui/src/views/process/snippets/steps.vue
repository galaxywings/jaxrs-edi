<template>
  <div>
    <el-form-item
        v-for="(step, index) in process.steps"
        :key="step.key"
        :label="'步骤 ' + (index+1)"
        :prop="'steps.' + (index+1)"
        :rules="{
          validator: stepValidator, trigger: 'input'
        }"
    >
      <span></span>
      <el-form-item :label="step.service.name">
        <el-button @click.prevent="removeStep(index)" type="danger" size="small">Delete</el-button>
      </el-form-item>

      <step :step="step" ></step>
    </el-form-item>

    <el-form-item
        label="服务类型">
      <el-select
          v-model="selectedService"
          :remote="true"
          :remote-method="querySearchService"
          :loading="isServiceLoading">
        <el-option
            v-for="(item,index) in serviceOptions"
            :key="item.id"
            :label="item.name"
            :value="index">
        </el-option>
      </el-select>
      <el-button @click.prevent="addStep()" type="success" size="small">添加新步骤</el-button>
    </el-form-item>
  </div>
</template>
<script>
  import Step from './step.vue'
  export default {
    props: {
      process: {
        type: Object,
        serviceOptions: [],
        required: true,
        selectedService: 0
      }
    },
    data () {
      return {
        selectedService: null,
        isServiceLoading: false
      }
    },
    components: {
      Step
    },
    methods: {
      removeStep (index) {
        this.process.steps.splice(index, 1)
      },
      addStep () {
        let service = this.serviceOptions[this.selectedService]
        let step = {
          service: service,
          service_id: service.id,
          content_type_id: service.content_type.id,
          params_value: {},
          errors: []
        }
        let serviceMap = {}
        for (let service of this.serviceOptions) {
          serviceMap[ service.id ] = service
        }
        this.assignStepKey(step)
        this.process.steps.push(step)
      },
      fetchServices (q = '') {
        this.isServiceLoading = true
        let url = '/api/v1/service/schemas/'
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
      onStepInvalid (step, errors) {
        // although we name it `$event` in the template,
        // it seems needs to be `$event` that the value could be passed through
        step.errors = errors
      },
      stepValidator (rule, value, callback) {
//        if (value.errors && value.errors.length > 0) {
//          callback(new Error(''))
//        } else {
//          callback()
//        }
      },
      saveProcessPromise () {
        let params = Object.assign(this.process)
        delete params.steps
        // if (true) { console.info('to be done') }
        let httpMethod = params.id ? this.$http.post : this.$http.put
        return httpMethod('/api/v1/task/processes/', params)
      },
      fetchSteps () {
        let url = `/api/task/steps/`
        this.$http.get(url, {
          params: { process: this.$route.params.id }
        }).then((response) => {
          // use response.body to avoid nested hell
          let result = response.body.results
          this.process.steps = result
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body
          })
        })
      }
    },
    mounted () {
//      this.fetchSteps()
      this.fetchServices()
      this.assignStepsKey(this.process)
    }
  }
</script>