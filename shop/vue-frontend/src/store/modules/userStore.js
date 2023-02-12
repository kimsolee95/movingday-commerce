import router from '@/router'

const userStore = {
    
    state: {

        token: ''
    },
    mutations: {
    
        setToken: function(state, payload) {
            state.token = payload;
        }
    }
}

export default userStore