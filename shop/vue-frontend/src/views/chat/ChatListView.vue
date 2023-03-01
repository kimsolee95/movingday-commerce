<template>
    <div id="chat-list">
        <b-alert show>1:1 서비스 상담</b-alert>

        <b-list-group>
            <b-list-group-item v-for="(chat, id) in chatList" :key="id" class="d-flex justify-content-between align-items-center">
                <a v-on:click="enterChatRoom(`${chat.id}`)">
                망개떡님 {{ chat.name }}
                <b-badge variant="primary" pill>14</b-badge>
                </a>
            </b-list-group-item>
        </b-list-group>
    </div>
</template>

<script>

export default {

    data() {
        return {
            requestBody: {},
            chatList: {}
        }
    },

    mounted() {
        this.getChatList();
    },

    methods: {

        getChatList() {
            this.$axios.get('http://localhost:8081/chat/rooms', {params: this.requestBody, headers: {}})
            .then((res) => {
                this.chatList = res.data;
            }).catch((err) => {
                alert(err);
            })
        },

        enterChatRoom(id) {
            this.requestBody.id = id;
            this.$router.push({
                name: 'chatroom',
                query: this.requestBody
            })
        }

    },



}
</script>

<style>

#chat-list {
  width: 800px;
  margin: auto;
  padding-top: 150px;
  padding-bottom: 150px;
}

</style>