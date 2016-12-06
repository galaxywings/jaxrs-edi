<template>
  <div ref="editorHolder"></div>
</template>

<script>
  // import 'brace'
  import JSONEditor from 'json-editor'
  import _ from 'lodash'
  // default is html
  JSONEditor.defaults.options.theme = 'html'
  // JSONEditor.plugins.ace.theme = 'twilight'

  let defaultOptions = {
    // Disable additional properties
    no_additional_properties: true,
    display_required_only: true
  }
  /*
  * output event:
  *   v-on:value-change(jsonValue) (may need a Currying function for index)
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
          this.$emit('value-change', value)
        }
      }, 500))
    }
  }
</script>
<style scoped>

</style>
