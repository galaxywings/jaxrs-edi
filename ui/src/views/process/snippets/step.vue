<template>
  <div>
    <compoment :is="getServiceTemplate(step)"
               v-if="getServiceTemplate(step)"
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
  import Ftp from './schemas/ftp.vue'
  export default {
    props: {
      step: {
        type: Object,
        required: true
      }
    },
    components: {
      Ftp
    },
    methods: {
      getServiceTemplate (step) {
        switch (step.service.content_type.model) {
          case 'ftp':
            return 'ftp'
          default:
            return ''
        }
      }
    }
  }
</script>