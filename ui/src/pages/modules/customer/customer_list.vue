<template>
  <div>
    <el-table
      :data="tableData"
      :stripe="true"
      selection-mode="multiple"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        fixed
        type="selection"
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
            type="danger"
            @click="handleDelete($index, row)">
            <i class="el-icon-close"></i>
          </el-button>
        </div>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="1"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableData.length">
    </el-pagination>
  </div>
</template>
<script>
export default {
  data () {
    let tableData = Array(11).fill({
      date: '2016-05-03',
      name: 'Tom',
      state: 'California',
      city: 'Los Angeles',
      address: 'No. 189, Grove St, Los Angeles',
      zip: 'CA 90036'
    })

    return {
      tableData: tableData,
      multipleSelection: []
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
      console.log(`${val} items per page`)
    },
    handleCurrentChange (val) {
      console.log(`current page: ${val}`)
    }
  }
}
</script>
