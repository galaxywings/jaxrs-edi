<template>
  <div>
    <el-form >
      <el-form-item label="Code:">
          {{schema.code}}
      </el-form-item>
      <el-form-item label="名称:">
          {{schema.name}}
      </el-form-item>
    </el-form>
    <el-tabs :active-name="activeName" style="width: 100%">
      <el-tab-pane label="JSON Schema" name="json">
        <ace-editor
          v-model="schema.extraSchemaStr"
          v-if="schema.extraSchemaStr"
          mode="ace/mode/json"
          theme="ace/theme/solarized_light"
          :read-only="true"
          class="ace-editor-xl-screen" >
        </ace-editor>
      </el-tab-pane>
      <el-tab-pane label="Preview" name="preview">
        <json-editor
          v-if="schema.extra_schema"
          :schema="schema.extra_schema"
          :options="editorOptions"
          v-model="extra_params"
          >
        </json-editor>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
export default {
  props: ['id'],
  data () {
    return {
      schema: {},
      activeName: 'json',
      editorOptions: {
        disable_properties: false
      },
      extra_params: {}
    }
  },
  computed: {
    apiEndpoint: function () {
      return `/api/service/schemas/${this.id}/`
    }
  },
  methods: {
  },
  mounted () {
    this.$http.get(this.apiEndpoint).then((response) => {
      let res = response.body
      res.extraSchemaStr = JSON.stringify(res.extra_schema, undefined, '\t')
      this.$set(this, 'schema', res)
    }, (response) => {
      if (response.status === 404) {
        this.$notify.warning({
          title: response.statusText,
          message: 'The service cannot be found'
        })
        this.$router.push({name: 'service.schemas.list'})
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
