<template>
  <div>
    Invalid Step<br/>
    content_type: {{item.value.content_type}}<br/>
    object_id: {{item.value.object_id}}<br/>
  </div>
</template>
<script>
export default {
  props: {
    item: {
      type: Object,
      require: true
    }
  }
}
</script>
