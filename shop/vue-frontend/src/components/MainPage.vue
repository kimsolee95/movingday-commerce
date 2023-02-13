<template>
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
              <b-form-group label="Name" label-for="name-input" invalid-feedback="Name is required" :state="nameState">
                <b-form-input id="name-input" v-model="name" :state="nameState" required></b-form-input>
              </b-form-group>

              <b-form-group label="serviceAddress" label-for="serviceAddress-input" invalid-feedback="서비스주소지를 입력하세요" :state="serviceAddressState">
                <b-form-input id="serviceAddress-input" v-model="serviceAddress" :state="serviceAddressState" required></b-form-input>
              </b-form-group>

            </form>
          </b-modal>
        </div>
      </b-jumbotron>
    </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: 'MainPage',
  data() {
    return {
      name: '',
      nameState: null,
      submittedNames: [],

      serviceAddress: ''
    }
  },
  methods: {
    checkFormValidity() {

        const valid = this.$refs.form.checkValidity();
        
        this.nameState = valid;
        this.serviceAddressState = valid;
        return valid;
      },
      resetModal() {

        this.name = '';
        this.nameState = null;
        
        this.serviceAddress = '';
        this.serviceAddressState = null;
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
        // Push the name to submitted names
        this.submittedNames.push(this.name)
        // Hide the modal manually
        this.$nextTick(() => {
          this.$bvModal.hide('modal-prevent-closing')
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
</style>
