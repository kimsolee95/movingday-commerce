<template>
  <div>
  <b-container fluid class="text-light text-center" style="padding-top: 57px">
    <b-row class="d-flex justify-content-center">
    <b-col cols="10">
      <b-jumbotron header="청소/시공" lead="요청서를 작성하면 무료로 견적을 받아보실 수 있습니다">
        <img alt="Vue logo" src="../assets/dust-pan.png" style="height: 150px; "/> 
        <img alt="Vue logo" src="../assets/interior-design.png" style="height: 150px; "/>
        <img alt="Vue logo" src="../assets/moving-truck.png" style="padding-left: 10px; height: 150px; "/>           
        <div style="padding-top: 20px;">

          <b-button v-b-modal.modal-prevent-closing variant="success">무료견적 받기</b-button>
          <b-modal 
            id="modal-prevent-closing" 
            ref="modal" 
            title="견적 요청서" 
            @show="resetModal" 
            @hidden="resetModal"
            @ok="handleOk"
          >
            <form ref="form" @submit.stop.prevent="handleSubmit">

              <b-form-group label="서비스 카테고리" label-for="serviceCategory-input" invalid-feedback="서비스 카테고리를 선택해주세요." >
                <b-form-select id="serviceCategory-intput" v-model="serviceCategorySelected" :options="serviceCategoryOptions" requierd></b-form-select>
              </b-form-group>

              <b-form-group label="서비스 받아볼 주소지" label-for="serviceAddress-input" invalid-feedback="서비스주소지를 입력하세요" :state="serviceAddressState">
                <b-form-input id="serviceAddress-input" v-model="serviceAddress" :state="serviceAddressState" required></b-form-input>
              </b-form-group>

              <div>
                <label for="example-datepicker">Choose a date</label>
                <b-form-datepicker id="example-datepicker" v-model="value" class="mb-2"></b-form-datepicker>
                <p>Value: '{{ value }}'</p>
              </div>

              <b-form-group label="장소 면적 (평)" label-for="placeArea-input" invalid-feedback="장소의 면적(평)를 입력하세요" :state="placeAreaState">
                <b-form-input id="placeArea-input" v-model="placeArea" :type="'number'" :state="placeAreaState" required></b-form-input>
              </b-form-group>

              <b-form-group label="장소 구조" label-for="placeShape-input" invalid-feedback="장소의 구조를 선택해주세요." >
                <b-form-select id="placeShape-intput" v-model="placeShapeSelected" :options="placeShapeOptions" requierd></b-form-select>
              </b-form-group>

              <b-form-group label="요구사항" label-for="detailRequest-input" invalid-feedback="서비스 시 전문가가 알고 있어야 할 내용을 입력해주세요." :state="detailRequestState">
                <b-form-input id="detailRequest-input" v-model="detailRequest" :state="detailRequestState" required></b-form-input>
              </b-form-group>

            </form>
          </b-modal>
        </div>
      </b-jumbotron>
    </b-col>
    </b-row>
  </b-container>

  <b-alert show variant="light">후기가 증명합니다.</b-alert>
  
  <div id="company-list">
    <b-card-group deck>
      <b-card title="Title" img-src="/images/company/2023/02/16/4019c79a-9782-4928-841b-21936fd1984e.png" img-alt="Image" img-top>
        <b-card-text>
          This is a wider card with supporting text below as a natural lead-in to additional content.
          This content is a little bit longer.
        </b-card-text>
        <template #footer>
          <small class="text-muted">Last updated 3 mins ago</small>
        </template>
      </b-card>
    
      <b-card title="Title" img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
        <b-card-text>
          This card has supporting text below as a natural lead-in to additional content.
        </b-card-text>
        <template #footer>
          <small class="text-muted">Last updated 3 mins ago</small>
        </template>
      </b-card>
    
      <b-card title="Title" img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
        <b-card-text>
          This is a wider card with supporting text below as a natural lead-in to additional content.
          This card has even longer content than the first to show that equal height action.
        </b-card-text>
        <template #footer>
          <small class="text-muted">Last updated 3 mins ago</small>
        </template>
      </b-card>
    </b-card-group>
  </div>

  </div>

