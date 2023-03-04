<template>

    <div id="request-info-list">

        <b-alert show>내게 제안 온 상품 목록</b-alert>

        <b-card v-for="(item, id) in items" :key="id" bg-variant="white" text-variant="dark">
        
        <b-card-text class="mr-auto" >

        <span class="info">{{ item.name }}</span>, 
        
        <b-icon icon="coin" scale="2" variant="warning"></b-icon>
        <span class="info">{{ item.productPrice }} 원</span>

        <b-icon icon="calendar-check" scale="2" variant="info"></b-icon>
        <span class="info">이행일( {{ item.executeDate }} )</span>

        <b-icon icon="person-circle" scale="2" variant="light"></b-icon>
        <span class="info">{{ item.companyInfo.name }}</span>
        
        <a v-on:click="onRowSelected(`${item.id}`)">
        <b-button variant="light">상품 보기</b-button>
        </a>
        </b-card-text>
        </b-card>
    </div>

</template>

<script>

export default {

    data() {
      return {

        items: [],
        requestBody: {}
      }
    },

    mounted() {
      this.getReceivedProductList();
    },

    methods: {

      getReceivedProductList() {
        this.$axios.get('/api/customer/service-product', {params: this.requestBody, headers: {}})
            .then((res) => {
                this.items = res.data;
            }).catch((err) => {
                alert(err);
            })
      },

      onRowSelected(id) {

        this.requestBody.id = id; //서비스상품id
            this.$router.push({
                name: 'receivedProductDetail',
                query: this.requestBody
        })
      }
    }
}
</script>

<style>
#request-info-list {
  width: 1200px;
  margin: auto;
  padding-top: 50px;
}

.info {
  margin-left: 14px;
  margin-right: 30px;
}
</style>