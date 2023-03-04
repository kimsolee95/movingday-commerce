<template>
  <div>
    <div id="login-page">
      <h2>Log In (Customer) </h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">

          <b-form-group id="fieldset-1" label="Enter your email" label-for="input-1"
            valid-feedback="Thank you!">
            <b-form-input id="input-1" v-model="email" trim></b-form-input>
          </b-form-group>

          <b-form-group id="fieldset-1" label="Enter your password" label-for="input-1"
            valid-feedback="Thank you!">
            <b-form-input id="input-1" v-model="password" trim></b-form-input>
          </b-form-group>

          <div>
            <b-button block variant="success" @click="login()">Login</b-button>
          </div>

          <router-link to="/login/company">
          <b-button variant="info" id="company-link-btn">업체회원이신가요? 업체회원으로 로그인하기</b-button>
          </router-link>

        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {

  data() {

    return {
      email: '',
      password: ''
    }
  },

  methods: {
    
    login() {
      
      if (this.email && this.password) {

        let email = this.email;
        let password = this.password;
        let signForm = {
          "email": email,
          "password": password
        };

        // 확인 필요내용. 
        //api 부분 proxy에서 http://localhost:8081 포트로 바꿔서 보내주긴 하나 8080도 같이 호출이 된다?.. 
        this.$axios.post('/api/signin/customer', JSON.stringify(signForm), {
          headers: {
            "Content-Type": `application/json`,
          },
        })
        .then((response) => {

          if(response.status === 200) {
            console.log(JSON.stringify(response.data));
            this.$store.commit('setToken', response.data);
            this.$router.push("/"); //로그인 성공하면 main page 이동
          }
        })
        .catch(error => {
          alert(JSON.stringify(error));
        })

      } else {
        alert('이메일과 비밀번호를 모두 입력하세요');
        return false;
      }
    }
  }
}
</script>

<style>
#loginForm {
  width: 700px;
  margin: auto;
  padding-top: 50px;
}

#login-page {
  padding-top: 70px;
}

#company-link-btn {
  margin-top: 30px;
}
</style>