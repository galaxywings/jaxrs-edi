<template>
  <div>
    <protocol-form :init-form-data="initFormData" v-if="initFormData"></protocol-form>
  </div>
</template>

<script>
import ProtocolForm from './snippets/protocolForm'
export default {
  props: ['id'],
  data () {
    return {
      initFormData: ''
    }
  },
  components: {
    ProtocolForm
  },
  mounted () {
    this.$loading()
    this.$http.get(`/api/service/protocols/${this.id}`)
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
