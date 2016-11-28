<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/customer'}">客户管理</el-breadcrumb-item>
      <el-breadcrumb-item>客户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :inline="true" :model="customerListForm" >
      <el-form-item>
        <el-input placeholder="Code / Name" v-model="customerListForm.q">
          <el-button slot="append" icon="search" @click="search"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small">
          <router-link :to="{ name: 'customerCustomerNew' }">
            <i class="el-icon-plus"></i>
          </router-link>
        </el-button>
        <el-button type="danger" size="small"
          :disabled="multipleSelection.length==0"
          @click="removeItems">
          <i class="el-icon-close"></i>
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      :stripe="true"
      selection-mode="multiple"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        fixed
        width="50">
      </el-table-column>
      <el-table-column
        prop="date"
        label="Date"
        width="150">
      </el-table-column>
      <el-table-column
        prop="name"
        label="Name"
        width="120">
      </el-table-column>
      <el-table-column
        prop="state"
        label="State"
        width="120">
      </el-table-column>
      <el-table-column
        prop="city"
        label="City"
        width="120">
      </el-table-column>
      <el-table-column
        prop="address"
        label="Address"
        width="300">
      </el-table-column>
      <el-table-column
        prop="zip"
        label="Zip"
        width="120">
      </el-table-column>
      <el-table-column
        :context="_self"
        fixed="right"
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
            @click="handleDelete($index, row)">
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
  </div>
</template>
<script>
export default {
  data () {
    let tableData = Array(3).fill({}).map((_, i) => {
      return {
        date: `2016-05-${i}`,
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: 'No. 189, Grove St, Los Angeles',
        zip: 'CA 90036'
      }
    })
    return {
      tableData: tableData,
      multipleSelection: [],
      customerListForm: {
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
      console.log($index, row)
    },
    handleClose ($index, row) {
      console.log($index, row)
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    handleSizeChange (val) {
      this.pagination.pageSize = val
    },
    handleCurrentChange (val) {
      this.pagination.page = val
    },
    search () {
      console.log(`search with q: ${this.customerListForm.q}, pagination: ${this.pagination}`)
    },
    removeItems () {
      if (this.multipleSelection.length > 0) {
        this.doRemoveItems(this.multipleSelection)
      }
    },
    doRemoveItems (items) {
      console.log(`remove items: ${items}`)
    }
  },
  mounted () {
    this.search()
  }
}
</script>
