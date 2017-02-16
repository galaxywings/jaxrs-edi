<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <el-select
          v-model="item.value.object_id"
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
      <el-form :form="item.content_object" label-width="120px">
        <el-form-item label="用户名" prop="username">
          <p v-text="item.content_object.username"></p>
        </el-form-item>
        <el-form-item label="密码" prop="password">
           <p v-text="item.content_object.password"></p>
        </el-form-item>
        <el-form-item label="服务器" prop="host">
           <p v-text="item.content_object.host"></p>
        </el-form-item>
        <el-form-item label="端口" prop="port">
           <p v-text="item.content_object.port"></p>
        </el-form-item>
        <el-form-item label="路径" prop="path">
           <p v-text="item.content_object.path"></p>
        </el-form-item>
        <json-editor ref="editor"
          v-if="item.schema.extra_schema"
          :schema="item.schema.extra_schema"
          v-model="item.content_object.extra_params">
        </json-editor>
      </el-form>
    </div>
  </div>
</template>
<script>
import _ from 'lodash'
import stepMixin from '../mixin'
// import Vue from 'vue'
export default {
  mixins: [stepMixin],
  data () {
    return {
      contentObjects: [],
      isLoading: false,
      displaying: false
    }
  },
  computed: {
    itemsRetrievalEndpoint () {
      return '/api/service/ftps/'
    }
  },
  methods: {
    queryContentObjects: _.debounce(function (q) {
      this.searchContentObjects(q)
    }, 500),
    searchContentObjects (q = '') {
      this.isLoading = true
      let params = {
        extra_schema: this.item.schema.id,
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
      let contentObject = {}
      for (let item of this.contentObjects) {
        if (item.id === selectedId) {
          contentObject = item
          break
        }
      }
      this.item.content_object = contentObject
    }
  },
  mounted () {
    if (this.item.value.object_id) {
      this.contentObjects.push(this.item.content_object)
    } else {
      this.searchContentObjects()
    }
  }
}
</script>
