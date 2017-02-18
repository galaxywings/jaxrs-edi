<template>
  <step-template
    :step="item.value"
    :schema="item.schema"
    :contentType="item.content_type"
    :contentObject="item.content_object"
    @content-change="handleContentChange">
    <el-form :form="item.content_object" label-width="120px">
      <el-form-item label="发送方" prop="sender">
        <p v-text="item.content_object.sender"></p>
      </el-form-item>
      <el-form-item label="发送方格式" prop="src_format">
         <p v-text="item.content_object.src_format"></p>
      </el-form-item>
      <el-form-item label="接收方" prop="receiver">
         <p v-text="item.content_object.receiver"></p>
      </el-form-item>
      <el-form-item label="接收方格式" prop="dest_format">
         <p v-text="item.content_object.dest_format"></p>
      </el-form-item>
      <el-form-item label="附件" prop="filename">
         <p v-text="item.content_object.filename"></p>
      </el-form-item>
      <json-editor ref="editor"
        v-if="item.schema.extra_schema"
        :schema="item.schema.extra_schema"
        v-model="item.content_object.extra_params">
      </json-editor>
      <a v-bind:href="file_url">下载附件</a>
    </el-form>
  </step-template>
</template>
<script>

import StepTemplate from '../stepTemplate'
import StepMixin from './mixin'
export default {
  mixins: [StepMixin],
  components: {
    StepTemplate
  },
  computed: {
    file_url: function () {
      return `/api/misc/dbfiles/download/${this.item.content_object.filename}`
    }
  }
}
</script>
