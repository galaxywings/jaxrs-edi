<template>
  <div>
    <el-form :model="item.content_object" ref="form" label-width="120px" >
      <el-form-item label="名称" prop="name"
        :rules="[
          { required: true, message: 'Name is required', trigger: 'blur' },
          { min: 3, max: 128, message: 'Length should be 3 to 128', trigger: 'blur' }
        ]">
        <el-input v-model="item.content_object.name"></el-input>
      </el-form-item>
      <el-form-item prop="extra_params"
        :rules="[
          { validator: checkExtraParams, trigger: 'change' }
        ]">
        <json-editor ref="editor"
          :schema="extraSchema"
          :options="editorOptions"
          v-model="item.content_object.extra_params">
        </json-editor>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit" :disabled="isSubmitting">提交</el-button>
      </el-form-item>
  </div>
</template>
<script>
import _ from 'lodash'
export default {
  props: {
    item: {
      // would be an object of {
      //   "key": "xxx"
      //   "value": {
      //     "seq": 0,
      //     "content_type": 0,
      //     "object_id": 0,
      //     "id": 0, // could be omitted in the ajax
      //     "process": 0, // could omitted in the ajax
      //   },
      //   "schema": {
      //     "code": "xxx"
      //     "name": "xxx",
      //     "extra_schema": {...}
      //   },
      //   "content_type": {
      //     "id": 0,
      //     "app_label": "xxx",
      //     "model": "xxx"
      //   },
      //   "content_object": {...}
      // }
      type: Object,
      require: true
    }
  },
  data () {
    return {
      isSubmitting: false,
      editorOptions: {
        collapsed: true
      }
    }
  },
  computed: {
    extraSchema () {
      // avoid polluting existing item.schema from parent
      // and make it collapsed at first for ui
      let result = _.cloneDeep(this.item.schema.extra_schema)
      result['options'] = {
        collapsed: !!this.item.value.object_id
      }
      return result
    }
  },
  methods: {
    checkExtraParams (rule, value, callback) {
      let errors = this.$refs.editor.validate()
      if (errors.length > 0) {
        callback(new Error('Json Editor 格式错误.'))
      } else {
        callback()
      }
    },
    validate (rule, value, callback) {
      this.$refs.form.validate((valid) => {
        if (valid) {
          callback()
        } else {
          callback(new Error('请修复步骤设置中的错误'))
        }
      })
    },
    save () {
      // default return this step's value
      return new Promise((resolve, reject) => {
        this.validate(null, this.item, (error) => {
          if (error) {
            reject(error)
            return
          }
          this.isSubmitting = true
          let method = 'post'
          let apiEndpoint = '/api/service/generics/'
          if (this.item.value.object_id) {
            method = 'put'
            apiEndpoint = `${apiEndpoint}${this.item.value.object_id}/`
          }
          this.item.content_object.extra_schema = this.item.schema.id
          this.$http[method](apiEndpoint,
              this.item.content_object)
          .then(({data}) => {
            this.item.content_object = data
            this.item.value.object_id = data.id
            resolve(this.item.value)
          }, (error) => {
            console.info('inside save', error)
            reject(error)
          }).finally(() => {
            this.isSubmitting = false
          })
        })
      })
    },
    submit () {
      let action = '增加'
      if (this.item.value.object_id) {
        action = '修改'
      }
      this.save().then(() => {
        this.$notify.success({
          title: '成功',
          message: `通用Service${action}成功!`
        })
      }, (error) => {
        this.$notify.error({
          title: '失败',
          message: `通用Service${action}失败!`
        })
        console.warn(error)
      })
    }
  }
}
</script>