</template>

<script>
// import axios from 'axios';
// import $axios from './utils/customaxios.js'


export default {
  name: 'MainPage',
  data() {
    return {

      submittedNames: [],

      customerRequestForm: {}, //request body

      serviceAddress: '',
      serviceAddressState: null,
      
      value: '',

      detailRequest: '',
      detailRequestState: null,

      placeArea: '',
      placeAreaState: null,

      serviceCategorySelected: null,
      serviceCategoryOptions: [
          { value: null, text: '서비스 카테고리를 선택하세요' },
          { value: 'CLEANING', text: '청소' },
          { value: 'CONSTRUCTION', text: '인테리어 시공' },
        ],

      placeShapeSelected: null,
      placeShapeOptions: [
        { value: null, text: '장소 구조를 선택하세요'},
        { value: 'APARTMENT', text: '아파트'},
        { value: 'SINGLE_HOUSE', text: '단독 주택'},
        { value: 'HOUSE', text: '빌라'}
      ]
    }
  },
  methods: {
    checkFormValidity() {

        const valid = this.$refs.form.checkValidity();
        
        if (this.value === null || this.value == '') {
          alert('서비스 희망날짜를 입력하세요.');
          return false;
        }

        if (this.serviceCategorySelected === null || this.serviceCategorySelected == '') {
          alert('서비스 카테고리를 입력하세요.');
          return false;
        }

        if (this.placeShapeSelected === null || this.placeShapeSelected == '') {
          alert('서비스 장소 구조를 입력하세요.');
          return false;
        }

        this.nameState = valid;
        this.serviceAddressState = valid;

        this.detailRequestState = valid;
        
        this.placeAreaState = valid;

        return valid;
      },

      setRequestBody() {
        
        this.customerRequestForm = {
          "desiredDate": this.value,
          "detailRequest": this.detailRequest,
          "placeArea": this.placeArea,
          "placeShape": this.placeShapeSelected,
          "serviceAddress": this.serviceAddress,
          "serviceCategory": this.serviceCategorySelected
        }
      },

      resetModal() {

        this.name = '';
        this.nameState = null;
        
        this.serviceAddress = '';
        this.serviceAddressState = null;
        this.value = '';

        this.detailRequest = '';
        this.detailRequestState = null;
        
        this.placeArea = '';
        this.placeAreaState = null;

        this.serviceCategorySelected = null;

        this.placeShapeSelected = null;
      },

      handleOk(bvModalEvent) {
        // Prevent modal from closing
        bvModalEvent.preventDefault()
        // Trigger submit handler
        this.handleSubmit()
      },
      handleSubmit() {
        // Exit when the form isn't valid
        if (!this.checkFormValidity()) {
          return
        }
        
        this.setRequestBody();

        // Push the name to submitted names
        // this.submittedNames.push(this.name)
        // Hide the modal manually
        this.$nextTick(() => {

          //고객 요청서 등록 API 호출
          //http://localhost:8081
          this.$axios.post('/api/customer/requests', this.customerRequestForm, {
            headers: {
              "Content-Type": `application/json`
            },
          }).then((response) => {

              if (response.status === 200) {
                console.log(JSON.stringify(response.data));
                alert('요청서를 접수하였습니다.');
              }
            })
            .catch(error => {
              alert(JSON.stringify(error));
            })

          //모달 hide
          this.$bvModal.hide('modal-prevent-closing');
        })

      }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1 {
    color: rgb(42, 59, 47);
  }
  p {
    color:rgb(42, 59, 47);
  }
  #company-list {
    width: 1000px;
    margin: auto;
    padding-top: 50px;
  }

</style>
