import Vue from 'vue'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'
import Resource from 'vue-resource'

import Element from 'element-ui'
import 'element-ui/lib/theme-default/index.css'

import routerConfig from './routes'
import authService from './services/auth'

import localeConfig from './locales/index'

import App from './App'

Vue.use(VueI18n)
Object.keys(localeConfig).forEach(langCode => Vue.locale(langCode, localeConfig[langCode]))
Vue.config.lang = 'zh-Hans'
Vue.config.fallbackLang = 'en'

Vue.use(VueRouter)
const router = new VueRouter({
  mode: 'hash',
  base: __dirname,
  routes: routerConfig
})

// Some middleware to help us ensure the user is authenticated.
router.beforeEach((to, from, next) => {
  let loginRequired = authService.isRouteLoginRequired(to)
  if (loginRequired && true) { // true just a placeholder for $store.state to check if the token not available
    console.log('Not authenticated')
    next({
      path: '/login',
      query: { next: to.fullPath }
    })
  } else {
    next()
  }
})

Vue.use(Resource)

Vue.http.interceptors.push((request, next) => {
  // this is the VueComponent
  request = authService.prepareRequestAuthRequired(request, router.currentRoute)
  console.log('interceptor', this, request)
  next((response) => {
    // TODO, error handling for login redirection
    return authService.handleResponse(response, request, router)
  })
})

Vue.use(Element)

new Vue({ // eslint-disable-line
  el: '#app',
  render: h => h(App),
  router
})
