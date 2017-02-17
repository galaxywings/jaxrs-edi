<template>
  <div>
    <el-form :inline="true" :model="form" >
      <el-form-item>
        <el-input placeholder="ID / 模板名称 / 文件名 / 配置类型" v-model="form.q">
          <el-button slot="append" icon="search" @click.native="search"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small">
          <router-link :to="{ name: 'service.templates.create' }">
            <i class="el-icon-plus"></i>
          </router-link>
        </el-button>
        <el-button type="danger" size="small"
          :disabled="selectedItems.length==0"
          @click="removeItems">
          <i class="el-icon-close"></i>
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="items"
      :stripe="true"
      v-loading="isLoadingData"
      :default-sort = "{prop: 'id', order: 'descending'}"
      selection-mode="multiple"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" ></el-table-column>
      <el-table-column
        prop="id"
        label="ID"
        >
      </el-table-column>
      <el-table-column
        prop="name"
        label="Template name"
        >
      </el-table-column>
      <el-table-column
        prop="filename"
        label="Filename"
        >
      </el-table-column>
      <el-table-column
        prop="extra_schema"
        label="Exaschema"
        >
      </el-table-column>
      <el-table-column
        :context="_self"
        inline-template
        label="Operations"
        >
        <div>
          <el-button
            size="small"
            @click="handleEdit($index, row)">
            <i class="el-icon-edit"></i>
          </el-button>
          <el-button
            size="small"
            @click="removeItem($index, row)">
            <i class="el-icon-close"></i>
          </el-button>
        </div>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.page"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.itemTotal">
    </el-pagination>
    <el-dialog title="请确认是否删除如下数据！" v-model="showDialog">
      <el-table :data="toBeRemoved" :stripe="true">
        <el-table-column prop="id" label="编号"></el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="filename" label="文件名称"></el-table-column>
        <el-table-column prop="exa_schema" label="配置类型"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showDialog = false">Cancel</el-button>
        <el-button type="primary" @click="doRemoveItems">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import _ from 'lodash'

  export default {
    data () {
      return {
        items: [],
        selectedItems: [],
        toBeRemoved: [],
        isLoadingData: false,
        form: {
          q: ''
        },
        pagination: {
          page: 1,
          pageSize: 10,
          itemTotal: 0
        },
        showDialog: false
      }
    },
    methods: {
      handleEdit ($index, row) {
        this.$router.push({name: 'service.templates.edit', params: {id: row.id}})
      },
      removeItem ($index, row) {
        this.toBeRemoved = [row]
        this.showDialog = true
      },
      removeItems () {
        // show form
        this.toBeRemoved = this.selectedItems
        this.showDialog = true
      },
      doRemoveItems () {
        // Delete items
        let ids = this.toBeRemoved.map(item => item.id).join(',')
        this.$http.delete('/api/service/templates/', {
          params: {
            id__in: ids
          }
        }).then((response) => {
          this.showDialog = false
          this.$notify.success({
            title: '删除成功！'
          })
          this.search()
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
          this.removeDialogVisible = false
        })
      },
      handleSelectionChange (val) {
        this.selectedItems = val
      },
      handleSizeChange (val) {
        this.pagination.pageSize = val
        this.search()
      },
      handleCurrentChange (val) {
        this.pagination.page = val
        this.search()
      },
      search: _.debounce(function () {
        this.isLoadingData = true
        this.$http.get('/api/service/templates/',
          {
            params: {
              q: this.form.q,
              fields: 'id,name,filename,extra_schema',
              page: this.pagination.page,
              page_size: this.pagination.pageSize
            }
          }).then(({data}) => {
            this.pagination.itemTotal = data.count
            this.$set(this, 'items', data.results)
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
      }, 500)
    },
    watch: {
      'form.q': function () {
        this.search()
      }
    },
    mounted () {
      this.search()
    }
  }
</script>
