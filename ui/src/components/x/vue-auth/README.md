# Vue Auth with modification to fit in wujie

For details, please refer to https://github.com/websanova/vue-auth

1. remove meta.auth `undefined` assumption, taking it as false
2. meta.auth could be inherited from current route's ancestor
3. redirect to login with `next` query string
