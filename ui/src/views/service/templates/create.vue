<template>
  <div>
    <template-form :init-form-data="initFormData"></template-form>
  </div>
</template>

<script>
import TemplateForm from './snippets/templateForm'
export default {
  data () {
    return {
      initFormData: {
        name: '',
        filename: '',
        extra_params: '',
        extra_schema: ''
      }
    }
  },
  components: {
    TemplateForm
  }
}
</script>
