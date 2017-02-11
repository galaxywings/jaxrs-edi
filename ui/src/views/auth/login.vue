<template>
  <el-row :gutter="20">
    <el-col :span="10" :offset="7">
      <el-card class="login-form">
        <el-row type="flex" class="row-bg" justify="center">
          <h1>无界EDI 登陆</h1>
        </el-row>
        <el-form ref="form" label-width="120px">

          <el-form-item label="用户名">
            <el-input v-model="data.body.username"></el-input>
          </el-form-item>

          <el-form-item label="密码">
            <el-input v-model="data.body.password" type="password"></el-input>
          </el-form-item>

          <el-form-item>
            <el-checkbox v-model="data.rememberMe">Remember Me</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="login">登陆</el-button>
            <el-button >忘记密码</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data () {
    return {
      context: 'login context',
      data: {
        // body is fixex variable naming in `vue-resource` do not CHANGE it!!!
        body: {
          username: 'admin',
          password: 'admin'
        },
        rememberMe: false
      },
      error: null
    }
  },

  methods: {
    login () {
      this.$auth.login({
        body: this.data.body,
        rememberMe: this.data.rememberMe,
        success () {
          console.log('success ' + this.context)
          this.$message({
            message: 'Login successfully',
            type: 'success'
          })
        },
        error (res) {
          console.log('error ' + this.context)
          this.error = res.data
          this.$message({
            message: this.error,
            type: 'error'
          })
        }
      })
    }
  }
}
</script>


<style lang="sass" >
  .login-form
    margin-top: 30%

</style>
