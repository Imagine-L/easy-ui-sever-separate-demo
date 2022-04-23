import store from "@/store";

export default {
    hasRole: {
        mounted(el, binding) {
            const {value} = binding
            const roles = store.getters && store.getters.roles
            // const roles = ['admin']
            console.log(roles)
            if (value && value instanceof Array && value.length > 0) {
                const hasPermissions = roles.some(permission => {
                    return value.includes(permission)
                })
                if (!hasPermissions) {
                    // 移除掉当前的element
                    el.parentNode && el.parentNode.removeChild(el)
                }
            } else {
                throw new Error(`请设置操作权限标签值`)
            }
        }
    },
    hasPermission: {
        mounted(el, binding) {
            const {value} = binding
            const all_permission = "*:*:*";
            const permissions = store.getters && store.getters.permissions
            if (value && value instanceof Array && value.length > 0) {
                const hasPermissions = permissions.some(permission => {
                    return all_permission === permission || value.includes(permission)
                })
                if (!hasPermissions) {
                    // 移除掉当前的element
                    el.parentNode && el.parentNode.removeChild(el)
                }
            } else {
                throw new Error(`请设置操作权限标签值`)
            }
        }
    }
}