<template>

    <div id="request-info-list">

        <b-alert show>전문업체의 제안상품</b-alert>

        <b-card
          border-variant="light"
          header="업체 정보"
          header-bg-variant="light"
          header-text-variant="dark"
          align="center"
          style="margin-top: 7px;"
        >
          <b-card-text>
            <div>{{ serviceProductInfo.company.name }}</div>
            <div>{{ serviceProductInfo.company.introduction }}</div>
            <div>tel: {{ serviceProductInfo.company.tel }}</div>
            <div>주소: {{ serviceProductInfo.company.address }}</div>
            <div>사업자등록번호: {{ serviceProductInfo.company.businessNumber }}</div>

          </b-card-text>
        </b-card>

        <b-card
          border-variant="light"
          header="상품 제안서"
          header-bg-variant="light"
          header-text-variant="dark"
          align="center"
          style="margin-top: 7px;"
        >
          <b-card-text>


            <div class="info-row">
              {{ serviceProductInfo.name }}
            </div>
            <div class="info-row">
              <span class="info">: {{ serviceProductInfo.outlineDescription }}</span>
            </div>

            <hr/>

            <div class="info-row">
              <b-icon icon="calendar-check" scale="2" variant="success"></b-icon>
              <span class="info">실행일시: {{ serviceProductInfo.executeDate }}</span>
            </div>

            <div class="info-row">
              <b-icon icon="coin" scale="2" variant="warning"></b-icon>
              <span class="info">가격: {{ serviceProductInfo.productPrice }} 원</span>
            </div>

          </b-card-text>
        </b-card>

        <b-card
              v-for="(productOption, id) in serviceProductInfo.productOptions" :key="id"
              border-variant="warning"
              header="상품옵션"
              header-bg-variant="transparent"
              align="center"
              style="margin-top: 7px;"
            >
              <b-card-text>

                <div class="info-row">
                  <span class="info">옵션명: {{ productOption.name }} </span>
                </div>

                <div class="info-row">
                  <b-icon icon="coin" scale="2" variant="success"></b-icon>
                  <span class="info">옵션 가격: {{ productOption.optionPrice }} 원</span>
                </div>

                <!-- <b-form-checkbox
                  id="checkbox-1"
                  v-model="status"
                  name="checkbox-1"
                  value="purchaseYn"
                  unchecked-value="purchaseYn"
                >
                  I accept the terms and use
                </b-form-checkbox> -->
                <input type="checkbox" :id="id" :name="id"> 해당 옵션 구매하기
              </b-card-text>
            </b-card>  

        <div class="btn-area">
          <b-button block variant="primary">해당 상품 주문하기</b-button>
        </div>
    </div>

</template>

<script>

export default {

    data() {
      return {
        id: this.$route.query.id,
        serviceProductInfo: {},
        requestBody: {}
      }
    },

    mounted() {
      this.getRequestList();
    },

    methods: {

      getRequestList() {
        this.$axios.get('/api/customer/service-product/' + this.id)
            .then((res) => {
                this.serviceProductInfo = res.data;
            }).catch((err) => {
                alert(err);
            })
      },

      // onRowSelected(id) {

      //   this.requestBody.id = id; //서비스상품id
      //       this.$router.push({
      //           name: 'managingServiceProducts',
      //           query: this.requestBody
      //   })
      // }
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

.info-row {
  margin-bottom: 15px;
}

.btn-area {
  margin-top: 15px;
}
</style>