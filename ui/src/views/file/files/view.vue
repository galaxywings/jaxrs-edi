<template>
  <div>
    <el-form >
      <el-form-item label="文件名称:">
          {{file.filename}}
      </el-form-item>
    </el-form>
    <el-tabs :active-name="activeName" style="width: 100%">
      <el-tab-pane label="JSON Schema" name="json">
        <ace-editor
          v-model="file.content"
          mode="ace/mode/json"
          theme="ace/theme/solarized_light"
          :read-only="true"
          class="ace-editor-xl-screen" >
        </ace-editor>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
export default {
  props: ['id'],
  data () {
    return {
      file: {},
      activeName: 'json',
      editorOptions: {
        disable_properties: false
      }
    }
  },
  computed: {
    apiEndpoint: function () {
      return `/api/misc/dbfiles/${this.id}/`
    }
  },
  methods: {
  },
  mounted () {
    this.$http.get(this.apiEndpoint).then((response) => {
      let res = response.body
      this.$set(this, 'file', res)
    }, (response) => {
      if (response.status === 404) {
        this.$notify.warning({
          title: response.statusText,
          message: 'The file cannot be found'
        })
        this.$router.push({name: 'file.files.list'})
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
