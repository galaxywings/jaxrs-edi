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
    this.$http.get(`/api/service/templates/${this.id}`)
      .then(({data}) => {
        this.initFormData = data
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
