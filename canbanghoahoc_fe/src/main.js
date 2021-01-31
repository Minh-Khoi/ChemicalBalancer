import Vue from 'vue';
import App from './App.vue';
import './registerServiceWorker';
import router from './router/router';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import jquery from 'jquery';

window.$ = jquery;

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
