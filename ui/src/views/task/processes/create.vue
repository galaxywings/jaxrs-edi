<template>
  <div>
    <el-form :model="processForm" :rules="rules" ref="processForm" label-width="120px" >
      <el-form-item label="Name" prop="name">
        <el-input v-model="processForm.name"></el-input>
      </el-form-item>
      <el-form-item label="Interval" prop="interval">
        <el-input v-model.number="processForm.interval"></el-input>
      </el-form-item>
      <el-form-item label="Active" prop="active">
         <el-switch on-text="Yes" off-text="No" v-model="processForm.active"></el-switch>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click.native="handleSubmit">Submit</el-button>
        <el-button @click.native="handleReset">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import _ from 'lodash'
export default {
  data () {
    return {
      processForm: {
        name: '',
        interval: 60,
        active: false
      },
      rules: {
        name: [
          { required: true, message: 'Name is required', trigger: 'blur' },
          { min: 3, max: 128, message: 'Length should be 3 to 128', trigger: 'blur' }
        ],
        interval: [
          { type: 'integer', min: 60, message: 'Interval better greater than 60 secs', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleReset () {
      this.$refs.processForm.resetFields()
    },
    handleSubmit: _.debounce(function (ev) {
      this.$refs.processForm.validate((valid) => {
        if (!valid) {
          this.$notify.error({
            title: 'Error',
            message: 'Please correct the outstanding error(s)'
          })
          return false
        }
        this.$http.post('/api/task/processes/', this.processForm)
          .then(({data}) => {
            this.$notify({
              title: 'Success',
              message: 'Process created successfully'
            })
            this.$router.push({name: 'task.processes.edit', params: { id: data.id }})
          }, (response) => {
            this.$notify.error({
              title: response.statusText,
              message: response.body.detail
            })
          })
      })
    }, 500)
  },
  mounted () {
  }
}
</script>
