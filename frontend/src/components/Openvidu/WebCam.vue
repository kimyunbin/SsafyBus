<template>
  <div id="session" v-if="data.session">
    <Camera :data="data" :location="location" v-on:leaveSession="leaveSession" v-on:updateStream="updateStream"/>
    <Chat :data="data" v-on:sendMessage="send"/>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import Camera from './Camera';
import Chat from './Chat';
import { mapGetters } from 'vuex'
const userStore = 'userStore'

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_URL = "https://k5c104.p.ssafy.io";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
export default {
    name : "Webcam",
    components : {
      Camera,
      Chat
    },
    data() {
      return {
        data : {
          OV: undefined,
          session: undefined,
          mainStreamManager: undefined,
          publisher: undefined,
          subscribers: [],
          participants : 1,
          roomName: "",
          setting: {
            audioSource: undefined,
            videoSource: undefined,
            publishAudio: false,
            publishVideo: false,
            resolution: "640x480",
            frameRate: 30,
            insertMode: "APPEND",
            mirror: false,
          },
          receiveMessage: [],
          receiveMessageBell : false,
          share : {
            active : false,
            screen : undefined,
          },
          // userName: "user1",
        },
        user : {},
        schoolName : '',
      }
    },
    props : {
      location : String,
    },
    created(){
      this.user = this.user_info.nickname;
      this.data.roomName = this.location;
      this.joinSession();

    },
    destroyed(){
      if (this.data.session) this.data.session.disconnect();
      this.data.session = undefined;
      this.data.mainStreamManager = undefined;
      this.data.publisher = undefined;
      this.data.subscribers = [];
      this.data.OV = undefined;
      this.data.receiveMessage = [];
      this.data.share.active = false;
      this.data.share.screen = undefined;
    },
    computed: {
      ...mapGetters(userStore, ['user_info']),
    },
    methods: {
      joinSession() {
        this.data.OV = new OpenVidu();
        this.data.session = this.data.OV.initSession();

        this.data.session.on("streamCreated", ({ stream }) => {
          const subscriber = this.data.session.subscribe(stream);
          if(subscriber.stream.typeOfVideo == "SCREEN"){
            this.data.share.active = true;
            this.data.share.screen = subscriber;
          }
          this.data.subscribers.push(subscriber);
          this.data.participants = this.data.subscribers.length+1;
        });
        this.data.session.on("streamDestroyed", ({ stream }) => {
          const index = this.data.subscribers.indexOf(stream.streamManager, 0);
          if(stream.typeOfVideo == "SCREEN"){
            this.data.share.active = false;
            this.data.share.screen = undefined;
          }
          if (index >= 0) {
            this.data.subscribers.splice(index, 1);
          }
          this.data.participants = this.data.subscribers.length+1;
        });
        this.data.session.on("signal:my-chat", (event) => {
          this.data.receiveMessage.push({sender : JSON.parse(event.from.data), message : event.data});
          this.data.receiveMessageBell = true;
        });

        this.getToken(this.data.roomName).then((token) => {
          this.data.session
            .connect(token, {clientData:this.user})
            .then(() => {
              let publisher = this.data.OV.initPublisher(undefined, this.data.setting);
              
              this.data.mainStreamManager = publisher;
              this.data.publisher = publisher;
              this.data.session.publish(this.data.publisher);
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message
              );
            });
        });

        window.addEventListener("beforeunload", this.data.leaveSession);
      },
      leaveSession() {
        if (this.data.session) this.data.session.disconnect();

        this.data.session = undefined;
        this.data.mainStreamManager = undefined;
        this.data.publisher = undefined;
        this.data.subscribers = [];
        this.data.OV = undefined;
        this.data.receiveMessage = [];
        window.removeEventListener("beforeunload", this.data.leaveSession);
        this.$router.push({name : 'Unity'});
      },
      getToken(roomName) {
        return this.createSession(roomName).then((sessionId) =>
          this.createToken(sessionId)
        );
      },
      createSession(sessionId) {
        return new Promise((resolve, reject) => {
          axios
            .post(
              `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
              JSON.stringify({
                customSessionId: sessionId,
              }),
              {
                auth: {
                  username: "OPENVIDUAPP",
                  password: OPENVIDU_SERVER_SECRET,
                },
              }
            )
            .then((response) => response.data)
            .then((data) => resolve(data.id))
            .catch((error) => {
              if (error.response.status === 409) {
                resolve(sessionId);
              } else {
                console.warn(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
                );
                if (
                  window.confirm(
                    `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                  )
                ) {
                  location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
                }
                reject(error.response);
              }
            });
        });
      },
      createToken(sessionId) {
        return new Promise((resolve, reject) => {
          axios
            .post(
              `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
              {},
              {
                auth: {
                  username: "OPENVIDUAPP",
                  password: OPENVIDU_SERVER_SECRET,
                },
              }
            )
            .then((response) => response.data)
            .then((data) => resolve(data.token))
            .catch((error) => reject(error.response));
        });
      },
      send(sendMessage) {
        this.data.session.signal({
            data: sendMessage,
            to: [],
            type: "my-chat",
        }).catch((error) => {
            console.error(error);
        });
      },
      updateStream(type){
        if (type == 1) {
          this.data.setting.publishAudio = !this.data.setting.publishAudio;
          this.data.publisher.publishAudio(this.data.setting.publishAudio);
        } else {
          this.data.setting.publishVideo = !this.data.setting.publishVideo;
          this.data.publisher.publishVideo(this.data.setting.publishVideo);
        }
      },
    },
}
</script>

<style scoped >
#session{
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 90%;
  height: 90%;
  margin: 10px;
  border-radius: 30px;
  background-color: #FFF;
  -webkit-transition: all 0.3s ease-in-out; 
  -moz-transition: all 0.3s ease-in-out; 
  -o-transition: all 0.3s ease-in-out; 
  transition: all 0.3s ease-in-out;
  font-family: 'Nanum Gothic', sans-serif;
}
</style>