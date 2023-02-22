<template>
  <div>
    <div id="login-page">
      <h2>Log In (Company)</h2>
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

        </form>
      </div>
    </div>
  </div>
</template>

<script>

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

        this.$axios.post('/api/signin/company', JSON.stringify(signForm), {
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
</style>