<template>
  <div>
    <el-dialog title="历史模板文件列表" v-model="showDialog">
      <el-table
        :data="files"
        :stripe="true"
        v-loading="isLoadingData"
        :default-sort = "{prop: 'version_id', order: 'descending'}"
        selection-mode="multiple"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" ></el-table-column>
        <el-table-column prop="user_id" label="用户编号"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="filename" label="文件名称"></el-table-column>
        <el-table-column prop="version_id" label="版本号"></el-table-column>
        <el-table-column prop="date_created" label="文件创建日期"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" :disabled="! oneFileSelected" @click="restoreFile">恢复选中模板文件</el-button>
        <el-button type="primary" :disabled="! twoFileSelected">比较两个模板内容</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import _ from 'lodash'
  export default {
    props: ['id'],
    data () {
      return {
        files: [],
        selectedItems: [],
        isLoadingData: false,
        showDialog: true
      }
    },
    computed: {
      oneFileSelected: function () {
        return this.selectedItems.length === 1
      },
      twoFileSelected: function () {
        return this.selectedItems.length === 2
      }
    },
    methods: {
      handleSelectionChange (val) {
        this.selectedItems = val
      },
      restoreFile: _.debounce(function () {
        let method = 'patch'
        let url = `/api/service/templates/${this.id}/`
        let action = '修改'
        let params = {
          id: this.id,
          filename: this.selectedItems[0].filename
        }
        this.$http[method](url, params)
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
          })
      }, 500),
      loadFiles () {
        this.isLoadingData = true
        this.$http.get(`/api/service/templates/${this.id}/historical-files/verbose/`)
          .then(({data}) => {
            this.$set(this, 'files', data.results)
          }, (response) => {
            let msg = 'Empty Response'
            if (response) {
              msg = response.body.detail
            }
            this.$notify.error({
              title: response.statusText,
              message: msg
            })
          }).finally(() => {
            this.isLoadingData = false
          })
      }
    },
    mounted () {
      this.loadFiles()
    }
  }
</script>
