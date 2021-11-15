<template>
  <div id="webcam-container">
    <div id="webcam-title">
      {{location}}
    </div>
    <div id="webcam-main" v-if="sunsu">
      <div id="video-container" :class="{'screen-share' : data.share.active}">
        <div id="prev">
          <button class="webcam-button page-button" @click="page -= 1;" v-if="prev">
            <div class="fas fa-chevron-left"></div>
          </button>
        </div>
        <div id="videos" >
          <user-video :ss=true :class="{publisher : true, 'flex-item': true, 'width-40': setWidth40, 'width-30' : setWidth30}" :stream-manager="data.publisher" v-if="page == 0"></user-video>
          <user-video :ss=true :class="{subscribers : true, 'flex-item': true, 'width-40': setWidth40, 'width-30' : setWidth30}" v-for="sub in pageSub" :key="sub.stream.connection.connectionId" :stream-manager="sub"></user-video>
        </div>
        <div id="next">
          <button class="webcam-button page-button" @click="page += 1;" v-if="next">
            <div class="fas fa-chevron-right"></div>
          </button>
        </div>
      </div>
      <div id="share-container" v-if="data.share.active">
        <div class="screen-video">
          <user-video class="screen-video" :ss=false :stream-manager="data.share.screen"></user-video>
        </div>
      </div>
    </div>
    <div id="webcam-main" v-else>
     <div class="sl-logo">
       <div class="sl-top">
        <div class="sl-content">
          <div>S<span>AMSUNG  </span></div>
          <div>S<span>W  </span></div>
          <div>A<span>CADEMY  </span></div>
          <div>F<span>OR  </span></div>
          <div>Y<span>OUTH  </span></div>
        </div>
        <div class="sl-right"></div>
       </div>
       <div class="sl-bottom">
         <div class="sb-left"></div>
         <div class="sb-center"></div>
         <div class="sb-right"></div>
       </div>
      </div>
    </div>
    <div id="webcam-nav">
      <button id="btnSetvideo" @click="updateStream(0)" :class="{'webcam-button':true, 'ctr-btn':true, 'ctr-btn-on':data.setting.publishVideo}">
        <div v-if="data.setting.publishVideo"><div id="unpublish-video" class="fas fa-video-slash"></div></div>
        <div v-else><div id="publish-video" class="fas fa-video"></div></div>
      </button>
      <button id="btnSetAudio" @click="updateStream(1)" :class="{'webcam-button':true, 'ctr-btn':true, 'ctr-btn-on':data.setting.publishAudio}">
        <div v-if="data.setting.publishAudio"><div id="unpublish-audio" class="fas fa-microphone-slash"></div></div>
        <div v-else><div id="publish-audio" class="fas fa-microphone"></div></div>
      </button>
      <button id="btnShareScreen" @click="shareScreen" :class="{'webcam-button':true, 'ctr-btn':true, 'ctr-btn-on':screenShare}">
        <div><div id="unpublish-screen" class="fas fa-upload"></div></div>
      </button>
      <button id="btnLeaveSession" @click="leaveSession" :class="{'webcam-button':true, 'ctr-btn':true, 'ctr-btn-on':true}"><div id="leave-session" class="fas fa-phone"></div></button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import UserVideo from "./UserVideo";
axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "Camera",
  components: {
    UserVideo,
  },
  data() {
    return {
      page : 0,
      screenShare : false,
      maxHeight : 0,
      sunsu:false,
      cnt:3
    }
  },
  props :{
    data : Object,
    location : String,
  },
  created() {
    setInterval(() => {
      this.sunsu = true
    }, 2700)
  },
  mounted() {
    const target = document.querySelector('#webcam-main')
    const targetRect = target.getBoundingClientRect();
    this.maxHeight = targetRect.height;
  },
  updated(){
    const container = document.querySelector('#mostPopular-title');
    if(container !== null) container.setAttribute('style', `z-index:unset;`);
    const screen = document.querySelector('.screen-video video');
    if(screen !== null) screen.setAttribute('style', `max-height:${this.maxHeight-80}px;`);
  },
  computed : {
    setWidth40 : function(){
      if(this.data.subscribers.length < 2 && !this.data.share.active){
        return true;
      }
      return false;
    },
    setWidth30 : function(){
      if(this.data.subscribers.length >= 2 && !this.data.share.active){
        return true;
      }
      return false;
    },
    next : function(){
      if((!this.data.share.active && this.data.subscribers.length+1 - (this.page+1)*6 > 0 ) || (this.data.share.active && this.data.subscribers.length+1 - (this.page+1)*4 > 0 )){
        return true;
      }
      return false;
    },
    prev : function(){
      if(this.page > 0){
        return true;
      }
      return false;
    },
    totalPage : function(){
      let remain = (this.data.subscribers.length+1)%6;
      if(remain != 0){
        return (this.data.subscribers.length+1)/6+1;
      }
      return (this.data.subscribers.length+1)/6;
    },
    pageSub : function(){
      if(this.page == 0){
        if(!this.data.share.active){
          return this.data.subscribers.slice(0,5);
        }
        return this.data.subscribers.slice(0,3);
      }else{
        if(!this.data.share.active){
          return this.data.subscribers.slice(this.page*5, Math.min(this.page*5+6,this.data.subscribers.length));
        }
        return this.data.subscribers.slice(this.page*3, Math.min(this.page*3+4,this.data.subscribers.length));
      }
    },
  },
  methods: {
    updateMainVideoStreamManager(stream) {
      if (this.data.mainStreamManager === stream) return;
      this.data.mainStreamManager = stream;
    },
    updateStream(type) {
      this.$emit('updateStream', type);
    },
    shareScreen() {
      this.$emit('shareScreen')
    },
    leaveSession() {
        this.data.share.active = false;
        this.$emit('leaveSession');
    },
  },
};
</script>

