import Vue from 'vue'
import App from './App.vue'
import router from './router'

//import ElementUI from 'element-ui' // 追記
//import locale from 'element-ui/lib/locale/lang/ja' // 追記
//import 'element-ui/lib/theme-chalk/index.css' // 追記

Vue.config.productionTip = false
//Vue.use(ElementUI, {locale}) // 追記

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')