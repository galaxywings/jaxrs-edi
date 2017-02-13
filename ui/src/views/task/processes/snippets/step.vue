<template>
  <div>
    <compoment :is="getContentTypeTemplate(step)"
               v-if="getContentTypeTemplate(step)"
               :step="step"
    ></compoment>
    <!--<json-editor-->
    <!--:schema="step.extra_schema"-->
    <!--:options="editorOptions"-->
    <!--v-model="step.params_value"-->
    <!--@invalid="onStepInvalid(step, $event)">-->
    <!--</json-editor>-->
  </div>
</template>
<script>
  import Ftp from './contenttypes/ftp.vue'
  export default {
    props: {
      step: {
        type: Object,
        required: true
      },
      idContentTypeMap: {
        type: Object,
        required: true
      }
    },
    components: {
      Ftp
    },
    methods: {
      getContentTypeTemplate (step) {
        let contentType = this.idContentTypeMap[step.content_type]
        switch (contentType.model) {
          case 'ftp':
            return 'ftp'
          default:
            return ''
        }
      }
    }
  }
</script>
