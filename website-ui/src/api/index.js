import axios from "axios";
import store from '@/store'
import {session} from "@/constant";
import storage from "@/utils/storage";

// 创建 axios 实例
const request = axios.create({
    // 请求url的公共部分
    baseURL: '/admin',
    // 超时时间
    timeout: 5000,
    // 设置 `content-type` ，规定前后端json格式通信
    headers: {'Content-Type': 'application/json;charset=UTF-8'}
})

request.interceptors.request.use(config => {
    //store.state.user.token
    let loginUser = storage.getSessionObject(session.SESSION_USER_TAG)
    if (loginUser != null) {
        // 如果用户含有token，则每次请求携带token
        config.headers['Authorization'] = loginUser.token
    }
    return config
}, error => {
    // 对请求错误做写什么
    return Promise.reject(error)
})

export default request