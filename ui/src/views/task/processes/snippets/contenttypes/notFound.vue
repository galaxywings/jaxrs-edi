<template>
  <step-template
    :step="item.value"
    :schema="item.schema"
    :contentObject="item.content_object"
    :contentType="item.content_type">
    Unknown Step<br/>
    content_type: {{item.value.content_type}}<br/>
    object_id: {{item.value.object_id}}<br/>
  </step-template>
</template>
<script>
import StepTemplate from '../stepTemplate'
import StepMixin from './mixin'
export default {
  mixins: [StepMixin],
  components: {
    StepTemplate
  }
}
</script>
