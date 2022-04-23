import {createApp} from 'vue'
import App from './App.vue'
import router from "@/route";
import store from "@/store";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@/assets/css/common.css'
import directives from "@/directive";

let app = createApp(App)
    .use(router)                    // 安装路由
    .use(store)                     // 安装vuex
    .use(ElementPlus)               // 使用UI库

// 添加自定义指定
for (let key in directives) {
    app.directive(key, directives[key])
}

app.mount('#app')