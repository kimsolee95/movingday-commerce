import axios from 'axios';
import store from '../store'

axios.interceptors.request.use(
  (config) => {

    console.log('인터셉터작동확인111======');
    const token = store.state.userStore.token;
    console.log('토큰확인---->' + JSON.stringify(token));

    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }

    console.log(`Bearer ${token}`);
    return config;
  }
  // ,

  // (error) => {
  //     return Promise.reject(error);
  // }
);

axios.interceptors.response.use(function (config) {
  return config
});

export default axios;