<style scoped>
#webcam-container{
  /* min-width: 80%; */
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  height: 100%;
  background-color: #17B0E750;
  border-top-left-radius: 30px;
  border-bottom-left-radius: 30px;
}
#webcam-title{
  width: 100%;
  height: 7%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30px;
  font-weight: 700;
  font-family: 'Nanum Gothic', sans-serif;
}
#webcam-main{
  width: 100%;
  height: 86%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#share-container{
  height: 80% !important;
  overflow: hidden;
  padding-right: 10px;
}

#video-container {
  display: flex;
  width: 100%;
  align-content: space-around;
  justify-content: space-between;
  align-items: center;
  overflow: hidden;
  margin-bottom: 10px;
}

.screen-share{
  height: 20%!important;
}
.screen-share #videos{
  flex : 1 !important;
  width : 10%!important;
  flex-direction: row;
}
.screen-share #videos .flex-item{
  width: 10% !important;
  height: 100%;
  /* height: 10% !important; */
}
.screen-share #prev{
    height: 10%!important;
    width : 10%!important;
}
.screen-share #next{
    height: 10%!important;
    width : 10%!important;
}
#webcam-main #video-container #prev{
    flex-grow: 1;
    flex-basis: 0;
}
#webcam-main #video-container #next{
    flex-grow: 1;
    flex-basis: 0;
}
.flex-row{
  flex-direction: row!important;
}
.flex-row #next button{
  height: auto!important;
}
.flex-row #prev button{
  height: auto!important;
}
.flex-row .inactive-user-name{
    font-size: initial;
}
.page-button{
    margin : 0px;
    width : 80%;
}
#videos {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-content: center;
    width: 80%;
    height: 100%;
}

.screen-video{
  height: 100%;
}
.screen-video .active-user-name{
  display: none;
}
.share-screen .inactive-user{
  display: none !important;
}
.flex-item {
  flex: 1 auto;
  position:relative;
  padding-left : 5px;
  padding-right: 5px;
  padding-top: 10px;
  /* height: 100%; */
}
.width-40{
  max-width: 40%;
}

.width-40 .inactive-user-name{
  font-size: var(--font-size-30);
}

.width-30 {
  max-width : 30%;
}

#webcam-nav{
  display: flex;
  flex-wrap: nowrap;
  flex-direction: row;
  justify-content: center;
  align-content: center;
  align-items: center;
  width: 100%;
  height: 7%;
  flex-grow: 1;
  flex-basis: 0;
}
.webcam-button{
  margin: 15px 10px;
  background-color : var(--color-white) 0% 0% no-repeat padding-box;
  border: 1px solid var(--color-grey-2);
  border-radius : 20px;
  width: 100px;
  height: 45px !important;
  cursor: pointer;
}

.ctr-btn{
  background-color: #bdc3c7;
  -webkit-transition: all 0.3s ease-in-out; 
  -moz-transition: all 0.3s ease-in-out; 
  -o-transition: all 0.3s ease-in-out; 
  transition: all 0.3s ease-in-out;
}

.ctr-btn:hover{
  background-color: #6ab04c;
}

.ctr-btn-on:hover{
  background-color: #eb4d4b !important;
}

#btnLeaveSession{
  width: 45px;
  border-radius: 50px;
}
#leave-session{
  color : var(--color-white);
}

.sl-logo{
  /* background-color: #FFF; */
  height: 183px;
  width: 258px;
  position: relative;
}
.sl-top{
  width: 100%;
  height: 150px;
  display: flex;
  overflow: hidden;
}

.sl-content{
  background-color: black;
  width: 225px;
  height: 150px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-left: 20px;
  overflow: hidden;
  /* justify-content: space-around; */
}

.sl-content div:first-child{
  padding-top: 20px !important;
}

.sl-content div{
  padding: 0%;
  margin: 0%;
  color: #17B0E7;
  font-size: 22px;
  font-weight: 800;
  display: flex;
  overflow: hidden;
}

.sl-content div span{
  padding: 0%;
  margin: 0%;
  color: aliceblue;
  position: relative;
}

.sl-content div span::after{
  content: "";
  position: absolute;
  left: 0px;
  height: 30px;
  width: 100%;
  background-color: black;
  overflow: hidden;
  animation: typing 1.5s infinite;
}

@keyframes typing{
  100%{
    left: 100%;
  }
}

.sl-right{
  content: '';
  width: 33px;
  height: 160px;
}

.sl-bottom{
  height: 33px;
  width: 100%;
  display: flex;
}

.sb-left{
  width: 0px;
  height: 0px;
  border-top: 33px solid black;
  border-left: 33px solid transparent;
}

.sb-center{
  width: 207px;
  height: 33px;
  background-color: black;
}

.sb-right{
  width: 0px;
  height: 0px;
  border-bottom: 33px solid black;
  border-right: 33px solid transparent;
}

</style>