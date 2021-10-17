import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home'
import Config from '@/components/Config'
import Application from '@/components/Application'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            component: Home
        },
        {
            path: '/index',
            component: Home
        },
        {
            path: '/application',
            component: Application
        },
        {
            path: '/config',
            component: Config
        }
    ]
})
