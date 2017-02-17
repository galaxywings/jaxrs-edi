<template>
  <step-template
    :step="item.value"
    :schema="item.schema"
    :contentType="item.content_type"
    :contentObject="item.content_object"
    @content-change="handleContentChange">
    <el-form :form="item.content_object" label-width="120px">
      <el-form-item label="用户名" prop="username">
        <p v-text="item.content_object.username"></p>
      </el-form-item>
      <el-form-item label="密码" prop="password">
         <p v-text="item.content_object.password"></p>
      </el-form-item>
      <el-form-item label="服务器" prop="host">
         <p v-text="item.content_object.host"></p>
      </el-form-item>
      <el-form-item label="端口" prop="port">
         <p v-text="item.content_object.port"></p>
      </el-form-item>
      <el-form-item label="路径" prop="path">
         <p v-text="item.content_object.path"></p>
      </el-form-item>
      <json-editor ref="editor"
        v-if="item.schema.extra_schema"
        :schema="item.schema.extra_schema"
        v-model="item.content_object.extra_params">
      </json-editor>
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
  }
}
</script>
