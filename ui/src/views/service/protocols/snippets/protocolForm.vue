<template>
  <div>
    <el-form :model="form" :rules="rules" ref="form" label-width="120px"
      @keydown.native="form.errors.clear($event.target.name)"
      >
      <el-form-item label="名称" prop="name"
       :show-message="!!form.errors.get('name')" :error="form.errors.get('name')">
        <el-input v-model="form.name" name="name"></el-input>
      </el-form-item>
      <el-form-item label="发送方" prop="sender"
       :show-message="!!form.errors.get('sender')" :error="form.errors.get('sender')">
       <el-select
         v-model="form.sender"
         filterable
         remote
         placeholder="Customer Code/Name"
         :remote-method="searchSenders"
         :loading="isSendersLoading">
         <el-option
           v-for="item in senders"
           :key="item.id"
           :label="item.name"
           :value="item.id">
         </el-option>
       </el-select>
      </el-form-item>
      <el-form-item label="发送方格式" prop="src_format"
       :show-message="!!form.errors.get('src_format')" :error="form.errors.get('src_format')">
        <el-input v-model="form.src_format" name="src_format"></el-input>
      </el-form-item>
      <el-form-item label="接收方" prop="receiver"
       :show-message="!!form.errors.get('receiver')" :error="form.errors.get('receiver')">
       <el-select
         v-model="form.receiver"
         filterable
         remote
         placeholder="Customer Code/Name"
         :remote-method="searchReceivers"
         :loading="isReceiversLoading">
         <el-option
           v-for="item in receivers"
           :key="item.id"
           :label="item.name"
           :value="item.id">
         </el-option>
       </el-select>
      </el-form-item>
      <el-form-item label="接收方格式" prop="dest_format"
       :show-message="!!form.errors.get('dest_format')" :error="form.errors.get('dest_format')">
         <el-input v-model="form.dest_format" name="dest_format"></el-input>
      </el-form-item>
      <el-form-item label="文件名" prop="filename"
       :show-message="!!form.errors.get('filename')" :error="form.errors.get('filename')">
        <el-input v-model="form.filename" name="filename" v-show="false"></el-input>
        <el-upload
          action="/api/misc/dbfiles/file/"
          type="drag"
          :multiple="false"
          :on-remove="handleUploadRemove"
          :on-success="handleUploadSuccess"
          :on-error="handleUploadError"
          :default-file-list="fileList"
          >
          <i class="el-icon-upload"></i>
          <div class="el-dragger__text">Drop file here or <em>click to upload</em></div>
          <div class="el-upload__tip" slot="tip">Attachment</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="配置类型" prop="extra_schema"
       :show-message="!!form.errors.get('extra_schema')" :error="form.errors.get('extra_schema')">
        <el-select v-model="form.extra_schema" name="extra_schema" @change="handleSchemaChange">
          <el-option v-for="item in schemas" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="额外配置" prop="extra_params">
        <json-editor
          v-if="isExtraSchemaVisible"
          :schema="extra_schema"
          v-model="form.extra_params">
        </json-editor>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit" :disabled="form.errors.any()">提交</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Form from 'src/models/Form'
import Protocol from 'src/models/Protocol'
import _ from 'lodash'
export default {
  props: ['initFormData'],
  data () {
    // console.log(this.initFormData)
    return {
      form: new Form(this.initFormData),
      rules: Protocol.rules,
      schemas: [],
      senders: [],
      isSendersLoading: false,
      receivers: [],
      isReceiversLoading: false,
      extra_schema: {}
    }
  },
  computed: {
    isExtraSchemaVisible () {
      return !_.isEmpty(this.extra_schema)
    },
    fileList () {
      if (!this.form.filename) { return [] }
      return [{
        'name': this.form.filename,
        'url': `/api/misc/dbfiles/download/${this.form.filename}`
      }]
    }
  },
  methods: {
    handleReset () {
      this.$refs.form.resetFields()
    },
    handleSubmit: _.debounce(function () {
      let method = this.form.id ? 'put' : 'post'
      let url = this.form.id ? `/api/service/protocols/${this.form.id}/` : `/api/service/protocols/`
      let action = this.form.id ? '修改' : '添加'
      this.form[method](url)
        .then((response) => {
          this.$notify.success({
            title: '成功',
            message: `协议${action}成功!`
          })
          this.$router.push({name: 'service.protocols.edit', params: { id: response.id }})
        },
        (response) => {
          console.error(response)
          this.$notify.error({
            title: '错误',
            message: `协议${action}失败!`
          })
          this.form.errors.clear()
        })
    }, 500),
    loadServiceSchemas () {
      return this.$http.get('/api/service/schemas/',
        {params: {
          content_type__app_label: 'service',
          content_type__model: 'protocol'
        }})
        .then(({data}) => {
          this.schemas = data.results
        }, (response) => {
          this.$notify.error({
            title: response.statusText,
            message: response.body
          })
        })
    },
    searchCustomers: _.debounce(function (q = '', customerKey = 'sender', id = false) {
      this[`is${_.startCase(customerKey)}sLoading`] = true
      let url = '/api/v1/customer/customers/'
      let params = {q: q}
      if (id) {
        params.id = id
      }
      return this.$http.get(url, {
        params: params
      }).then(({data}) => {
        this[`${customerKey}s`] = data.results
      }, (response) => {
        this.$notify.error({
          title: response.statusText,
          message: response.body
        })
      }).finally(() => {
        this[`is${_.startCase(customerKey)}sLoading`] = false
      })
    }, 500),
    searchSenders (q = '', id = false) {
      return this.searchCustomers(q, 'sender', id)
    },
    searchReceivers (q = '', id = false) {
      return this.searchCustomers(q, 'receiver', id)
    },
    handleSchemaChange () {
      for (let schema of this.schemas) {
        if (schema.id === this.form.extra_schema) {
          this.extra_schema = schema.extra_schema
          this.form.extra_params = {}
          break
        }
      }
    },
    handleUploadRemove (file, fileList) {
      this.form.filename = ''
    },
    handleUploadSuccess (response, file, fileList) {
      console.info(response)
      this.form.filename = response.filename
    },
    handleUploadError (err, response, file) {
      console.info(err, response, file)
      this.$notify.error({
        title: response.statusText,
        message: `${err} ${response}`
      })
    }
  },
  mounted () {
    this.loadServiceSchemas()
      .then(() => {
        this.handleSchemaChange()
      })
    this.searchSenders('', this.form.sender)
    this.searchReceivers('', this.form.receiver)
  }
}
</script>
