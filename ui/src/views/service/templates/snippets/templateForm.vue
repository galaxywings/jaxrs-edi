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
      <el-form-item label="额外配置" prop="extra_params">
        <json-editor ref="editor"
          v-if="isExtraSchemaVisible"
          :schema="extra_schema"
          v-model="form.extra_params"
          >
        </json-editor>
      </el-form-item>
      <el-tabs :active-name="activeName" style="width: 100%">
        <el-tab-pane label="输入模板文件内容" name="input">
          <ace-editor
            v-if="ace_editor_key"
            :key="ace_editor_key"
            v-model="form.file_content"
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
        <el-button @click="handleUpload" :disabled="! form.file_content">上传文件</el-button>
        <el-button type="primary" @click="handleSubmit" :disabled="isSubmitting || ! form.filename">提交</el-button>
        <el-button @click="handleReset">重置</el-button>
        <el-button :disabled="! form.id" @click="show_history = show_history + 1">显示历史模板文件</el-button>
      </el-form-item>
      <template-history
        :key="show_history"
        v-if="form.id && show_history"
        :id="form.id"
        @updateFilename="updateFilename">
      </template-history>
    </el-form>
  </div>
</template>

<script>
import TemplateHistory from './templateHistory'
import _ from 'lodash'
export default {
  props: ['initFormData'],
  data () {
    let validateParams = (rule, value, callback) => {
      if (!this.isExtraSchemaVisible) {
        callback()
        return
      }
      let errors = this.$refs['editor'].validate()
      if (errors.length > 0) {
        callback(new Error('Json Editor 格式错误.'))
      } else {
        callback()
      }
    }
    return {
      form: this.initFormData,
      schemas: [],
      extra_schema: {},
      extra_params_invlaid: false,
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
      },
      rules: {
        name: [
          { required: true, message: '请输入模板名称', trigger: 'blur' },
          { min: 3, message: '长度至少3个字符', trigger: 'blur' }
        ],
        filename: [
          { required: true, message: '请输入模板名称', trigger: 'blur' },
          { min: 6, max: 64, message: '长度至少6个字符, 最大64个字符', trigger: 'blur' }
        ],
        extra_schema: [
          { type: 'number', required: true, message: '请选择配置', trigger: 'blur' }
        ],
        extra_params: [
          { validator: validateParams, required: true, trigger: 'blur' }
        ]
      },
      show_history: 0,
      ace_editor_key: 0
    }
  },
  components: {
    TemplateHistory
  },
  computed: {
    isExtraSchemaVisible: function () {
      return !_.isEmpty(this.extra_schema)
    }
  },
  methods: {
    updateFilename (val) {
      this.form.filename = val
      this.$http.get(`/api/misc/dbfiles/view/${val}`)
        .then((content) => {
          this.form.file_content = content.body
          this.ace_editor_key += 1
        }, (res) => {
          let message = `读取${val}文件内容失败.`
          this.$notify.error({
            title: res.statusText,
            message: message
          })
        })
    },
    handleUpload () {
      let method = 'post'
      let url = '/api/misc/dbfiles/text/'
      this.file.content = window.encodeURIComponent(this.form.file_content)
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
    console.log(this.form)
    this.loadServiceSchemas()
      .then(() => {
        this.handleSchemaChange(false)
        this.ace_editor_key += 1
      })
  }
}
</script>

<style scoped>
div.ace-editor-xl-screen {
  height: 300px;
}
</style>
