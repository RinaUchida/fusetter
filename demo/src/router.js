import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Currency.vue')
    },
    // ここを追加
    {
      path: '/currency',
      name: 'currency',
      component: () => import(/* webpackChunkName: "currency" */ './views/Currency.vue')
    },
    {
        path: '/RestTest',
        name: 'RestTest',
        component: () => import(/* webpackChunkName: "currency" */ './views/RestTest.vue')
    },
    {
        path: '/RestTest2',
        name: 'RestTest2',
        component: () => import(/* webpackChunkName: "currency" */ './views/RestTest2.vue')
    },
    {
        path: '/tw/:fsgCryptogram',
        name: 'tw',
        component: () => import(/* webpackChunkName: "currency" */ './views/tw.vue')
    },

    //ERROR系遷移
    {
        path: '/404',
        name: '404',
        component: () => import(/* webpackChunkName: "currency" */ './views/404NotFound.vue')
    },

  ]
})

