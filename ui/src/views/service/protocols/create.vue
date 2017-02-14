<template>
  <div>
    <protocol-form :init-form-data="initFormData"></protocol-form>
  </div>
</template>

<script>
import ProtocolForm from './snippets/protocolForm'
export default {
  data () {
    return {
      initFormData: {
        name: '',
        sender: '',
        receiver: '',
        src_format: '',
        dest_format: '',
        filename: '',
        extra_schema: ''
      }
    }
  },
  components: {
    ProtocolForm
  }
}
</script>
