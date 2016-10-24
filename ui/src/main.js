import Vue from 'vue'
import App from './App'
import Element from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import VueRouter from 'vue-router'
import configRouter from './route.config'
// import Icon from './components/icon'

Vue.use(Element)
Vue.use(VueRouter)
// Vue.component('icon', Icon)

const router = new VueRouter({
  mode: 'hash',
  base: __dirname,
  routes: configRouter
})

new Vue({ // eslint-disable-line
  render: h => h(App),
  router
}).$mount('#app')
