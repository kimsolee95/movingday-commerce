<template>

    <div id="request-info-list">

        <b-alert show>고객 견적문의 목록</b-alert>
        <b-card v-for="(item, id) in items" :key="id" bg-variant="white" text-variant="dark">
        <b-card-text class="mr-auto" >
        <b-icon icon="check-square" scale="2" variant="success"></b-icon>
        <span class="info">카테고리: {{ item.serviceCategory }}</span>, 
        
        <b-icon icon="person-bounding-box" scale="2" variant="warning"></b-icon>
        <span class="info">{{ item.serviceAddress }},</span>
        
        <b-icon icon="building" scale="2" variant="info"></b-icon>
        <span class="info">{{ item.placeShape }}</span>

        <b-icon icon="alarm" scale="2" variant="warning"></b-icon>
        <span class="info">희망일: {{ item.desiredDate }}</span>
        
        <a v-on:click="onRowSelected(`${item.id}`)">
        <b-button variant="light">상품 제안하기</b-button>
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
      this.getRequestList();
    },

    methods: {

      getRequestList() {
        this.$axios.get('/api/company/customer-requests', {params: this.requestBody, headers: {}})
            .then((res) => {
                this.items = res.data;
            }).catch((err) => {
                alert(err);
            })
      },

      onRowSelected(id) {

        this.requestBody.id = id;
            this.$router.push({
                name: 'managingServiceProducts',
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