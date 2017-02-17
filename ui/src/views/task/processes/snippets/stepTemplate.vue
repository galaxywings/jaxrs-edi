<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <el-select
          v-model="step.object_id"
          filterable
          remote
          placeholder="Query String"
          :remote-method="queryContentObjects"
          :loading="isLoading"
          @change="handleChange">
          <el-option
            v-for="contentObject in contentObjects"
            :key="contentObject.id"
            :label="contentObject.name"
            :value="contentObject.id">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-button @click.prevent="displaying = !displaying"
          size="small">
          <i :class="{'el-icon-arrow-down': !displaying,
                      'el-icon-arrow-up': displaying}"></i>
        </el-button>
      </el-col>
    </el-row>
    <div v-show="displaying">
      <slot></slot>
    </div>
  </div>
</template>
<script>
import _ from 'lodash'
export default {
  props: {
    step: {
      type: Object,
      require: true
    },
    schema: {
      type: Object,
      require: true
    },
    contentType: {
      type: Object,
      require: true
    },
    contentObject: {
      type: Object,
      require: true
    }
  },
  data () {
    return {
      contentObjects: [],
      isLoading: false,
      displaying: false
    }
  },
  computed: {
    itemsRetrievalEndpoint () {
      return `/api/service/${this.contentType.model}s/`
    }
  },
  methods: {
    validate (rule, value, callback) {
      // here value = stepItem
      // would be an object of {
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
      if (!value.value.object_id) {
        callback(new Error('请选择相关服务实例'))
      }
      callback()
    },
    save () {
      // default return this step's value
      return new Promise((resolve, reject) => {
        resolve(this.step)
      })
    },
    queryContentObjects: _.debounce(function (q) {
      this.searchContentObjects(q)
    }, 500),
    searchContentObjects (q = '') {
      this.isLoading = true
      let params = {
        extra_schema: this.schema.id,
        q: q
      }
      this.$http.get(this.itemsRetrievalEndpoint, {
        params: params
      }).then(({body}) => {
        this.contentObjects = body.results
      }, (response) => {
        this.$notify.error({
          title: response.statusText,
          message: response.body
        })
      }).finally(() => {
        this.isLoading = false
      })
    },
    handleChange (selectedId) {
      let contentObject = this.contentObjects.find((element) => {
        return element.id === selectedId
      })
      this.$emit('content-change', contentObject)
    }
  },
  mounted () {
    if (this.step.object_id) {
      this.contentObjects.push(this.contentObject)
    } else {
      this.searchContentObjects()
    }
  }
}
</script>
