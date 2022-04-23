import {createStore} from "vuex";
import actions from '@/store/actions'
import getters from '@/store/getters'
import mutations from '@/store/mutations'
import user from '@/store/modules/user'

const store = createStore({
    modules: {
        user
    },
    getters,
    mutations,
    actions
})

export default store