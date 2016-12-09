<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task'}">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/task/services'}">Service管理</el-breadcrumb-item>
      <el-breadcrumb-item>Service列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-form :inline="true" :model="serviceListForm" >
      <el-form-item>
        <el-input placeholder="Code / Name" v-model="serviceListForm.q">
          <el-button slot="append" icon="search" @click.native="search"></el-button>
        </el-input>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      :stripe="true"
      v-loading="isLoadingData"
      style="">
      <el-table-column
        prop="id"
        label="ID"
        >
      </el-table-column>
      <el-table-column
        prop="code"
        label="Code"
        >
      </el-table-column>
      <el-table-column
        prop="name"
        label="Name"
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
            <i class="el-icon-view"></i>
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
  </div>
</template>
<script>
  import _ from 'lodash'

  export default {
    data () {
      return {
        tableData: [],
        isLoadingData: false,
        serviceListForm: {
          q: ''
        },
        pagination: {
          page: 1,
          pageSize: 10,
          itemTotal: 0
        }
      }
    },
    methods: {
      handleEdit ($index, row) {
        this.$router.push({name: 'taskServiceEdit', params: {id: row.id}})
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
        this.$http.get('/api/v1/task/services/',
          {
            params: {
              q: this.serviceListForm.q,
              fields: 'id,code,name',
              page: this.pagination.page,
              page_size: this.pagination.pageSize
            }
          }).then((response) => {
            // use response.body to avoid nested hell
            let data = response.body
            this.pagination.itemTotal = data.count
            this.$set(this, 'tableData', data.results)
            // response.json().then((res) => {
            //   this.pagination.itemTotal = res.count
            //   this.$set(this, 'tableData', res.results)
            // })
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
      'serviceListForm.q': function () {
        this.search()
      }
    },
    mounted () {
      this.search()
    }
  }
</script>
