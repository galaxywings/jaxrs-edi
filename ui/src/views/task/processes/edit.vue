<template>
  <div>
    <el-form :model="form" ref="form" label-width="120px" >
      <el-form-item label="名称" prop="name"
        :rules="[
          { required: true, message: 'Name is required', trigger: 'blur' },
          { min: 3, max: 128, message: 'Length should be 3 to 128', trigger: 'blur' }
        ]">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="间隔" prop="interval"
        :rules="[
          { type: 'integer', min: 60, message: 'Interval better greater than 60 secs', trigger: 'input' }
        ]">
        <el-input v-model="form.interval"></el-input>
      </el-form-item>
      <el-form-item label="激活" prop="active">
         <el-switch on-text="Yes" off-text="No" v-model="form.active"></el-switch>
      </el-form-item>
      <el-form-item
        v-for="(step, index) in form.steps"
        :key="step.key"
        :prop="'steps.'+index"
        :label="'步骤 ' + (index+1)"
        :rules="[
          { validator: checkStep, trigger: 'change' }
        ]"
        >
        <el-row :gutter="20">
          <el-col :span="4">
            <el-tag type="primary" v-text="step.schema.name"></el-tag>
          </el-col>
          <el-col :span="4">
            <el-button type="danger" icon="delete"
              @click.prevent="removeStep(index)"></el-button>
          </el-col>
        </el-row>
        <component :item="step" ref="steps"
          :is="step.content_type.model">
        </component>
      </el-form-item>

      <el-form-item
          label="服务类型">
        <el-select
            v-model="selectedSchemaId"
            filterable
            remote
            :remote-method="querySchemas"
            :loading="isSchemaSearching">
          <el-option
              v-for="(schema,index) in serviceSchemas"
              :key="schema.id"
              :label="schema.name"
              :value="schema.id">
          </el-option>
        </el-select>
        <el-button @click.prevent="addStep()" type="success">添加新步骤</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click.native="handleSubmit">Submit</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import _ from 'lodash'
