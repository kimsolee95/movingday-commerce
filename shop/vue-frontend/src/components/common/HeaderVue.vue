<template>
<div>
  <b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="/#/">Home</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav v-if="!this.$store.state.userStore.isLogin">
        <router-link to="/login">
        <b-nav-item href="/login">로그인</b-nav-item>
        </router-link>
      </b-navbar-nav>

      <b-navbar-nav v-if="!this.$store.state.userStore.isLogin">
        <router-link to="/sign-up/company">
        <b-nav-item href="/sign-up/company">회원가입</b-nav-item>
        </router-link>
      </b-navbar-nav>

      <b-navbar-nav v-if="this.$store.state.userStore.userType == 'CUSTOMER'">
        <router-link to="/customer/receivedProducts">
        <b-nav-item href="/customer/receivedProducts">제안 상품목록</b-nav-item>
        </router-link>
      </b-navbar-nav>

      <b-navbar-nav v-if="this.$store.state.userStore.userType == 'CUSTOMER'">
        <router-link to="/chat/list">
        <b-nav-item href="/chat/list">상품 1:1 문의</b-nav-item>
        </router-link>
      </b-navbar-nav>

      <b-navbar-nav v-if="this.$store.state.userStore.userType == 'COMPANY'">
        <router-link to="/company/chat/list">
        <b-nav-item href="/company/chat/list">고객 상품 1:1 문의</b-nav-item>
        </router-link>
      </b-navbar-nav>

      <b-navbar-nav v-if="this.$store.state.userStore.userType == 'COMPANY'">
        <router-link to="/company/requests">
        <b-nav-item href="/company/requests">고객 견적문의서</b-nav-item>
        </router-link>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-form>
          <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
          <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
        </b-nav-form>

        <b-nav-item-dropdown v-if="this.$store.state.userStore.isLogin" right>
          <!-- Using 'button-content' slot -->
          <template #button-content>
            <em>User</em>
          </template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
          <b-dropdown-item href="#" @click="logout()">로그아웃하기</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
export default {
	
  methods: {

    logout() {
      this.$store.dispatch('logout');
    }

  }

}
</script>