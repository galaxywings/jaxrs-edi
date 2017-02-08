<template>
  <div>
    <el-select
        v-model="objectId">
      <el-option
          v-for="item in ftps"
          :key="item.id"
          :label="item.name"
          :value="item.id">
      </el-option>
    </el-select>
    <div v-show="ftp">
      <table>
        <tr>
          <th>FTP地址:</th>
          <td>{{ftp.host}} : {{ftp.port}}</td>
        </tr>
        <tr>
          <th>FTP路径:</th>
          <td> {{ftp.path}}</td>
        </tr>
        <tr>
          <th>用户名:</th>
          <td>{{ftp.username}}</td>
        </tr>
        <tr>
          <th>密码:</th>
          <td>{{ftp.username}}</td>
        </tr>
        <tr v-for="(value,key) in ftp.extra_params">
          <th> {{key}}: </th>
          <td> {{value}} </td>
        </tr>
      </table>

    </div>
  </div>
</template>
<script>
  export default {
    props: {
      step: {
        type: Object,
        require: true
      }
    },
    data () {
      return {
        ftps: [],
        objectId: ''
      }
    },
    watch: {
      objectId (val, oldVal) {
        this.step.object_id = val
      }
    },
    computed: {
      ftp () {
        let result = this.ftps.filter((item) => item.id === this.objectId)
        if (result.length) {
          return result[0]
        } else {
          return ''
        }
      }
    },
    methods: {
      fetchFtps () {
        this.$http.get('/api/v1/service/ftps/').then(({data}) => {
          this.ftps = data.results
        }, (response) => {
          if (response.status === 404) {
            this.$notify.warning({
              title: response.statusText,
              message: 'The FTP list cannot be found'
            })
          }
        })
      }
    },
    mounted () {
      this.fetchFtps()
      this.objectId = this.step.object_id
    }
  }
</script>
