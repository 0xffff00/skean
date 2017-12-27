import Vue from 'vue'
import iView from 'iview'
import VueRouter from 'vue-router'
import Routes from './routes'

import App from './example-app.vue'
import 'iview/dist/styles/iview.css'

Vue.use(VueRouter)
Vue.use(iView)

// 开启debug模式
Vue.config.debug = true

// 路由配置
const RouterConfig = {
    // mode: 'history',
    routes: Routes
}
const router = new VueRouter(RouterConfig)

router.beforeEach((to, from, next) => {
    iView.LoadingBar.start()
    window.document.title = to.meta.title || 'SkFE Project'
    next()
})

router.afterEach(() => {
    iView.LoadingBar.finish()
    window.scrollTo(0, 0)
})

new Vue({
    el: '#app',
    router: router,
    render: h => h(App)
})
