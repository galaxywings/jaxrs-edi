<template>
  <div>
    <ftp-form :init-form-data="initFormData" v-if="initFormData"></ftp-form> 
  </div>
</template>

<script>
import FtpForm from './snippets/ftpForm'
export default {
  props: ['id'],
  data () {
    return {
      initFormData: ''
    }
  },
  components: {
    FtpForm
  },
  mounted () {
    this.$loading()
    this.$http.get(`/api/v1/service/ftps/${this.id}`)
      .then(({data}) => {
        this.initFormData = data
        this.$loading().close()
      }, (response) => {
      })
  }
}
</script>
