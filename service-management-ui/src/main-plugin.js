// import Vue from 'vue'
import router from './router-plugin'
// import ViewUI from 'view-design';
// import 'view-design/dist/styles/iview.css';
import PluginSelect from "./components/select.vue";
import ZH from "./i18n/zh-CN.json";
import EN from "./i18n/en-US.json";

// Vue.use(ViewUI);

// Vue.config.productionTip = false
// Vue.component("PluginSelect", PluginSelect);
window.component && window.component("PluginSelect", PluginSelect)
window.addRoutes && window.addRoutes(router, "itsm");
window.locale("zh-CN", ZH);
window.locale("en-US", EN);
