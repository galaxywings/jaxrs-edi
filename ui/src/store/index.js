import Vue from 'vue'
import Vuex from 'vuex'
// import createLogger from 'vuex/dist/logger'

// import * as actions from './actions'
// import * as getters from './getters'
import task from './modules/task'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    task
  },
  strict: debug,
  plugins: []
})
