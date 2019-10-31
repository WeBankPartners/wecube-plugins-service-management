import Vue from 'vue'
import App from './App.vue'
import router from './router'
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import PluginSelect from "./components/select.vue";

Vue.use(iView);

Vue.config.productionTip = false
Vue.component("PluginSelect", PluginSelect);
window.component && window.component("PluginSelect", PluginSelect)
window.addRoutes && window.addRoutes(router, "itsm");


// new Vue({
//   router,
//   render: h => h(App)
// }).$mount('#qqqqqq')
