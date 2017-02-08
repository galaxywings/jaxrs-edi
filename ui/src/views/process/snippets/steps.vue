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
      <el-form-item :label="step.content_object.name">
        <el-button @click.prevent="removeStep(index)" type="danger" size="small">Delete</el-button>
      </el-form-item>

      <step :step="step" :idContentTypeMap="idContentTypeMap"></step>
    </el-form-item>

    <el-form-item
        label="服务类型">
      <el-select
          v-model="selectedSchema"
          :remote="true"
          :remote-method="querySearchService"
          :loading="isSchemaLoading">
        <el-option
            v-for="(item,index) in schemaOptions"
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
        schemaOptions: [],
        required: true,
        selectedSchema: 0
      }
    },
    data () {
      return {
        selectedSchema: null,
        isSchemaLoading: false,
        idContentTypeMap: {}
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
        let schema = this.schemaOptions[this.selectedSchema]
        let step = {
          extra_schema: schema,
          content_object: {},
          content_type: schema.content_type,
          object_id: 0,
          errors: []
        }
        this.assignStepKey(step)
        this.process.steps.push(step)
      },
      fetchSchemas (q = '') {
        this.isSchemaLoading = true
        let url = '/api/v1/service/schemas/'
        return this.$http.get(url, {
          params: {q: q}
        }).then(({data}) => {
          this.schemaOptions = data.results
          return data.results
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body
          })
        }).finally(() => {
          this.isSchemaLoading = false
        })
      },
      fetchContentTypes () {
        let url = '/api/misc/contenttypes/'
        return this.$http.get(url, {
          params: {app_label: 'service'}
        }).then(({data}) => {
          for (let contentType of data.results) {
            this.idContentTypeMap[contentType.id] = contentType
          }
          return data.results
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body
          })
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
        return httpMethod('/api/task/processes/', params)
      },
      fetchSteps () {
        let url = `/api/task/steps/detail/`
        this.$http.get(url, {
          params: { process: this.$route.params.id }
        }).then(({data}) => {
          for (let step of data.results) {
            this.assignStepKey(step)
          }
          this.process.steps = data.results
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body
          })
        })
      }
    },
    mounted () {
      Promise.all([this.fetchSchemas(), this.fetchContentTypes()])
        .then(() => {
          this.fetchSteps()
        })
    }
  }
</script>
