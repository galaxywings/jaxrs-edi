import process from './process'

export default {
  namespaced: true,
  // module assets
  state: {}, // module state is already nested and not affected by namespace option
  getters: {
    isAdmin () {} // -> getters['account/isAdmin']
  },
  actions: {
    login () {} // -> dispatch('account/login')
  },
  mutations: {
    login () {} // -> commit('account/login')
  },
  modeuls: {
    process
  }
}
