<template>
  <div id="webcam-container">
    <div id="webcam-title">
      <div id="school-title">
        <!-- <img src="@/../public/Image/school_icon.png" id="school-icon"> -->
        <p>{{schoolName}}</p>
      </div>
    </div>
    <div id="webcam-main">
      <div id="share-container" v-if="data.share.active">
        <div id="share-screen" v-if="data.share.screen">
          <user-video class="flex-item screen-video" :stream-manager="data.share.screen"></user-video>
        </div>
      </div>
      <div id="video-container" :class="{'flex-column': data.share.active, 'screen-share' : data.share.active}">
        <div id="prev">
          <button class="webcam-button page-button" @click="page -= 1;" v-if="prev">
            <div v-if="!data.share.active">fas fa-chevron-left</div>
            <div v-else>fas fa-angle-up</div>
          </button>
        </div>
        <div id="videos" :class="{'flex-column': data.share.active}">
          <user-video :class="{publisher : true, 'flex-item': true, 'width-40': setWidth40, 'width-30' : setWidth30}" :stream-manager="data.publisher" v-if="page == 0"></user-video>
          <user-video :class="{subscribers : true, 'flex-item': true, 'width-40': setWidth40, 'width-30' : setWidth30}" v-for="(sub, idx) in pageSub" :key="idx" :stream-manager="sub"></user-video>
        </div>
        <div id="next">
          <button class="webcam-button page-button" @click="page += 1;" v-if="next">
            <div v-if="!data.share.active">fas fa-chevron-right</div>
            <div v-else>fas fa-angle-down</div>
          </button>
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
      schoolName : '',
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
    flex-wrap: nowrap;
    justify-content: space-around;
    height: 100%;
    border : 1px solid var(--color-grey-6);
    border-radius: 0px 20px 0px 0px;
    flex : 1;
}
#webcam-title{
    width: 100%;
    text-align: left;
    flex-grow: 1;
    flex-basis: 0;
}
#webcam-title #school-title{
    width: 90%;
    margin: 30px auto 0px;
}
#webcam-title #school-title p{
    display: inline-block;
    margin : 0px 0px 0px 10px;
    font-size : var(--font-size-30);
    font-family: 'AppleSDGothicNeoEB';
    font-weight: var(--weight-regular);
    vertical-align: bottom;
}
#school-icon{
    display: inline-block;
    height : 50px;
    width : 50px;
    vertical-align: middle;
}
#webcam-main{
    width: 100%;
    display: flex;
    flex-direction: row;
    flex-grow: 8;
    flex-basis: 0;
    align-items: center;
}
#webcam-main #share-container{
    border: 1px solid var(--color-grey-6);
    border-radius: 10px;
    padding: 10px;
    margin: 5px;
    flex-grow: 8;
    flex-basis: 0;
}
#webcam-main #video-container {
    display: flex;
    flex-wrap: nowrap;
    align-content: space-around;
    justify-content: space-around;
    align-items: center;
    flex-grow: 2;
    flex-basis: 0;
}
.screen-share{
    width : 20%!important;
    height: 100%!important;
}
.screen-share #videos{
    flex : none!important;
    width : 60%!important;
}
.screen-share #prev{
    height: 10%!important;
    width : 100%!important;
}
.screen-share #next{
    height: 10%!important;
    width : 100%!important;
}
#webcam-main #video-container #prev{
    flex-grow: 1;
    flex-basis: 0;
}
#webcam-main #video-container #next{
    flex-grow: 1;
    flex-basis: 0;
}
.flex-column{
    flex-direction: column!important;
}
.flex-column #next button{
    height: auto!important;
}
.flex-column #prev button{
    height: auto!important;
}
.flex-column .inactive-user-name{
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
    margin: auto;
    flex-grow: 8;
    flex-basis: 0;
}
.active-user {
    width: 100%;
    height: auto;
    border-radius : 20px;
    vertical-align: middle;
}
.active-user-name {
    width: 30%;
    position: absolute;
    bottom: 0px;
}
.active-user-name p {
    display: inline-block;
    background: var(--color-grey-5);
    padding : 5px;
    color: var(--color-grey-2);
    font-size : var(--font-size-16);
    font-family: 'AppleSDGothicNeoSB';
    font-weight: var(--weight-regular);
    border-bottom-left-radius: 20px;
    border-top-right-radius: 20px;
    width : 100%;
    margin : 0px!important;
    min-width: 80px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    vertical-align: bottom;
    text-align: center;
}
.inactive-user{
    position: absolute;
    width: 100%;
    height: 100%;
    bottom: 0px;
}
.inactive-user-img{
    width : 30%;
    border-radius: 50%;
    background-color: none;
    display: block;
    margin: auto;
    margin-top : 10%;
    margin-bottom: 5%;
}
.inactive-user-name{
    font-family: 'AppleSDGothicNeoSB';
    font-weight: var(--weight-regular);
    font-size: var(--font-size-30);
    color : var(--color-white);
    text-align: center;
}
.inactive-user-video{
    display: inline-block;
    position: absolute;
    right: 5%;
    bottom: 5%;
}
.inactive-user-audio{
    display: inline-block;
    position: absolute;
    right: 15%;
    bottom: 5%;
}
.inactive-user .inactive{
    font-size : 15px!important;
    color : var(--color-red)!important;
}

.screen-video video{
    border-radius: 0px;
    max-height: 600px;
}
.screen-video .active-user-name{
    display: none;
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
    border-top: 1px solid var(--color-grey-6);
    flex-grow: 1;
    flex-basis: 0;
}
.webcam-button{
    margin: 15px 10px;
    background-color : var(--color-white) 0% 0% no-repeat padding-box;
    border: 1px solid var(--color-grey-2);
    border-radius : 20px;
    width: 100px;
    height: 45px;
}
#publish-screen{
    color : var(--color-mainBlue);
}
#publish-video{
    color : var(--color-grey-2);
}
#unpublish-video{
    color : var(--color-red);
}
#publish-audio{
    color : var(--color-grey-2);
}
#unpublish-audio{
    color : var(--color-red);
}
#btnLeaveSession{
    width: 45px;
    border-radius: 50px;
    border-color: var(--color-red);
    background-color: var( --color-red);
}
#leave-session{
    color : var(--color-white);
}
</style>