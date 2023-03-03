import router from '@/router'

const userStore = {
    
    state: {

        token: '',
        isLogin: false,
        userType: ''
    },
    mutations: {
    
        setToken: function(state, payload) {
            state.token = payload;
            state.isLogin = true;
            state.userType = 'CUSTOMER';
        },

        setCompanyToken: function(state, payload) {
            state.token = payload;
            state.isLogin = true;
            state.userType = 'COMPANY';
        },

        clearToken: function(state) {
            state.token = '';
            state.isLogin = false;
            state.userType = '';
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