<template>
  <div class="studyroom-page">
    <div class="studyroom-box">
      <h1 class="title">{{ roomId }}번방 입장</h1>
      <div class="room-list">
        <div v-for="(room, idx) in studyRoom" :key="idx">
          <div class="room-item">
          <p class="time">
          {{ room.reservation.substring(11,16) }}
          </p>
          <p class="name">
            {{ room.user.nickname }}
          </p>
          <button @click="openModal($event)" :id="room.reservation | timeCom">{{room.reservation | timeCom}}</button>
          </div>
        </div>
        <div v-if="emptyRoom" class="empty-room">
          아직 예약이 없습니다 😋<br>
          예약페이지로 가서 예약해주세요 🙏
        </div>
      </div>
      <button class="goBack" @click="goBack">나가기</button>
    <div v-if="isOpen" @click="isOpen = false" id="modal" class="modal-overlay">
    </div>
    <div v-if="isOpen" class="modal-window">
      <div>
      <span><i class="fas fa-lock"></i></span>
      <input type="password">
      </div>
      <button @click="goStudyroom">입장</button>
    </div>
    </div>
  </div>
</template>
<script>
import { createInstance } from "@/api/index.js";

export default {
  data() {
    return {
      today: '',
      studyRoom: [],
      isOpen : false,
      emptyRoom: false,
    }
  },
  props: {
    roomId: {
      type: String,Number
    }
  },
  created() {
    let today = new Date()
    this.today = this.customDate(today)
    this.getStudyRoom()
  },
  methods: {
    async getStudyRoom() {
      console.log("스터디방 현황")
      const instance = createInstance()
      const response = await instance.get(`/studyroom/${this.roomId}?date=${this.today}`)
      this.studyRoom= response.data.room[`${this.roomId}`]
      console.log(this.studyRoom)
      if(this.studyRoom.length == 0){
        this.emptyRoom = true
      }
    },
    customDate(date) {
      let year = date.getFullYear()
      let month = ('0' + (date.getMonth() + 1)).slice(-2);
      let day = ('0' + date.getDate()).slice(-2);
      return year + '-' + month +'-' + day
    },
    openModal(e){
      // console.log(e.currentTarget.id)
      if(e.currentTarget.id == '입장'){
        this.isOpen = true
      }
    },
    goStudyroom(){
      console.log(this.roomId)
      this.$router.push({name: 'Live', params: {location: this.roomId}})
    },
    goBack(){
      this.$router.push({name: "UnityGame"});
    }
  },
  filters: {
    timeCom(value){
      let today = new Date()
      let reserveHour = parseInt(value.substring(11,13))
      let hour = today.getHours()
      if(reserveHour < hour){
        return '마감'
      }
      else if(reserveHour == hour){
        return '입장'
      }
      else if(reserveHour > hour){
        return '대기'
      }
    }
  }
}
</script>
<style lang="scss" scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
input, button {
  border: none;
  outline: none;
  background: none;
  font-family: 'Open Sans', Helvetica, Arial, sans-serif;
}
.empty-room{
  margin-top: 15vh;
  font-size: 17px;
}
::-webkit-scrollbar{
    height: 5px;
    width: 3px;
}

/* 스크롤바 막대 설정*/
::-webkit-scrollbar-thumb{
    height: 17%;
    background-color: rgba(65, 60, 60, 0.2);
    /* 스크롤바 둥글게 설정    */
    border-radius: 10px;    
}

/* 스크롤바 뒷 배경 설정*/
::-webkit-scrollbar-track{
    background-color: rgba(0,0,0,0);
}
#마감{
  background-color: grey;
  border: 1px solid grey;
}
#입장{
  cursor: pointer;
  border: 1px solid #84a6b3;
  background-color: #17B0E7;
}
#입장:hover{
  background-color: white;
  color: #17B0E7;
}
#대기{
  border: 1px solid #FFE651;
  background-color: #FFE651;
}
.room-list{
  height: 410px;
  overflow: auto;
}
.modal-overlay{
  position: fixed;
  width: 400px;
  height: 510px;
  top: 45%;
  left: 50%;
  border-radius: 20px;
  transform: translate(-50%, -50%);
  background-color: rgba($color: white, $alpha:0.5);
}
.modal-window{
  position: fixed;
  height: 80px;
  width: 200px;
  border-radius: 15px;
  top: 45%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #17B0E7;
  div {
    margin: 10px 0px;
    padding: 0px 5px;
    svg{
      font-size: 13px;
      margin-right: 7px;
    }
  }
  input{
    width: 130px;
    border-radius: 50px;
    margin-left: 5px;
    margin-bottom: 5px;
    padding-left: 8px;
    padding-top: 2px;
    background: white;
  }
  button {
    border: 2px solid white;
    background-color: #2cb5e7;
    border-radius: 10px;
    padding: 2px 70px;
    cursor: pointer;
    color: white;
    font-weight: bold;
  }
  button:hover{
    background-color: white;
    color: #17B0E7;
  }
}
.studyroom-page{
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
    background-image: url("../assets/studyroom.png");
  background-size: cover;
  background-position: center;
  .studyroom-box{
    background-color: white;
    width: 410px;
    height: 530px;
    border: 3px solid #17B0E7;
    // background-color: #17B0E7;
    border-radius: 20px;
    padding: 15px 30px 10px 30px;
    .title{
      margin: 10px 0px 20px 0px;
      font-size: 27px;
    }
    .room-item{
      display: flex;
      justify-content: space-between;
      padding: 5px 5px 10px 10px;
      margin: 10px 5px 10px 0px;
      background-color: white;
      border-bottom: 1px solid #dbdbdb;
      .time{
        margin-top: 5px;
        font-size: 14px;
      }
      .name{
        margin-top: 2px;
        font-size: 15px;
      }
      button{
        border-radius: 10px;
        padding: 3px 13px;
        color: white;
        font-weight: bold;
      }
    }
    .goBack{
      border-radius: 10px;
      padding: 3px 13px;
      font-weight: bold;
      color: white;
      background-color: #17B0E7;
    }
  }
}
</style>