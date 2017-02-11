# wujie-ui

> wujie dashboard

## Build Setup

``` bash
# install dependencies
npm install

# default backend endpoint change to suite your need
# usually our dashboard django backend
export backend_endpoint="http://localhost:8090"
# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

For detailed explanation on how things work, checkout the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

## upgrade to webpack 1.* to 2

```
pyenv shell system
pyenv local system # using Python 2.x
cd node_modules
rm -rf *
cnpm install -g webpack vue-cli
# npm install grunt --save-dev
# npm install phantomjs
# cnpm rebuild node-sass
# npm install --chromedriver_cdnurl=http://cdn.npm.taobao.org/dist/chromedriver
cnpm install
```