<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task'}">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task/services'}">Service管理</el-breadcrumb-item>
      <el-breadcrumb-item>Service编辑</el-breadcrumb-item>
    </el-breadcrumb>

    <el-tabs :active-name="activeName" style="width: 100%">
      <el-tab-pane label="JSON Schema" name="schema">
        <ace-editor
          v-model="service.paramsSchemaStr"
          v-if="service.paramsSchemaStr"
          mode="ace/mode/json"
          theme="ace/theme/solarized_light"
          :read-only="true"
          class="ace-editor-xl-screen" >
        </ace-editor>
      </el-tab-pane>
      <el-tab-pane label="Preview" name="preview">
        <json-editor
          v-if="service.params_schema"
          :schema="service.params_schema"
          :options="editorOptions"
          @value-change="valueChange"
          @invalid="valueInvalid">
        </json-editor>
      </el-tab-pane>
    </el-tabs>


  </div>
</template>
<script>
export default {
  data () {
    return {
      service: {},
      oldService: {},
      activeName: 'schema',
      editorOptions: {
        disable_properties: false
      }
    }
  },
  computed: {
    apiEndpoint: function () {
      return `/api/task/services/${this.$route.params.id}/`
    }
  },
  methods: {
    valueChange (value) {
      // this.$notify.success({
      //   title: 'valueChange',
      //   message: value
      // })
    },
    valueInvalid (errors) {
      this.$notify.error({
        title: 'valueInvalid',
        message: errors
      })
    }
  },
  mounted () {
    this.$http.get(this.apiEndpoint).then((response) => {
      let res = response.body
      res.paramsSchemaStr = JSON.stringify(res.params_schema, undefined, '\t')
      this.$set(this, 'service', res)
      this.oldService = res
    }, (response) => {
      if (response.status === 404) {
        this.$notify.warning({
          title: response.statusText,
          message: 'The service cannot be found'
        })
        this.$router.push({name: 'taskServiceList'})
      }
    })
  }
}
</script>
<style scoped>
div.ace-editor-xl-screen {
  height: 450px;
}
</style>
