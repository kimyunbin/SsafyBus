<template>
<div id="chat-container">
  <div id="chat-main">
    <div id="chat-title">
        <p v-if="showChatting">Chat</p>
        <p v-if="showUsers">User</p>
    </div>
    <div id="receive-container" v-if="showChatting||showUsers">
        <div class="message"  v-for="(message, index) in data.receiveMessage" :key="index">
        <table class="message-table" v-if="message.sender.clientData !== user.nickname">
          <tr class="user-profile">
            <td class="user-name">{{message.sender.clientData}}</td>
          </tr>
          <tr>
            <td><p class="user-message">{{message.message}}</p></td>
          </tr>
        </table>
        <table class="message-table my-message" v-else>
            <tr class="user-profile">
              <td class="user-name">{{message.sender.clientData}}</td>
            </tr>
            <tr>
              <td><p class="user-message">{{message.message}}</p></td>
            </tr>
        </table>
        </div>
    </div>
    <div id="participant-container" v-if="showUsers">
        <div class="participant-info my-info">
            <p class="inline-p">{{user.userNickname}} (me)</p>
        </div>
        <div class="participant-info" v-for="(sub, index) in data.subscribers" :key="index">
            <p class="inline-p">{{JSON.parse(sub.stream.connection.data).userNickname}}</p>
        </div>
    </div>
    <div id="input-container" v-if="showChatting">
      <div id="input-form">
      <input id="sendMessage" class="input-message" type="text" v-model="sendMessage" @keyup.enter="send">
      <div id="btnSendMessage" class="fas fa-location-arrow" @click="send"></div>
      </div>
    </div>
  </div>
  <div id="chat-nav">
    <div class="icon" @click="showChange(0)">
      <button>bbbb</button>
    </div>
    <div class="icon-bottom">
      <div @click="showChange(1)" class="messenger">
        <i id="message-icon" class="fas fa-comments" :class="{active : showChatting}"></i>
        <!-- <div class="bell" v-if="showBell">fas fa-bell</div> -->
      </div>
      <div @click="showChange(2)">
        <div id="participant-icon" class="fas fa-user" :class="{active : showUsers}"></div>
      </div>
      <div id="live-container">
        <div id="live-circle"></div>
        <div id="participant-counter">{{ data.participants }}명</div>
      </div>
    </div>
      
  </div>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
const userStore = 'userStore'

export default {
  name : "Chat",
  data() {
    return {
      sendMessage : "",
      showChatting : false,
      showUsers : false,
      user : {},
    }
  },
  props :{
    data : Object,
  },
  updated() {
    let container = this.$el.querySelector("#receive-container,#participant-container");
    if(container !== null) container.scrollTop = container.scrollHeight;
  },
  created() {
    this.user = this.user_info
  },
  computed : {
    ...mapGetters(userStore, ['user_info']),
    // showBell : function(){
    //     if(this.showChatting){
    //         this.data.receiveMessageBell = false;
    //         return false;
    //     }
    //     return this.data.receiveMessageBell;
    // },
  },
  methods: {
    send(){
      if(this.sendMessage.length > 0){
        this.$emit('sendMessage', this.sendMessage);
        this.sendMessage = '';
      }
    },
    showChange(type){
      if(type == 1){
        this.showChatting = true;
        this.showUsers = false;
        this.data.receiveMessageBell = false;
      }else if(type == 2){
        this.showChatting = false;
        this.showUsers = true;
      }else{
        this.showChatting = false;
        this.showUsers = false;
      }
    }
  },
}
</script>

