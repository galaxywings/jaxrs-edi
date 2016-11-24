<template>
  <div>
    test1
    <router-link :to="{ name: 'test2'}">Test2</router-link>
  </div>
</template>
<script>
export default {
  data () {
    return {
      gridData: []
    }
  },
  mounted () {
    console.log('abc')
    // this.getCustomers()
  },
  methods: {
    getCustomers () {
      this.$http.get('http://211.149.193.19:8080/api/customers')
        .then(response => {
          this.$set(this, 'gridData', response.data)
        })
        .catch(response => {
          console.log(response)
        })
    }
  }
}
</script>
