<template>
    <div id="chat-list">
        <b-alert show>1:1 서비스 상담</b-alert>

        <b-list-group>
        
        <b-list-group-item class="d-flex align-items-center">
            <b-avatar variant="primary" text="BV" class="mr-3"></b-avatar>
            <span class="mr-3">박테스트님</span>
            <b-alert show variant="warning">업체측 화면단</b-alert>
        </b-list-group-item>

        <b-list-group-item class="d-flex align-items-center">
            <b-avatar variant="info" src="https://placekitten.com/300/300" class="mr-3"></b-avatar>
            <span class="mr-3">김테스트님</span>
            <b-alert show variant="secondary">예산은 200000원 이상부터 가능합니다.</b-alert>
        </b-list-group-item>

        <!-- 실제 전송 메시지-->
        <b-list-group-item v-for="(message, idx) in recvList" :key="idx" class="d-flex align-items-center">
            <b-avatar variant="success" icon="people-fill" class="mr-3"></b-avatar>
            <span v-if="message.writer == userInfo.name" class="mr-3"> {{ userInfo.name }} 님</span>
            <span v-else class="mr-3"> {{ message.writer }} 님</span>
            
            <b-alert v-if="message.writer == userInfo.name" show variant="secondary">{{ message.message }}</b-alert>
            <b-alert v-else show variant="warning">{{ message.message }}</b-alert>
        </b-list-group-item>

        </b-list-group>

        <div id="message-form">
            <b-button block variant="info" id="message-btn" @click="sendMessage">입력하기</b-button>
            <b-form-textarea
            id="textarea"
            v-model="text"
            placeholder="내용을 입력하세요"
            rows="3"
            max-rows="6"
            ></b-form-textarea>
        </div>

    </div>

</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {

    data() {

        return {

            userInfo: {},
            requestBody: this.$route.query,
            roomId: this.$route.query.id,
            message: '',
            messages: [],
            text: '',
            recvList: []
        }
    },

    created() {
    
        this.getUserInfo();
        this.connect();
    },

    methods: {

        getUserInfo() {

            this.$axios.get('/api/company', {params: this.requestBody, headers: {}})
            .then((res) => {
                this.userInfo = res.data;
            }).catch((err) => {
                alert(err);
            })
        },

        sendMessage(e) {
            if(this.text !== '') {
                this.send();
                this.text = '';
            }
        },

        send() {
            console.log("Send message:" + this.message);
            if (this.stompClient && this.stompClient.connected) {
                
                const msg = {    
                roomId: this.roomId,
                message: this.text,
                writer: this.userInfo.name
                // sendAt: Date.now(),
                // isRequest: false,
                };

                this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
            }
        }, 
        
        connect() {

            const serverURL = "http://localhost:8081/stomp/chat"
            let socket = new SockJS(serverURL);
            this.stompClient = Stomp.over(socket);
            
            console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
            this.stompClient.connect(
                {},
                frame => {
                this.connected = true;
                console.log('소켓 연결 성공', frame);

                //채팅방 입장. (입장 시 메시지 보내기)
                let msg = { 
                roomId: this.roomId,
                message: '입장하였습니다.',
                writer: 'test',
                type: 'ENTER'
                };
                this.stompClient.send('/pub/chat/enter-room', JSON.stringify(msg));

                this.stompClient.subscribe("/sub/chat/room/" + this.roomId, res => {
                    console.log('구독으로 받은 메시지 입니다.');
                    console.log(JSON.stringify(res.body));
                    this.recvList.push(JSON.parse(res.body))
                });
                },
                error => {
                console.log('소켓 연결 실패', error);
                this.connected = false;
                } 
            );               
        
        }

    }


}
</script>

<style>
#chat-list {
  width: 960px;
  margin: auto;
  padding-top: 150px;
  padding-bottom: 150px;
}

#message-form {
 padding-top: 30px;
}

</style>