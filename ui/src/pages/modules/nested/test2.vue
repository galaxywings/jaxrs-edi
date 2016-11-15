<template>
  <div>
    test2  {{msg}}
    <router-link :to="{ path: '/nested/test1'}">Test1</router-link>
  </div>
</template>
<script>
export default {
  data () {
    return {
      msg: 'default msg'
    }
  },
  mounted () {
    this.getMsg()
  },
  methods: {
    getMsg () {
      this.$http.get('/api/auth/users/')
        .then(response => {
          this.$set(this, 'msg', response.data.msg)
        }, response => {
          console.error(response)
        })
        .catch(response => {
          console.log(response)
        })
    }
  }

}
</script>
