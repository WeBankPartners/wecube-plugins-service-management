import Vue from 'vue'
import router from './router-plugin'
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';
import PluginSelect from "./components/select.vue";

Vue.use(ViewUI);

Vue.config.productionTip = false
Vue.component("PluginSelect", PluginSelect);
window.component && window.component("PluginSelect", PluginSelect)
window.addRoutes && window.addRoutes(router, "itsm");