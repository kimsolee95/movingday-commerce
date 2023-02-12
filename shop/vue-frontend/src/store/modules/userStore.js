import router from '@/router'

const userStore = {
    state: {
        email: '',
        password: '',
        token: ''
    },
    mutations: {
        login: function (state, payload) {
            state.email = payload.email
            state.password = payload.password
            state.token = payload.token
        },
        loginCheck: function (state) {
            if (!state.token) {
                router.push({
                    name: 'login'
                }).catch(error => {
                    console.log(error)
                })
            }
        }

    }
}

export default userStore