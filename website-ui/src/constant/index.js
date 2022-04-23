export const actions = {
    LOGIN: 'LOGIN',
    LOGOUT: 'LOGOUT',
    RESTORE: 'RESTORE',
    EMPTY: 'EMPTY',
    GET_USER_INFO: 'GET_USER_INFO'
}

export const mutations = {
    SAVE_USER_NAME: 'SAVE_USER_NAME',
    SAVE_NICK_NAME: 'SAVE_NICK_NAME',
    SAVE_TOKEN: 'SAVE_TOKEN',
    SAVE_PERMS: 'SAVE_PERMS',
    SAVE_ROLES: 'SAVE_ROLES'
}

export const session = {
    SESSION_USER_TAG: 'LOGIN_USER'
}

export const route_white_list = [
    'login'
]