<style scoped>
#chat-container{
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    background-color: #17B0E7;
    height: 100%;
}
#chat-nav{
    display: flex;
    flex-direction: column;
}
#chat-nav .icon{
    margin: 10px;
    cursor: pointer;
}
#chat-nav .icon-bottom{
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    flex: 1;
}
#chat-nav #message-icon{
    color : var(--color-white);
    width: 50px;
    height: 50px;
    margin : auto;
    cursor: pointer;
}
#chat-nav #participant-icon{
    color : var(--color-white);
    width: 50px;
    height: 50px;
    margin : auto;
    cursor: pointer;
}
#chat-nav .messenger{
    position: relative;
}
#chat-nav .active{
    border : 1px solid var(--color-white);
    border-radius: 50px;
    vertical-align: -webkit-baseline-middle;
}
#chat-nav .bell{
    color: var(--color-mainYellow);
    font-size: 14px;
    position: absolute;
    right: 10px;
}
#chat-nav #live-container{
    width : 80%;
    margin : 10px auto 20px;
}
#chat-nav #live-circle{
    height: 6px;
    width: 6px;
    background-color: var(--color-green-live);
    border-radius: 3px;
    vertical-align: middle;
    display: inline-block;
}
#chat-nav #participant-counter{
    font-size : var(--font-size-14);
    font-family: 'AppleSDGothicNeoB';
    font-weight: var(--weight-regular);
    color : var(--color-green-live);
    display: inline-block;
    vertical-align: middle;
    margin: 0px 0px 0px 5px;
}
#chat-main{
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    background-color: #FFE651;
}
#chat-main #chat-title{
    width: 100%;
    border-bottom: 1px solid var(--color-grey-6);
    text-align: left;
    flex-grow: 0.5;
    flex-basis: 0;
}
#chat-main #chat-title p{
    font-size : var(--font-size-30);
    font-family: 'AppleSDGothicNeoEB';
    font-weight: var(--weight-regular);
    color : var(--color-white);
    margin : 25px 0px 0px 0px;
    padding-left: 20px;
    opacity: 1;
}
#receive-container{
    overflow-y: auto;
    width: 100%;
    height: 90%;
    flex-grow: 9;
    flex-basis: 0;
}
#receive-container .message{
    width : 100%;
    opacity: 1;
}
#receive-container .message-table{
    color: var(--color-white);
    text-align: left;
    padding : 10px;
    table-layout: fixed;
    border-collapse: separate;
    text-indent: initial;
    border-spacing: 2px;
}
#receive-container .my-message{
    text-align: right;
    margin-left : auto;
}
#receive-container .user-profile{
    vertical-align: baseline;
}
#receive-container .user-profile .user-img{
    width: 40px;
    height: 40px;
    border-radius: 40px;
    vertical-align: middle;
    border: 1px solid var(--color-grey-2);
}
#receive-container .user-profile .user-name{
    font-size: var(--font-size-16);
    font-family: 'AppleSDGothicNeoSB';
    font-weight: var(--weight-light);
    margin-left : 5px;
}
#receive-container .message .user-message{
    width: fit-content;
    font-size: var(--font-size-14);
    font-family: 'AppleSDGothicNeoSB';
    font-weight: var(--weight-light);
    word-break: break-all;
    white-space: normal;
    max-width: 200px;
    text-align: left;
    display: inline-block;
}
#input-container{
    width : 100%;
    border-top: 1px solid var(--color-grey-6);
    flex-grow: 0.5;
    flex-basis: 0;
}
#input-form{
    margin : 10px;
    background-color : var(--color-white);
    border: 1px solid var(--color-mainBlue);
    border-radius: 20px;
    opacity: 1;
    padding: 5px;
    min-width: 250px;
}
#input-form input[type="text"]{
    display: inline-block;
    border: none;
    text-align: left;
    letter-spacing: 0px;
    color: var(--color-grey-2);
    font-size : var(--font-size-16);
    font-family: 'AppleSDGothicNeoSB';
    font-weight: var(--weight-regular);
    margin : 0px 5px 0px 0px;
    width: 80%;
}

.input-message{
  background-color: #fff;
}

#input-form input[type="text"]:focus{
    outline:none;
}
#input-form #btnSendMessage{
    display: inline-block;
    color : var(--color-mainBlue);
    opacity: 1;
    font-size: 15px;
}

#participant-container{
    width: 100%;
    min-width: 270px;
    text-align: left;
    overflow-y: auto;
    flex-grow: 9;
    flex-basis: 0;
}
.my-info{
    font-size : var(--font-size-20)!important;
    font-family: 'AppleSDGothicNeoB'!important;
}
.participant-info{
    font-size : var(--font-size-18);
    font-family: 'AppleSDGothicNeoSB';
    font-weight: var(--weight-regular);
    color : var(--color-white);
    margin : 5px;
    padding : 5px;
}
.participant-info .user-img{
    width: 30px;
    height: 30px;
    border-radius: 30px;
    vertical-align: middle;
    border: 1px solid var(--color-grey-2);
}
.inline-p{
    display: inline-block;
    margin : 0px 5px;
}
#bts-icon{
    height: 50px;
    width: 50px;
    border-radius: 20px;
}
</style>