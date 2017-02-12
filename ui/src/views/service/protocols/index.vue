<template>
  <div>
    <el-form :inline="true" >
      <el-form-item>
        <el-input placeholder="Name/Attachment/Sender/Receiver" v-model="query" @change="search">
          <el-button slot="append" icon="search"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small">
          <router-link :to="{ name: 'service.protocols.create' }">
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
      <el-table-column prop="id" label="编号"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="sender_code" label="发送方code"></el-table-column>
      <el-table-column prop="src_format" label="发送格式"></el-table-column>
      <el-table-column prop="receiver_code" label="接收方code"></el-table-column>
      <el-table-column prop="dest_format" label="接收格式"></el-table-column>
      <el-table-column prop="filename" label="附件"></el-table-column>
      <el-table-column :context="_self" inline-template label="Operations">
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
        <el-table-column prop="sender_code" label="发送方code"></el-table-column>
        <el-table-column prop="src_format" label="发送格式"></el-table-column>
        <el-table-column prop="receiver_code" label="接收方code"></el-table-column>
        <el-table-column prop="dest_format" label="接收格式"></el-table-column>
        <el-table-column prop="filename" label="附件"></el-table-column>
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
      query: '',
      pagination: {
        page: 1,
        pageSize: 10,
        itemTotal: 0
      },
      showDialog: false
    }
  },
  methods: {
    search: _.debounce(function () {
      this.$http.get('/api/v1/service/protocols/',
        {
          params: {
            q: this.query,
            page: this.pagination.page,
            page_size: this.pagination.pageSize
          }
        }).then(({data}) => {
          this.items = data.results
          this.pagination.itemTotal = data.count
        }, (error) => {
          console.log(error)
        })
    }, 500),
    handleEdit ($index, row) {
      this.$router.push({name: 'service.protocols.edit', params: {id: row.id}})
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
      this.$http.delete('/api/service/protocols/', {
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
    }
  },
  mounted () {
    this.search()
  }
}
</script>
