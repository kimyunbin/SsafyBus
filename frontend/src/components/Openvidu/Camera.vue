<template>
  <div id="webcam-container">
    <div id="webcam-title">
      <p>{{location}}</p>
    </div>
    <div id="webcam-main">
      <div id="video-container" :class="{'screen-share' : data.share.active}">
        <div id="prev">
          <button class="webcam-button page-button" @click="page -= 1;" v-if="prev">
            <div class="fas fa-chevron-left"></div>
          </button>
        </div>
        <div id="videos" >
          <user-video :ss=true :class="{publisher : true, 'flex-item': true, 'width-40': setWidth40, 'width-30' : setWidth30}" :stream-manager="data.publisher" v-if="page == 0"></user-video>
          <user-video :ss=true :class="{subscribers : true, 'flex-item': true, 'width-40': setWidth40, 'width-30' : setWidth30}" v-for="(sub, idx) in pageSub" :key="idx" :stream-manager="sub"></user-video>
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
    <div id="webcam-nav">
      <button id="btnSetvideo" @click="updateStream(0)" class="webcam-button">
          <div v-if="!data.setting.publishVideo"><div id="unpublish-video" class="fas fa-video-slash"></div></div>
          <div v-else><div id="publish-video" class="fas fa-video"></div></div>
      </button>
      <button id="btnSetAudio" @click="updateStream(1)" class="webcam-button">
          <div v-if="!data.setting.publishAudio"><div id="unpublish-audio" class="fas fa-microphone-slash"></div></div>
          <div v-else><div id="publish-audio" class="fas fa-microphone"></div></div>
      </button>
      <button id="btnShareScreen" @click="shareScreen" class="webcam-button">
        <div v-if="!screenShare"><div id="unpublish-screen" class="fas fa-upload"></div></div>
        <div v-else><div id="publish-screen" class="fas fa-upload"></div></div>
      </button>
      <button id="btnLeaveSession" @click="leaveSession" class="webcam-button"><div id="leave-session" class="fas fa-phone-alt"></div></button>
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
    }
  },
  props :{
    data : Object,
    location : String,
  },
  created() {
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
      let screen = this.data.OV.initPublisher(undefined, {
        resolution: "1280x720",
        videoSource: "screen",
        publishAudio : this.data.setting.publishAudio,
      });

      screen.once("accessAllowed", () => {
        screen.stream
          .getMediaStream()
          .getVideoTracks()[0]
          .addEventListener("ended", () => {
            this.data.session.unpublish(screen);
            this.screenShare = false;
            this.data.share.active = false;
            this.data.share.screen = undefined;
            this.data.session.publish(this.data.publisher);
          });
        
        this.data.session.unpublish(this.data.publisher);
        this.screenShare = true;
        this.data.share.active = true;
        this.data.share.screen = screen;
        this.data.session.publish(this.data.share.screen);
      });
      screen.once("accessDenied", () => {
        console.warn("ScreenShare: Access Denied");
      });
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
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  height: 100%;
}
#webcam-title{
  width: 100%;
  height: 10%;
  text-align: center;
}
#webcam-main{
  width: 100%;
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#share-container{
  height: 80%;
  overflow: hidden;
  padding-right: 10px;
}

#video-container {
  display: flex;
  width: 100%;
  align-content: space-around;
  justify-content: space-between;
  align-items: center;
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
  /* flex-direction: row!important; */
}
.flex-row #next button{
  /* height: auto!important; */
}
.flex-row #prev button{
  /* height: auto!important; */
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
    margin: auto;
    width: 80%;
}

.screen-video{
  height: 100%;
}
.screen-video video{
  width: 100%;
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
  margin : 5px;
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
  height: 75px;
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
}
#btnLeaveSession{
  width: 45px;
  border-radius: 50px;
}
#leave-session{
  color : var(--color-white);
}
</style>