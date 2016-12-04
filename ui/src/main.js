import Vue from 'vue'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'
import Resource from 'vue-resource'

import Element from 'element-ui'
import 'element-ui/lib/theme-default/index.css'

import Auth from './components/x/vue-auth'

import routerConfig from './routes'
// import authService from './services/auth'

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

Vue.use(Resource)

Vue.use(Auth, {
  // http: Vue.http, // seems Vue.use(Resource) already make Vue.http available
  router: router,
  loginData: {
    url: 'api/auth/token/',
    method: 'POST',
    redirect: '/'
  },
  fetchData: {
    url: 'api/auth/users/user-info/',
    method: 'GET'
  },
  refreshData: {
    url: 'api/auth/token/refresh/',
    method: 'POST'
  },
  parseUserData: function (data) {
    return data
  },
  rolesVar: 'groups',
  refreshPerform: function (data) {
    let token = this.token()
    let _this = this
    let success = data.success || function () { console.info('empty sucess') }
    let error = data.error || function (res) { console.error(res) }
    data = Object.assign({},
      data,
      this.options.refreshData,
      {
        body: {
          token: token
        },
        success: function (res) {
          data.success = success
          _this.options.refreshProcess.call(_this, res, data)
        },
        error: error
      })

    this.options._http.call(this, data)
  },
  tokenExpired: function () {
    if (!this.watch.loaded) { return true }
    let token = this.token()
    if (!token) { return true }

    let payload = window.atob(token.split('.')[1])
    // below is utc milliseconds, please refer to
    // http://stackoverflow.com/questions/8047616/get-a-utc-timestamp-in-javascript/15012173#answer-8047885
    let nowUTCMilliSec = new Date().getTime()
    let delta5MinMilliSec = 5 * 60 * 1000 * 1000
    return payload.exp < nowUTCMilliSec + delta5MinMilliSec
  },
  authType: 'jwt',
  jwtAuth: {
    request: function (req, token) {
      // this token would not be empty
      this.options._setHeaders.call(this, req, {
        Authorization: 'JWT ' + token
      })
    },
    response: function (res) {
      // return the token if exists otherwise false
      let token = false
      if (res.ok) {
        let tokenUrlSet = new Set([this.options.loginData.url, this.options.refreshData.url])
        if (tokenUrlSet.has(res.url)) {
          token = res.data.token
        }
      } else {
        let redirectUrlSet = new Set([this.options.refreshData.url])
        if (redirectUrlSet.has(res.url)) {
          let _this = this
          res.text().then((text) => {
            if (text.includes('expired')) {
              setTimeout(() => {
                if (!_this.options.resolveRouteAuth(_this.options.router.currentRoute)) {
                  return
                }
                let nextQs = {}
                if (_this.options.router.currentRoute.path !== _this.options.authRedirect.path) {
                  nextQs.query = {next: _this.options.router.currentRoute.fullPath, reason: 'TOKEN_EXPIRED'}
                } else {
                  nextQs.query = Object.assign({}, _this.options.router.currentRoute.query, {reason: 'TOKEN_EXPIRED'})
                }
                let redirect = Object.assign({}, _this.options.authRedirect, nextQs)
                _this.watch.loaded = true // TODO disable a second time to tokenExpired
                _this.options._routerGo.call(_this, redirect)
              }, 200) // I need this timeout to get the route path stable...
            }
          })
        }
      }
      return token
    }
  }
})

Vue.use(Element)

// custom component activation here, using async component loading
Vue.component('ace-editor', function (resolve, reject) {
  // please refer to https://vuejs.org/v2/guide/components.html#Async-Components
  require(['./components/ace-editor'], resolve)
})

new Vue({ // eslint-disable-line
  el: '#app',
  render: h => h(App),
  router
})
