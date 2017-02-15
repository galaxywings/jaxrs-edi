<template>
  <div>
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
    <div v-show="item.value.object_id">
      <table>
        <tr>
          <th>FTP地址:</th>
          <td>{{item.content_object.host}} : {{item.content_object.port}}</td>
        </tr>
        <tr>
          <th>FTP路径:</th>
          <td> {{item.content_object.path}}</td>
        </tr>
        <tr>
          <th>用户名:</th>
          <td>{{item.content_object.username}}</td>
        </tr>
        <tr>
          <th>密码:</th>
          <td>{{item.content_object.password}}</td>
        </tr>

        <!-- <tr v-for="(value,key) in ftp.extra_params">
          <th> {{key}}: </th>
          <td> {{value}} </td>
        </tr> -->
      </table>

    </div>
  </div>
</template>
<script>
import _ from 'lodash'

export default {
  props: {
    item: {
      type: Object,
      require: true
    }
  },
  data () {
    return {
      contentObjects: [],
      isLoading: false
    }
  },
  watch: {

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
      for (let item of this.items) {
        if (item.id === selectedId) {
          contentObject = item
          break
        }
      }
      this.item.content_object = contentObject
      console.info('select change')
    }
  },
  mounted () {
    if (this.item.value.object_id) {
      this.contentObjects.push(this.item.content_object)
    } else {
      this.searchFtps()
    }
  }
}
</script>
