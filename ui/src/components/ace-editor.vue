<template>
  <div ref="wrapper"
    class="brace-editor-default"
    :class="['brace-editor-dimension']"></div>
</template>

<script>
  import ace from 'brace'
  export default {
    name: 'ace-editor',
    props: {
      mode: {
        type: String,
        default: 'ace/mode/javascript'
      },
      theme: {
        type: String,
        default: 'ace/theme/monokai'
      },
      fontSize: {
        type: Number,
        default: 12
      },
      readOnly: {
        type: Boolean,
        default: false
      },
      value: { // for v-model to work value seems to be reserved keyword
        type: String
      }
    },
    mounted () {
      // since `import` could be only used as top level import
      // let vm = this
      // require.ensure([], function () {
      //   require(vm.mode)
      //   require(vm.theme)
      // })
      // require(this.theme)
      // only support this two for the time being
      require('brace/mode/javascript')
      require('brace/mode/json')
      require('brace/theme/monokai')
      require('brace/theme/solarized_light')
      // let req_ctx = require.context("../../node_modules/brace", true, /^\.\/.*\.js$/);
      // require(vm.mode.slice(6))
      // require(vm.theme.slice(6))
      // debug to see if this.$el any access to class option

      let editor = ace.edit(this.$el)
      let session = editor.getSession()
      editor.$blockScrolling = Infinity // disable some warning message...
      session.setMode(this.mode)
      editor.setTheme(this.theme)
      editor.setFontSize(this.fontSize)
      editor.setReadOnly(this.readOnly)
      editor.setValue(this.value)
      editor.on('change', () => {
        let value = editor.getValue()
        // https://vuejs.org/v2/guide/components.html#Form-Input-Components-using-Custom-Events
        this.$emit('input', value)
      })
    }
  }
</script>
<style scoped>
.brace-editor-default {
  /*position: absolute;*/
}
.brace-editor-dimension {
  min-width: 100%;
  min-height: 100px;
}
</style>
