<template>
  <div>
    <el-form :inline="true" :model="processListForm" >
      <el-form-item>
        <el-input placeholder="名称" v-model="processListForm.q">
          <el-button slot="append" icon="search" @click.native="search"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small">
          <router-link :to="{ name: 'task.processes.create' }">
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
      v-loading="isLoadingData"
      selection-mode="multiple"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        fixed>
      </el-table-column>
      <el-table-column
        prop="id"
        sortable
        label="ID">
      </el-table-column>
      <el-table-column
        prop="name"
        sortable
        label="名称">
      </el-table-column>
      <el-table-column
        prop="interval"
        label="间隔">
      </el-table-column>
      <el-table-column
        prop="active"
        label="激活"
        :filters="[{ text: 'Enabled', value: 'Enabled' }, { text: 'Disabled', value: 'Disabled' }]"
        :filter-method="filterActive"
        inline-template>
        <el-tag :type="row.active? 'primary' : 'dark'" close-transition>{{row.active? 'Enabled': 'Disabled'}}</el-tag>
      </el-table-column>
      <el-table-column
        :context="_self"
        fixed="right"
        inline-template
        label="操作"
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
    <el-dialog title="Sure to delete" v-model="removeDialogVisible">
      <el-table :data="toBeRemoved" :stripe="true">
        <el-table-column property="customer_code" label="Customer"></el-table-column>
        <el-table-column property="name" label="Name"></el-table-column>
        <el-table-column property="active" label="Active" inline-template>
          <el-tag :type="row.active? 'primary' : 'success'" close-transition>{{row.active? 'Enabled': 'Disabled'}}</el-tag>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click.native="removeDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click.native="doRemoveItems">Confirm</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import _ from 'lodash'

export default {
  data () {
    return {
      tableData: [],
      multipleSelection: [],
      isLoadingData: false,
      processListForm: {
        q: ''
      },
      pagination: {
        page: 1,
        pageSize: 10,
        itemTotal: 0
      },
      toBeRemoved: [],
      removeDialogVisible: false
    }
  },
  methods: {
    handleEdit ($index, row) {
      this.$router.push({name: 'task.processes.edit', params: {id: row.id}})
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
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
      // should go with format _.debounce( function () {...} )
      this.isLoadingData = true
      this.$http.get('/api/task/processes/',
        {
          params: {
            q: this.processListForm.q,
            page: this.pagination.page,
            page_size: this.pagination.pageSize,
            fields: 'id,name,interval,customer,customer_code,active'
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
    }, 500),
    removeItems () {
      if (this.multipleSelection.length > 0) {
        this.$set(this, 'toBeRemoved', this.multipleSelection)
        this.removeDialogVisible = true
      }
    },
    handleDelete ($index, row) {
      this.$set(this, 'toBeRemoved', [row])
      this.removeDialogVisible = true
    },
    doRemoveItems () {
      let toBeRemovedIds = this.toBeRemoved.map(x => x.id).join(',')
      this.$http.delete('/api/task/processes/', {
        params: {
          id__in: toBeRemovedIds
        }
      }).then((response) => {
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
    filterActive (value, row) {
      if (value === 'Enabled') {
        return row.active
      } else {
        return !row.active
      }
    }
  },
  watch: {
    'processListForm.q': function () {
      this.search()
    }
  },
  mounted () {
    this.search()
  }
}
</script>
