// 导入创建路由和路由模式的方法
import {createRouter, createWebHistory} from "vue-router";
import {actions, route_white_list, session} from "@/constant";
import store from "@/store";
import storage from "@/utils/storage";
import {checkToken} from "@/api/user";
import {ElMessage} from "element-plus";


// 定义路由信息
let routes = [
    {
        name: 'login',
        path: '/login',
        component: () => import('@/components/login')
    },
    {
        name: 'main',
        path: '/main',
        component: () => import('@/components/main'),
        children: [
            {
                name: 'user',
                path: 'user',
                component: () => import('@/components/system/user')
            },
            {
                name: 'role',
                path: 'role',
                component: () => import('@/components/system/role')
            },
        ]
    }

]

// 创建路由实例并传递 `routes` 配置
// 使用的路由模式是html5的，而不是hash，url中不含#
const router = createRouter({
    history: createWebHistory(),
    routes
})

// 全局的路由守卫，可以在路由进入前做一些工作
router.beforeEach((to, from) => {
    // 获取目的路由名字
    let routeName = to.name
    // 是否需要放行
    if (route_white_list.includes(routeName)) return true

    console.log("执行before")
    // 检查是否登录，登录则放行
    console.log(store.state.user.token)
    if (!store.getters.isLogin) {
        if (storage.sessionKeyIsEmpty(session.SESSION_USER_TAG)) {
            // 尝试从session中恢复数据
            store.dispatch(actions.RESTORE).catch(error => {
                store.dispatch(actions.EMPTY)
                ElMessage.error('身份认证失效，请重新登录')
                router.push({name: 'login'})
            })
        } else {
            router.push({name: 'login'})
            return true
        }
    }

    store.dispatch(actions.GET_USER_INFO)
    // 未登录则登录
    return true
})

// 导出路由实例
export default router