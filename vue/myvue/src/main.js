import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false

// 显式声明使用VueRouter
// Vue.use(VueRouter)

new Vue({
    el: '#app',
    // 配置路由
    router,
    components: {App},
    template: '<App/>'
})
