<template>
<div id="chat-container">
  <div id="chat-main"  v-if="showChatting">
    <div id="chat-title">
        <p v-if="showChatting">Chat</p>
    </div>
    <div id="receive-container">
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
              <tr>
                <td><p class="user-message">{{message.message}}</p></td>
              </tr>
          </table>
        </div>
    </div>
    <div id="input-container" v-if="showChatting">
      <div id="input-form">
      <input id="sendMessage" autocomplete="off" class="input-message" type="text" v-model="sendMessage" @keyup.enter="send">
      <div id="btnSendMessage" class="fas fa-location-arrow" @click="send"></div>
      </div>
    </div>
  </div>
  <div id="chat-nav">
    <div class="icon-bottom">
      <div @click="showChange" class="messenger">
        <div id="message-icon" class="fas fa-comments" :class="{active : showChatting}"></div>
        <div class="bell fas fa-circle" v-if="showBell"></div>
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
    showBell : function(){
        if(this.showChatting){
            this.changeBell()
            return false;
        }
        return this.data.receiveMessageBell;
    },
  },
  methods: {
    send(){
      if(this.sendMessage.length > 0){
        this.$emit('sendMessage', this.sendMessage);
        this.sendMessage = '';
      }
    },
    showChange(){
      this.showChatting = !this.showChatting
      this.data.receiveMessageBell = false;
    },
    changeBell(){
      this.data.receiveMessageBell = false;
    }
  },
}
</script>

<style scoped>
#chat-container{
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  background-color: #FFE65180;
  height: 100%;
  width: auto;
  right: 0% !important;
  border-top-right-radius: 30px;
  border-bottom-right-radius: 30px;
  -webkit-transition: all 0.3s ease-in-out; 
  -moz-transition: all 0.3s ease-in-out; 
  -o-transition: all 0.3s ease-in-out; 
  transition: all 0.3s ease-in-out;
}
#chat-nav{
    display: flex;
    flex-direction: column;
    min-width: 50px;
  -webkit-transition: all 0.3s ease-in-out; 
  -moz-transition: all 0.3s ease-in-out; 
  -o-transition: all 0.3s ease-in-out; 
  transition: all 0.3s ease-in-out;
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
    width: 30px;
    height: 30px;
    margin : auto;
    cursor: pointer;
}
#chat-nav #participant-icon{
    color : var(--color-white);
    width: 30px;
    height: 30px;
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
    color: #eb4d4b;
    font-size: 14px;
    position: absolute;
    right: 10px;
    cursor: pointer;
}
#chat-nav #live-container{
    width : 80%;
    margin : 10px auto 20px;
}
#chat-nav #live-circle{
    height: 6px;
    width: 6px;
    border-radius: 3px;
    vertical-align: middle;
    display: inline-block;
}
#chat-nav #participant-counter{
    display: inline-block;
    vertical-align: middle;
    margin: 0px 0px 0px 5px;
}
#chat-main{
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  background-color: #FFF;
  border-top: 5px solid #FFE65180;
  border-bottom : 5px solid #FFE65180;
  border-left : 5px solid #FFE65180;
  -webkit-transition: all 0.3s ease-in-out; 
  -moz-transition: all 0.3s ease-in-out; 
  -o-transition: all 0.3s ease-in-out; 
  transition: all 0.3s ease-in-out;

}
#chat-main #chat-title{
  width: 100%;
  margin: 10px;
  text-align: center;
  flex-grow: 0.5;
  flex-basis: 0;
  font-family: 'Nanum Gothic', sans-serif;
  font-weight: 800;
}
#chat-main #chat-title p{
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
    width: 100%;
    display: flex;
    flex-flow: column;
    padding: 0px 5px;
}
#receive-container .my-message{
  align-items: flex-end;
  text-align: right;
  margin-left : auto;
}
.my-message .user-message{
  padding: 6px 10px !important;
  border-radius: 6px 0 6px 0 !important;
  position: relative !important;
  background: #17B0E750 !important;
  color: #6c6c6c !important;
  font-size: 12px !important;
}
.my-message .user-message::after{
  content: "";
  position: absolute;
  border: 10px solid transparent;
  border-top: 10px solid #17B0E750;
  border-right: none;
  bottom: -20px;
  right: 9px;
  top: auto !important;
  left: auto !important;
}
#receive-container .user-profile{
  font-size: 12px;
  color: #191919;
}
#receive-container .user-profile .user-name{
    margin-left : 5px;
    width: auto;
}
#receive-container .message .user-message{
  padding: 6px 10px;
  border-radius: 6px 0 6px 0;
  position: relative;
  color: #6c6c6c;
  font-size: 12px;
  background: #FFE65150;
  /* border: 2px solid #FFE651; */
  align-self: flex-start;
}
.user-message::after{
  content: "";
  position: absolute;
  right: auto;
  bottom: auto;
  top: -20px;
  left: 9px;
  border: 10px solid transparent;
  border-bottom: 10px solid #FFE65150;
  border-left: none;
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
    border: 5px solid #17B0E750;
    border-radius: 10px 0 10px 0 !important;
    opacity: 1;
    padding: 5px;
    min-width: 250px;
}
#input-form input[type="text"]{
    display: inline-block;
    border: none;
    text-align: left;
    letter-spacing: 0px;
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