<template>
  <div ref="editorHolder"></div>
</template>

<script>
  // import 'brace'

  // Since we make this `json-editor` as a CommonJS Module, we can do it like below now
  import JSONEditor from 'json-editor'
  import _ from 'lodash'

  // let JSONEditor = require('exports?JSONEditor!json-editor')
  // import 'json-editor'
  // let JSONEditor = window.JSONEditor

  // import JSONEditor from 'json-editor'
  // console.info('JSONEditor', JSONEditor)

  // default is html
  JSONEditor.defaults.options.theme = 'bootstrap3'
  // JSONEditor.plugins.ace.theme = 'twilight'

  let defaultOptions = {
    // Disable additional properties
    no_additional_properties: true,
    display_required_only: false,
    required_by_default: true,
    disable_properties: true,
    disable_edit_json: true
  }
  /*
  * output event:
  *   v-on:value-change(jsonValue) (plz do watch in the parent component)
  *   v-on:invalid(errors)
  */
  export default {
    name: 'json-editor',
    props: {
      schema: {
        type: Object,
        required: true
      },
      options: {
        type: Object,
        default: function () {
          return {}
        }
      },
      value: {
        type: Object,
        required: true
      }
    },
    data () {
      return {
        editor: null
      }
    },
    watch: {
      value: _.debounce(function (val) {
        this.editor.setValue(val)
      }, 500)
    },
    mounted () {
      let opts = _.assignIn({schema: this.schema}, defaultOptions, this.options)
      let editor = this.editor = new JSONEditor(this.$el, opts)

      editor.setValue(this.value)

      editor.on('change', _.debounce(() => {
        // Get an array of errors from the validator
        let errors = editor.validate()
        if (errors.length > 0) {
          this.$emit('invalid', errors)
        } else {
          let value = editor.getValue()
          this.$emit('input', value)
        }
      }, 500))
    },
    beforeDestroy () {
      // let's try beforeDestroy first if anything goes wrong then destroyed
      this.editor.destroy()
    }
  }
</script>
<style scoped>

</style>
