import Vue from 'vue'
export default {
  isRouteLoginRequired (route) {
    let result = false
    // I just want exactRoute to have a chance to override its ancestor's settings
    for (var item of route.matched) {
      // as detailed in https://router.vuejs.org/en/api/route-object.html, the last one is the exactRoute
      // When the URL is /foo/bar, $route.matched will be an Array containing both objects (cloned), in parent to child order.
      // in parent to child order.
      // typeof only usage to see if a variable is undefined
      // please refer to http://bonsaiden.github.io/JavaScript-Garden/#types.typeof
      if (item.meta && typeof item.meta.loginRequired !== 'undefined') {
        result = item.meta.loginRequired
      }
    }
    return result
  },
  prepareRequestAuthRequired (request, currentRoute) {
    // this method will setup an appropriate request Authorization headers or not
    // request.headers at least an empty Map object
    if (request.headers.has('Authorization')) {
      return request
    }

    if (this.isRouteLoginRequired(currentRoute)) {
      // let jwt = 'test'
      // request.headers.set('Authorization', `JWT: ${jwt}`)
      request.headers.set('Authorization', '')
    }
    return request
  },
  handleResponse (response, request, router) {
    return new Promise((resolve, reject) => {
      if (response.ok) {
        resolve(response)
      } else {
        if (response.status === 401) {
          // if (response.status===401 && response.xxx == 'not logined?') {
          let currentRoute = router.currentRoute
          Vue.nextTick(() => {
            router.push({name: 'login', query: {next: currentRoute.fullPath}})
          })
        }
        reject(response)
      }
    })
  }
}
