<template>
  <div>
    <el-form :model="form" :rules="rules" ref="form" label-width="120px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" name="name"></el-input>
      </el-form-item>
      <el-form-item label="文件名" prop="username">
        <span>{{form.filename}}</span>
      </el-form-item>
      <el-form-item label="配置类型" prop="extra_schema">
        <el-select v-model="form.extra_schema" name="extra_schema" @change="handleSchemaChange">
          <el-option v-for="item in schemas" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="额外配置" prop="extra_params" ref="editor">
        <json-editor
          v-if="isExtraSchemaVisible"
          :schema="extra_schema"
          v-model="form.extra_params"
          >
        </json-editor>
      </el-form-item>
      <el-tabs :active-name="activeName" style="width: 100%">
        <el-tab-pane label="输入模板文件内容" name="input">
          <ace-editor
            v-model="file.content"
            mode="ace/mode/text"
            theme="ace/theme/solarized_light"
            :read-only="false"
            class="ace-editor-xl-screen" >
          </ace-editor>
        </el-tab-pane>
        <el-tab-pane label="上传模板" name="upload">
          <el-form-item label="文件">
            <el-upload
              action="/api/misc/dbfiles/file/"
              type="drag"
              :multiple="false"
              :on-remove="handleUploadRemove"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
              >
              <i class="el-icon-upload"></i>
              <div class="el-dragger__text">Drop file here or <em>click to upload</em></div>
            </el-upload>
          </el-form-item>
        </el-tab-pane>
      </el-tabs>
      <el-form-item>
        <el-button @click="handleUpload" :disabled="! file.content">上传文件</el-button>
        <el-button type="primary" @click="handleSubmit" :disabled="isSubmitting || ! form.filename">提交</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import rules from './rules'
import _ from 'lodash'
export default {
  props: ['initFormData'],
  data () {
    // console.log(this.initFormData)
    return {
      form: this.initFormData,
      rules: rules.data,
      schemas: [],
      extra_schema: {},
      isSubmitting: false,
      activeName: 'input',
      file: {
        // Math.random().toString(36).substring(7) + '.txt', random template file name
        filename: '',
        content: '',
        overwrite: true
      },
      editorOptions: {
        disable_properties: true
      }
    }
  },
  computed: {
    isExtraSchemaVisible: function () {
      return !_.isEmpty(this.extra_schema)
    }
  },
  methods: {
    handleInvalid (errors) {
      console.error(errors)
      this.$notify.error({
        title: 'Json Editor 模板错误',
        message: 'Json Editor 模板错误!'
      })
    },
    handleUpload () {
      let method = 'post'
      let url = '/api/misc/dbfiles/text/'
      this.file.content = window.encodeURIComponent(this.file.content)
      this.$http[method](url, this.file)
        .then(({data}) => {
          this.$notify.success({
            title: '成功',
            message: '模板文件上传成功!'
          })
          this.form.filename = data.filename
        },
        (response) => {
          console.error(response)
          this.$notify.error({
            title: '错误',
            message: '模板文件上传失败!'
          })
        })
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
    },
    handleReset () {
      this.$refs.form.resetFields()
    },
    handleSubmit: _.debounce(function () {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          this.$notify.error({
            title: 'Error',
            message: 'Please correct the outstanding error(s)'
          })
          return false
        }
        let method = this.form.id ? 'put' : 'post'
        let url = this.form.id ? `/api/service/templates/${this.form.id}/` : `/api/service/templates/`
        let action = this.form.id ? '修改' : '添加'
        this.isSubmitting = true
        this.$http[method](url, this.form)
          .then(({data}) => {
            this.$notify.success({
              title: '成功',
              message: `协议${action}成功!`
            })
            this.$router.push({name: 'service.templates.edit',
              params: { id: data.id }})
          },
          (response) => {
            console.error(response)
            this.$notify.error({
              title: '错误',
              message: `协议${action}失败!`
            })
          }).finally(() => {
            this.isSubmitting = false
          })
      })
    }, 500),
    loadServiceSchemas () {
      return this.$http.get('/api/service/schemas/',
        {params: {
          content_type__app_label: 'service',
          content_type__model: 'template'
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
    handleSchemaChange (resetParams = true) {
      for (let schema of this.schemas) {
        if (schema.id === this.form.extra_schema) {
          this.extra_schema = schema.extra_schema
          if (resetParams) {
            this.form.extra_params = {}
          }
          break
        }
      }
    }
  },
  mounted () {
    this.loadServiceSchemas()
      .then(() => {
        this.handleSchemaChange(false)
      })
  }
}
</script>

<style scoped>
div.ace-editor-xl-screen {
  height: 300px;
}
</style>
