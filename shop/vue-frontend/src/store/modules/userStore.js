import router from '@/router'

const userStore = {
    
    state: {

        token: ''
    },
    mutations: {
    
        setToken: function(state, payload) {
            state.token = payload;
        },

        clearToken: function(state) {
            state.token = '';
        }
    },
    actions: {
        
        logout({commit}) {
            commit('clearToken');
        }
    },
    getters: {
    
        getToken(state) {
            return state.token;
        }
    }
}

export default userStore