<template>
  <div>
    <el-form :inline="true" :model="form" >
      <el-form-item>
        <el-input placeholder="Code / Name" v-model="form.q">
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
        prop="filename"
        label="Filename"
        >
      </el-table-column>
      <el-table-column
        prop="size"
        label="Size"
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
            @click="handleView($index, row)">
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
        form: {
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
      handleView ($index, row) {
        this.$router.push({name: 'file.files.view', params: {id: row.id}})
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
        this.$http.get('/api/misc/dbfiles/',
          {
            params: {
              q: this.form.q,
              fields: 'id,filename,size',
              page: this.pagination.page,
              page_size: this.pagination.pageSize
            }
          }).then(({data}) => {
            this.pagination.itemTotal = data.count
            this.$set(this, 'tableData', data.results)
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
