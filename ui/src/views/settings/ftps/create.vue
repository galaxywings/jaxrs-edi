<template>
  <div>
    <ftp-form :init-form-data="initFormData"></ftp-form> 
  </div>
</template>

<script>
import FtpForm from './snippets/ftpForm'
export default {
  data () {
    return {
      initFormData: {
        name: '',
        extra_params: '{}',
        username: '',
        password: '',
        host: '',
        port: 21,
        path: '',
        extra_schema: 1
      }
    }
  },
  components: {
    FtpForm
  }
}
</script>