import Ftp from './snippets/contenttypes/ftp.vue'
import NotFound from './snippets/contenttypes/notFound.vue'
export default {
  props: ['id'],
  data () {
    return {
      form: {
        name: '',
        interval: 0,
        active: false,
        steps: []
      },
      oldProcess: {},
      selectedSchemaId: '',
      serviceSchemas: [],
      isSchemaSearching: false,
      idContentTypeMap: new Map(),
      isSubmitting: false
    }
  },
  computed: {
    processApiEndpoint () {
      return `/api/task/processes/${this.id}/`
    }
  },
  components: {
    Ftp,
    NotFound
  },
  methods: {
    checkStep (rule, value, callback) {
      console.info('check Step', rule.fullField)
      let index = this.form.steps.indexOf(value)
      this.$refs.steps[index].validate(rule, value, callback)
    },
    querySchemas: _.debounce(function (queryString) {
      this.searchSchemas(queryString)
    }, 500),
    searchSchemas (q = '', ids = []) {
      this.isSchemaSearching = true
      let url = '/api/service/schemas/'
      let params = {q: q}
      if (ids.length > 0) {
        params.id__in = ids.join(',')
      }
      return this.$http.get(url, {
        params: params
      }).then(({data}) => {
        this.serviceSchemas = data.results
        return this.serviceSchemas
      }, (response) => {
        this.$notify.error({
          title: response.statusText,
          message: response.body
        })
        return response
      }).finally(() => {
        this.isSchemaSearching = false
      })
    },
    fetchContentTypes () {
      let url = '/api/misc/contenttypes/'
      return this.$http.get(url, {
        params: {app_label: 'service'}
      }).then(({body}) => {
        return body.results
      }, (response) => {
        this.$notify.error({
          title: response.statusText,
          message: response.body
        })
      })
    },
    fetchSteps () {
      let promise = new Promise((resolve, reject) => {
        this.$http.get(`/api/task/steps/detail/`, {
          params: { process: this.id }
        }).then(({body}) => {
          let schemaIdSet = new Set()
          let steps = body.results
          for (let step of steps) {
            schemaIdSet.add(step.content_object.extra_schema)
          }
          this.searchSchemas('', [...schemaIdSet])
            .then((schemas) => {
              resolve({steps: steps, schemas: schemas})
            }, (response) => {
              reject(response)
            })
          return
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body
          })
          reject(response)
        })
      })
      return promise
    },
    fetchProcess () {
      return this.$http.get(this.processApiEndpoint)
        .then(({body}) => {
          return body
        }, (response) => {
          if (response.status === 404) {
            this.$notify.warning({
              title: response.statusText,
              message: 'The process cannot be found'
            })
            this.$router.push({name: 'task.process.index'})
          }
        })
    },
    addStep () {
      let schema = this.serviceSchemas.find((element) => {
        return element.id === this.selectedSchemaId
      })
      let contentType = this.idContentTypeMap.get(schema.content_type)
      let item = {
        key: this.generateStepKey(),
        value: {
          content_type: contentType.id,
          // it's important to be empty
          // to match in step's contentObject selection
          object_id: ''
        },
        schema: schema,
        content_type: contentType,
        content_object: {
          // json editor just need this
          extra_params: {}
        }
      }
      this.form.steps.push(item)
    },
    removeStep (index) {
      this.form.steps.splice(index, 1)
    },
    generateStepKey () {
      // assigning key is important for v-for
      // https://vuejs.org/v2/guide/list.html#key
      // refer to http://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        let r = Math.random() * 16 | 0
        let v = c === 'x' ? r : (r & 0x3 | 0x8)
        return v.toString(16)
      })
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

        Promise.all(this.$refs.steps.map((x) => x.save()))
          .then((steps) => {
            let process = {}
            for (let field in this.form) {
              process[field] = this.form[field]
            }
            process.steps = steps
            this.isSubmitting = true
            this.$http.post(this.processApiEndpoint + 'save/', process)
              .then(({data}) => {
                this.$notify.success({
                  title: '成功',
                  message: `修改成功!`
                })
              },
              (response) => {
                console.error(response)
                this.$notify.error({
                  title: '错误',
                  message: `修改失败!`
                })
              }).finally(() => {
                this.isSubmitting = false
              })
          }, (error) => {
            console.error('Steps saving error', error)
            this.$notify.error({
              title: 'Error',
              message: 'Please correct the outstanding error(s)'
            })
          })
      })
    }, 500)
  },
  mounted () {
    Promise.all([
      this.fetchProcess(),
      this.fetchContentTypes(),
      this.fetchSteps()
    ]).then(([process, contentTypes, {steps, schemas}]) => {
      // DO NOT DO THIS !!!
      // this.form = process

      // using loop to avoid reference error in v-for!!!
      for (let field in process) {
        this.form[field] = process[field]
      }
      // this.form.steps = []
      for (let contentType of contentTypes) {
        this.idContentTypeMap.set(contentType.id, contentType)
      }

      let idSchemaMap = new Map()
      for (let schema of schemas) {
        idSchemaMap.set(schema.id, schema)
      }
      // we will get an item of
      // stepItem = {
      //   "key": "xxx"
      //   "value": {
      //     "seq": 0,
      //     "content_type": 0,
      //     "object_id": 0,
      //     "id": 0, // could be omitted in the ajax
      //     "process": 0, // could omitted in the ajax
      //   },
      //   "schema": {
      //     "code": "xxx"
      //     "name": "xxx",
      //     "extra_schema": {...}
      //   },
      //   "content_type": {
      //     "id": 0,
      //     "app_label": "xxx",
      //     "model": "xxx"
      //   },
      //   "content_object": {...}
      // }
      for (let step of steps) {
        let item = {}
        item.value = step
        item.content_object = step.content_object
        delete item.value.content_object
        item.schema = idSchemaMap.get(item.content_object.extra_schema)
        item.content_type = this.idContentTypeMap.get(item.schema.content_type)
        if (!item.content_type) {
          item.content_type = {model: 'not-found'}
        }
        item.key = this.generateStepKey()
        this.form.steps.push(item)
      }
    })
  }
}
</script>
