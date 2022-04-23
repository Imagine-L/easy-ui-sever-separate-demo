import {checkToken, getUserInfo, login, logout} from "@/api/user"
import storage from "@/utils/storage";
import {actions, mutations, session} from "@/constant";
import router from "@/route";
import {ElMessage} from "element-plus";

const user = {
    state: {
        userName: '',
        nickName: '',
        token: '',
        perms: [],
        roles: []
    },
    getters: {
        isLogin(state) {
            return state.userName !== '' && state.token !== ''
        },
        permissions(state) {
            return state.perms
        },
        roles(state) {
            return state.roles
        }
    },
    mutations: {
        [mutations.SAVE_USER_NAME](state, userName) {
            state.userName = userName
        },
        [mutations.SAVE_NICK_NAME](state, nickName) {
            state.nickName = nickName
        },
        [mutations.SAVE_TOKEN](state, token) {
            state.token = token
        },
        [mutations.SAVE_PERMS](state, perms) {
            console.log("perms:")
            console.log(perms)
            state.perms = perms
        },
        [mutations.SAVE_ROLES](state, roles) {
            console.log("roles:")
            console.log(roles)
            state.roles = roles
        },
    },
    actions: {
        [actions.LOGIN]({commit}, user) {
            return new Promise((resolve, reject) => {
                login(user).then(resp => {
                    storage.saveSessionObject(session.SESSION_USER_TAG, resp.data)
                    commit(mutations.SAVE_USER_NAME, resp.data.user.userName)
                    commit(mutations.SAVE_NICK_NAME, resp.data.user.nickName)
                    commit(mutations.SAVE_TOKEN, resp.data.token)
                    resolve(resp)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        [actions.LOGOUT]({dispatch, commit}) {
            return new Promise((resolve, reject) => {
                logout().then(resp => {
                    dispatch(actions.EMPTY)
                    resolve(resp)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 从session中恢复数据
        [actions.RESTORE]({dispatch, commit}) {
            return new Promise((resolve, reject) => {
                // 先检查token是否合法
                checkToken().then(resp => {
                    return new Promise(resolve => {
                        resolve(resp)
                    })
                }).then(resp => {
                    // 合法则恢复
                    if (resp.status === 200) {
                        let loginUser = storage.getSessionObject(session.SESSION_USER_TAG);
                        if (loginUser !== null && loginUser !== '') {
                            console.log(loginUser)
                            commit(mutations.SAVE_USER_NAME, loginUser.user.userName)
                            commit(mutations.SAVE_NICK_NAME, loginUser.user.nickName)
                            commit(mutations.SAVE_TOKEN, loginUser.token)
                        }
                    } else {
                        // 不合法则清空
                        dispatch(actions.EMPTY)
                    }
                }).catch(error => {
                    reject(error)
                })
            })
        },
        [actions.EMPTY]({commit}) {
            storage.remove(session.SESSION_USER_TAG)
            commit(mutations.SAVE_USER_NAME, '')
            commit(mutations.SAVE_NICK_NAME, '')
            commit(mutations.SAVE_TOKEN, '')
        },
        [actions.GET_USER_INFO]({commit}) {
            return new Promise(resolve => {
                getUserInfo().then(resp => {
                    commit(mutations.SAVE_ROLES, resp.data.roles)
                    commit(mutations.SAVE_PERMS, resp.data.perms)
                    resolve()
                })
            })
        }
    }
}

export default user