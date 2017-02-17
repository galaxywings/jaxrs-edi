<template>
  <div>
    <template-form :init-form-data="initFormData" v-if="initFormData"></template-form>
  </div>
</template>

<script>
import TemplateForm from './snippets/templateForm'
export default {
  props: ['id'],
  data () {
    return {
      initFormData: ''
    }
  },
  components: {
    TemplateForm
  },
  mounted () {
    this.$loading()
    this.$http.get(`/api/service/templates/${this.id}/`)
      .then(({data}) => {
        this.initFormData = data
        this.$http.get(`/api/misc/dbfiles/view/${this.initFormData.filename}`)
          .then((content) => {
            this.initFormData.file_content = content.body
          }, (res) => {
            let message = '读取文件内容失败.'
            this.$notify.error({
              title: res.statusText,
              message: message
            })
          })
      }, (response) => {
        let msg = 'Empty Response'
        if (response) {
          msg = response.body.detail
        }
        this.$notify.error({
          title: response.statusText,
          message: msg
        })
      }).finally(() => {
        this.$loading().close()
      })
  }
}
</script>
