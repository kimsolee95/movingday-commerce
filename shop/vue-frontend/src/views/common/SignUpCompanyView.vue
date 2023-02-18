<template>
    <div id="signUpForm">
      <b-form @submit="onSubmit" v-if="show"> <!--  @reset="onReset"-->
  
        <b-form-group id="input-group-2" label="회사명" label-for="input-2">
          <b-form-input
            id="input-1"
            v-model="form.name"
            placeholder="Enter name"
            required
          ></b-form-input>
        </b-form-group>
  
        <b-form-group id="input-group-3" label="서비스 카테고리" label-for="input-3">
          <b-form-select
            id="input-2"
            v-model="form.serviceCategory"
            :options="serviceCategoryOptions"
            required
          ></b-form-select>
        </b-form-group>

        <b-form-group
          id="input-group-3"
          label="EMAIl"
          label-for="input-3"
          description="We'll never share your email with anyone else."
        >
          <b-form-input
            id="input-3"
            v-model="form.email"
            type="email"
            placeholder="Enter email"
            required
          ></b-form-input>
        </b-form-group>        

        <b-form-group id="input-group-4" label="PASSWORD" label-for="input-4">
          <b-form-input
            id="input-4"
            v-model="form.password"
            placeholder="Enter password"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-5" label="소개글" label-for="input-5">
          <b-form-input
            id="input-5"
            v-model="form.introduction"
            placeholder="회사 서비스에 대한 간략한 소개글을 작성해주세요"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-6" label="주소" label-for="input-6">
          <b-form-input
            id="input-6"
            v-model="form.address"
            placeholder="회사 주소를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-7" label="상세 주소" label-for="input-7">
          <b-form-input
            id="input-7"
            v-model="form.addressDetail"
            placeholder="회사 상세주소를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-8" label="우편번호" label-for="input-8">
          <b-form-input
            id="input-8"
            v-model="form.zipcode"
            placeholder="회사 주소 우편번호를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-9" label="회사 연락처" label-for="input-9">
          <b-form-input
            id="input-9"
            v-model="form.tel"
            placeholder="회사 연락처를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-10" label="사업자등록번호" label-for="input-10">
          <b-form-input
            id="input-10"
            v-model="form.businessNumber"
            placeholder="사업자등록번호를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>

        <!-- Styled -->
        <b-form-file
          v-model="uploadFile"
          :state="Boolean(uploadFile)"
          placeholder="Choose a file or drop it here..."
          drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="mt-3">Selected file: {{ uploadFile ? uploadFile.name : '' }}</div>        
  
        <b-button type="submit" block variant="success">Submit</b-button>
        <!-- <b-button type="reset" block variant="danger">Reset</b-button> -->
      </b-form>

      <!-- <b-card class="mt-3" header="Form Data Result">
        <pre class="m-0">{{ form }}</pre>
      </b-card> -->
    
    
    </div>
  </template>

<script>

export default {
    data() {
      return {
        form: {
          email: '',
          name: '',
          serviceCategory: null,
          address: '',
          addressDetail: '',
          zipcode: '',
          introduction: '',
          businessNumber: '',
          tel: ''
        },
        uploadFile: null,
        serviceCategoryOptions: [
          { value: null, text: '서비스 카테고리를 선택하세요' },
          { value: 'CLEANING', text: '청소' },
          { value: 'CONSTRUCTION', text: '인테리어 시공' },
        ],
        show: true
      }
    },
    methods: {

      setRequestForm() {

        const formData = new FormData();
        formData.append("uploadFile", this.uploadFile);
        formData.append("companySignUpForm", new Blob([JSON.stringify(this.form)], {type: "application/json"}));
        return formData;
      },

      onSubmit(event) {
        event.preventDefault();
        alert(JSON.stringify(this.form));

        const requestForm = this.setRequestForm();

        //업체 회원가입 API 호출
        //http://localhost:8081
        this.$axios.post('/api/signup/company', requestForm).then((response) => {

            if (response.status === 200) {
              console.log(JSON.stringify(response.data));
              alert('업체 회원 가입을 완료했습니다.');
            }
          })
          .catch(error => {
            alert(JSON.stringify(error));
          })        
      },
      // onReset(event) {
      //   event.preventDefault()
      //   // Reset our form values
      //   this.form.email = ''
      //   this.form.name = ''
      //   this.form.food = null

      //   // Trick to reset/clear native browser form validation state
      //   this.show = false
      //   this.$nextTick(() => {
      //     this.show = true
      //   })
      // }
    }
  }

</script>

<style>
#signUpForm {
  width: 700px;
  margin: auto;
  padding-top: 50px;
}

#login-page {
  padding-top: 70px;
}
</style>