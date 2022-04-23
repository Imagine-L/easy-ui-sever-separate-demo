import request from "@/api";

// 测试方法
export function test(data) {
    return request({
        url: '/test',
        method: 'get',
        data: data
    })
}

// 登录
export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}

// 登出
export function logout() {
    return request({
        url: '/logout',
        method: 'get'
    })
}

// 检查token是否合法
export function checkToken() {
    return request({
        url: '/checkToken',
        method: 'post'
    })
}

// 获取用户列表
export function getUserList(data) {
    return request({
        url: '/userList',
        method: 'get',
        params: data
    })
}

// 获取用户权限
export function getUserInfo() {
    return request({
        url: '/getInfo',
        method: 'get'
    })
}

// 根据id获取用户
export function getUserById(id) {
    return request({
        url: `/getUser/${id}`,
        method: 'get'
    })
}

// 新增用户
export function saveUser(data) {
    return request({
        url: '/saveUser',
        method: 'post',
        data: data
    })
}

// 修改用户
export function updateUser(data) {
    return request({
        url: '/updateUser',
        method: 'put',
        data: data
    })
}

// 删除用户
export function deleteUser(userId) {
    return request({
        url: `/deleteUser/${userId}`,
        method: 'delete'
    })
}