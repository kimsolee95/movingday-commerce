<template>
    <div id="product-info">

    <b-card bg-variant="light">
        <b-form-group
        label-cols-lg="3"
        label="서비스 상품 입력 정보"
        label-size="lg"
        label-class="font-weight-bold pt-0"
        class="mb-0"
        >
        <b-form-group
            label="서비스 상품명: "
            label-for="productName"
            label-cols-sm="3"
            label-align-sm="right"
        >
            <b-form-input id="productName" v-model="productName"></b-form-input>
        </b-form-group>

        <b-form-group
            label="상품 상세 설명: "
            label-for="outlineDescription"
            label-cols-sm="3"
            label-align-sm="right"
        >
            <b-form-input id="outlineDescription" v-model="outlineDescription"></b-form-input>
        </b-form-group>

        <b-form-group
            label="서비스 상품 가격: "
            label-for="productPrice"
            label-cols-sm="3"
            label-align-sm="right"
        >
            <b-form-input id="productPrice" :type="'number'" v-model="productPrice"></b-form-input>
        </b-form-group>

        <b-form-group
            label="서비스 실행일: "
            label-for="dateValue"
            label-cols-sm="3"
            label-align-sm="right"
        >
            <b-form-datepicker id="example-datepicker" v-model="dateValue" class="mb-2"></b-form-datepicker>
        </b-form-group>

        <b-form-group
            label="서비스 시작 시간: "
            label-for="timeValue"
            label-cols-sm="3"
            label-align-sm="right"
        >
        <b-form-timepicker id="timeValue" v-model="timeValue" locale="en"></b-form-timepicker>
        </b-form-group>

        </b-form-group>
    </b-card>
    
    <div id="option-add-btn">
      <b-button block variant="outline-primary" @click="addProductOption"> + 상품 옵션 입력 추가</b-button>
    </div>

    <div id="option-area">
      <div class="option" v-for="productOption in productOptions" v-bind:key="productOption.name">
        <b-card
          border-variant="warning"
          header= "상품옵션"
          header-bg-variant="transparent"
          align="center"
        >
        <b-card-text>
          <b-form-group
            label="상품옵션명: "
            label-for="optionName"
            label-cols-sm="3"
            label-align-sm="left"
          >
            <b-form-input class="optionName" :type="'text'" v-model="productOption.name"></b-form-input>
          </b-form-group>
          <b-form-group
              label="상품옵션 가격: "
              label-for="optionPrice"
              label-cols-sm="3"
              label-align-sm="left"
            >
            <b-form-input class="optionPrice" :type="'number'" v-model="productOption.optionPrice"></b-form-input>
          </b-form-group>
        </b-card-text>

        </b-card>
      </div>
    </div>

    <div>
      <b-button block variant="primary" @click="registServiceProduct">서비스 상품 등록하기</b-button>
    </div>
    
  </div>

</template>

<script>

  export default {
    data() {
      return {

        serviceProductFrom: {}, //request body

        dateValue: '',
        timeValue: '',
        productName: '',
        outlineDescription: '',
        productPrice: 0,

        productOptions: [
          {
            name: '',
            optionPrice: 0,
            serviceProductId: 0
          }
        ],

        serviceRequestId: this.$route.query.id

      }
    },

    computed: {

      getExecuteDate: function() {
        return this.dateValue + "T" + this.timeValue;
      }

    },

    methods: {
      
      addProductOption: function() {
        this.productOptions.push({name: '', optionPrice: 0, serviceProductId: 0});
      },

      setRequestBody() {

        this.serviceProductFrom = {
          "executeDate": this.getExecuteDate,
          "name": this.productName,
          "outlineDescription": this.outlineDescription,
          "productOptions": this.productOptions,
          "productPrice": this.productPrice,
          "serviceRequestId": this.serviceRequestId
        }
      },

      registServiceProduct() {
        
        this.setRequestBody();
        console.log(JSON.stringify(this.serviceProductFrom));

        //고객 요청서 등록 API 호출
        //http://localhost:8081
        this.$axios.post('/api/company/service-product', this.serviceProductFrom, {
          headers: {
            "Content-Type": `application/json`
          },
        }).then((response) => {

            if (response.status === 400) {
              alert(response);
            }

            if (response.status === 200) {
              console.log(JSON.stringify(response.data));
              alert('선택한 고객님의 서비스요청서에 서비스상품등록을 완료하였습니다.');
            }
          })
          .catch(error => {

            if (error.response.status === 400) {
              alert(error.response.data.message);
            }
          })        
      }

    }
  }
</script>

<style>

#product-info {
  width: 1200px;
  margin: auto;
  padding-top: 50px;
  padding-bottom: 40px;
}

#option-add-btn {
 padding-top: 25px;
}

#option-area {
  padding-top: 25px;
  padding-bottom: 20px;
}

.option {
  padding-top: 25px;
}


</style>