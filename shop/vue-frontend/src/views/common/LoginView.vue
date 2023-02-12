<template>
    <div>
      <div>
        <h2>Log In</h2>
        <div id="loginForm">
          <form @submit.prevent="fnLogin">
            <p>
              <input class="w3-input" name="email" placeholder="Enter your EMAIL" v-model="email"><br>
            </p>
            <p>
              <input name="password" class="w3-input" placeholder="Enter your password" v-model="password" type="password">
            </p>
            <p>
              <button type="button" class="w3-button w3-green w3-round" @click="login()">Login</button>
            </p>
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

          axios.post('/api/signin/customer', JSON.stringify(signForm), {
            headers: {
              "Content-Type": `application/json`,
            },
          })
          .then((response) => {

            if(response.status === 200) {
              console.log(JSON.stringify(response.data));
              this.$store.commit('setToken', response.data);
            }
          })
          .catch(error => {
            alert(JSON.stringify(error.response));
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
  }
  </style>