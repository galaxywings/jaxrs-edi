import Vue from 'vue'
import App from './App'
import Element from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import VueRouter from 'vue-router'
import routerConfig from './route.config'
import localeConfig from './locales/index'
// import Icon from './components/icon'
import VueI18n from 'vue-i18n'

Vue.use(Element)
Vue.use(VueRouter)
// Vue.component('icon', Icon)

Vue.use(VueI18n)

Object.keys(localeConfig).forEach(langCode => Vue.locale(langCode, localeConfig[langCode]))

Vue.config.lang = 'zh-Hans'
Vue.config.fallbackLang = 'en'

const router = new VueRouter({
  mode: 'hash',
  base: __dirname,
  routes: routerConfig
})

new Vue({ // eslint-disable-line
  el: '#app',
  render: h => h(App),
  router
})
