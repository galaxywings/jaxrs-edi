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
      <el-form-item v-for="(step, index) in form.steps"
        :prop="'steps.'+index"
        :label="'步骤 ' + (index+1)"
        :rules="[
          { validator: checkStep, trigger: 'blur' }
        ]"
        >

        <component :item="step" ref="steps"
          :is="step.content_type.model">
        </component>
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
        steps: [] // to be tested if needed
      },
      oldProcess: {},
      serviceSchemas: [],
      isSchemaSearching: false,
      stepItems: [],
      idContentTypeMap: new Map()
    }
  },
  computed: {

  },
  components: {
    Ftp,
    NotFound
  },
  methods: {
    checkStep (rule, value, callback) {
      console.info(rule, value, callback)
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
      return this.$http.get(`/api/task/processes/${this.id}/`)
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
    handleSubmit () {

    }
  },
  mounted () {
    Promise.all([
      this.fetchProcess(),
      this.fetchContentTypes(),
      this.fetchSteps()
    ]).then(([process, contentTypes, {steps, schemas}]) => {
      this.form = process
      this.form.steps = []
      for (let contentType of contentTypes) {
        this.idContentTypeMap.set(contentType.id, contentType)
      }

      let idSchemaMap = new Map()
      for (let schema of schemas) {
        idSchemaMap.set(schema.id, schema)
      }
      // we will get an item of
      // stepItem = {
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
        if (item.schema) {
          item.content_type = this.idContentTypeMap.get(item.schema.content_type)
          if (!item.content_type) {
            item.content_type = {model: 'not-found'}
          }
        } else {
          item.content_type = {model: 'not-found'}
        }
        this.form.steps.push(item)
      }
    })
  }
}
</script>
