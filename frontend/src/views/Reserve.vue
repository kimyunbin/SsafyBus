<template>
  <div class="reserve-page">
    <div class="reserve-box">
      <div class="status">
        <div class="date-box">
          <span @click="prevDate">
            <i class="fas fa-caret-left"></i>
          </span>
          <span class="date">
          {{ mdDates[now] }}
          </span>
          <span @click="nextDate">
            <i class="fas fa-caret-right"></i>
          </span>
        </div>
        <div class="box1">
          <div class="room-box">
            <div class="room">
              <p class="room__name">1번방</p>
              <hr>
              <div class="room__content" v-for="(status,idx) in statusRoom[1]" :key="idx">
                <p class="content-time">
                {{ status.reservation.substring(11,16) }}
                </p>
                <p class="content-user">
                  {{ status.user.nickname }}
                </p>
              </div>
            </div>
            <div class="room">
              <p class="room__name">2번방</p>
              <hr>
              <div class="room__content" v-for="(status,idx) in statusRoom[2]" :key="idx">
                <p class="content-time">
                {{ status.reservation.substring(11,16) }}
                </p>
                <p class="content-user">
                  {{ status.user.nickname }}
                </p>
              </div>
            </div>
            <div class="room">
              <p class="room__name">3번방</p>
              <hr>
              <div class="room__content" v-for="(status,idx) in statusRoom[3]" :key="idx">
                <p class="content-time">
                {{ status.reservation.substring(11,16) }}
                </p>
                <p class="content-user">
                  {{ status.user.nickname }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="table">
        <div class="table-box">
          <div class="title">스터디룸 예약
          </div>
          <div class="select-room">
            <p>방 선택</p>
            <div :class="{ 'yes-choice': room == 1, 'no-choice': room != 1 }" @click="selectRoom(1)">1</div>
            <div :class="{ 'yes-choice': room == 2, 'no-choice': room != 2 }" @click="selectRoom(2)">2</div>
            <div :class="{ 'yes-choice': room == 3, 'no-choice': room != 3 }" @click="selectRoom(3)">3</div>
          </div>
          <div class="select-date">
            <p>
              날짜 선택
            </p>
            <div>
            <span @click="prevDate2">
              <i class="fas fa-caret-left"></i>
            </span>
            <span class="date">
            {{ mdDates[reserve_num] }}
            </span>
            <span @click="nextDate2">
              <i class="fas fa-caret-right"></i>
            </span>
            </div>
          </div>
          <div class="select-time">
            <span v-for="(time,idx) in selectTimes" :key="idx">
              <span  v-if="room" class="time" :class="{'yes-time': yesTime == time && times[idx]  == true,'no-time': yesTime != time && times[idx]  == true,'x-time': times[idx] == false}" @click="selectTime(idx)">
              {{ time }}
              </span>
            </span>
          </div>
          <div class="select-team">
            <p>친구 초대</p>
            <div class="input-member">
              <input type="text" v-model="member" @keypress="searchMember">
              <span @click="searchMember">
                <i class="fas fa-search"></i>
              </span>
              <ul v-if="showList==true">
                <li  v-for="(user, idx) in users" :key="idx">
                  <div @click="addMember(user)">
                  {{ user.generation}}기 {{ user.classNum }}반 {{ user.nickname }}
                  </div>
                </li>
              </ul>
            </div>
          </div>
          <div class="member-list">
            <div v-for="(member, idx) in members" :key="idx">
              {{ member.nickname }}
              <span @click="deleteMember(idx)"><i class="fas fa-times"></i></span>
            </div>
          </div>
          <div class="input-password">
            <p>비밀번호</p>
            <input type="password" v-model="password">
          </div>
          <button @click="reserveStudyRoom">예약 완료</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { createInstance } from "@/api/index.js";
import { createInstance2 } from "@/api/index.js";

export default {
  data() {
    return {
      selectTimes: ['00','01','02','03','04','05','06','07','08','09','10','11','12',
      '13','14','15','16','17','18','19','20','21','22','23'],
      statusRoom: [],
      emptyRoom: [],
      dates: [],
      mdDates: [],
      times: [],
      room: 0,
      now: 0,
      yesTime: '',
      reserve_num: 0,
      member: '',
      password: '',
      users: [],
      members: [],
      showList: false,
      userid: [],
    }
  },
  created() {
    this.getSevendays()
    this.getSevendays2()
    this.statusStudyRoom(0)
    console.log(this.dates)
  },
  methods: {
    async statusStudyRoom(num) {
      const data = {
        date: this.dates[num]
      }
      console.log("스터디방 예약 현황")
      const instance = createInstance()
      const response = await instance.get(`/studyroom?date=${data['date']}`)
      this.statusRoom= response.data.kiosk
      console.log(this.statusRoom)
    },
    async emptyStudyRoom() {
      const data = {
        room: this.room,
        date: this.dates[this.reserve_num],
      }
      console.log("빈방 조회")
      const instance = createInstance()
      const response = await instance.get(`/studyroom/check?room=${data['room']}&date=${data['date']}`)
      console.log(response.data)
      this.times = response.data.time
    },
    async reserveStudyRoom(){
      if(this.room!=0 && this.password!= '' && this.yesTime != ''){
      const data = {
        room: this.room,
        date: this.dates[this.reserve_num]+'T'+this.yesTime+':'+'00',
        userid: this.userid,
        password: this.password,
      }
      const instance = createInstance2()
      await instance.post('/studyroom',data)
      this.statusStudyRoom(this.now)
      this.room = 0
      this.yesTime = ''
      this.reserve_num = 0
      this.userid = []
      this.members = []
      this.password = ''
      }
      else{
        alert('빈 칸을 다 채워주세요.')
      }
    },
    async searchMember(){
      const data = {
        nickname: this.member,
      }
      const instance = createInstance()
      const response = await instance.get(`users?nickname=${data['nickname']}`)
      console.log(response.data)
      this.users = response.data.users
      this.showList=true
    },
    customDate(date) {
      let year = date.getFullYear()
      let month = ('0' + (date.getMonth() + 1)).slice(-2);
      let day = ('0' + date.getDate()).slice(-2);
      return year + '-' + month +'-' + day
    },
    customDate2(date){
      let month = ('0' + (date.getMonth() + 1)).slice(-2);
      let day = ('0' + date.getDate()).slice(-2);
      return month +'.' + day
    },
    getSevendays(){
      let today = new Date()
      let year = today.getFullYear()
      let month = today.getMonth()
      let day = today.getDate()
      
      this.mdDates.push(this.customDate2(today))
      for (let i = 1; i<7;i++){
        let date = new Date(year, month, day+i)
        this.mdDates.push(this.customDate2(date))
      }
    },
    getSevendays2(){
      let today = new Date()
      let year = today.getFullYear()
      let month = today.getMonth()
      let day = today.getDate()
      
      this.dates.push(this.customDate(today))
      for (let i = 1; i<7;i++){
        let date = new Date(year, month, day+i)
        this.dates.push(this.customDate(date))
      }
    },
    nextDate() {
      if(this.now != 6){
        this.now += 1
        this.statusStudyRoom(this.now)
      }
    },
    prevDate() {
      if(this.now != 0){
        this.now -= 1
        this.statusStudyRoom(this.now)
      }
    },
    nextDate2() {
      if(this.reserve_num != 6){
        this.reserve_num += 1
        this.yesTime=''
        if(this.room != 0){
        this.emptyStudyRoom()
        }
      }
    },
    prevDate2() {
      if(this.reserve_num != 0){
        this.reserve_num -= 1
        this.yesTime=''
        if(this.room != 0){
        this.emptyStudyRoom()
        }
      }
    },
    selectRoom(num){
      if(this.room == num){
        this.room = 0
      }
      else{
        this.room = num
        this.emptyStudyRoom()
      }
    },
    selectTime(idx){
      if(this.yesTime == this.selectTimes[idx]){
        this.yesTime = ''
      }
      else{
      this.yesTime = this.selectTimes[idx]
      }
    },
    addMember(user){
      this.members.push(user)
      this.userid.push(user.userId)
      this.showList=false
      this.member = ''
    },
    deleteMember(idx){
      this.members.splice(idx,1)
      this.userid.splice(idx,1)
    }
  },
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

::-webkit-scrollbar{
    height: 5px;
    width: 4px;
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

.yes-choice{
  background: white;
  color: #17B0E7;
}
.no-choice{
  background: #39c3f5;
  color: white;
}
.yes-time{
  color: white;
  background-color: #67cef3;
  cursor: pointer;
}
.yes-time:hover{
  background-color: #67cef3;
  color: white;
  cursor: pointer;
}
.no-time{
  color: #17B0E7;
  background-color: white;
  cursor: pointer;
}
.no-time:hover{
  background-color: #67cef3;
  color: white;
  cursor: pointer;
}
.x-time{
  background-color: rgb(201, 201, 201);
  color: white;
}
.reserve-page{
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  .reserve-box{
    display: flex;
    justify-content: space-evenly;
    width: 1000px;
    height: 590px;
    .status{
      position: relative;
      width: 40%;
      height: 100%;
      background-color: #FFE651;
      border-radius: 7px;
      .date-box{
        position: absolute;
        left: 50%;
        transform: translate(-50%,-10%);
        padding: 9px 15px 8px 15px;
        z-index: 2000;
        border-radius: 5px;
        background-color: black;
        span {
          color: white;  
          font-size: 20px;
          svg{
            cursor: pointer;
          }
        }
        .date{
          margin: 0px 8px;
        }
      }
      .box1{
        border-radius: 4px;
        z-index: 1;
        position: absolute;
        left: 50%;
        top: 48%;
        transform: translate(-50%, -50%);
        width: 90%;
        height: 90%;
        background-color: white;
        -webkit-box-shadow: 2px 4px 4px 0px rgba(84,84,84,1);
        -moz-box-shadow: 2px 4px 4px 0px rgba(84,84,84,1);
        box-shadow: 2px 4px 4px 0px rgba(84,84,84,1);
        .room-box{
          margin: 50px 0px 0px 0px;
          padding: 0px 40px;
          height: 450px;
          overflow: auto;
          .room{
            margin-bottom: 30px;
            &__name{
              text-align: left;
              font-size: 15px;
              margin-bottom: 5px;
              margin-left: 10px;
            }
            &__content{
              display:flex;
              align-items: center;
              font-size: 14px;
              margin-left: 10px;
              .content-time{
                padding-top: 8px;
              }
              .content-user{
                margin-left: 80px;
                padding-top: 5px;
              }
            }
          }
        }
      }
    }
    .table{
      width: 40%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      .table-box{
        padding: 15px 10px;
        width: 75%;
        height: 65%;
        border-radius: 10px;
        background-color:rgba(#17B0E7, 1.0);
        .title{
          color: white;
          margin-bottom: 20px;
          font-size: 20px;
          font-weight: bold;
        }
        .select-room{
          margin-bottom: 15px;
          display: flex;
          justify-content: center;
          align-items: center;
          p{
            color: white;
            font-size: 14px;
            margin-right: 10px;
          }
          div{
            margin-left: 10px;
            width: 50px;
            padding: 3px 10px;
            font-size: 15px;
            border-radius: 5px;
            border: 2px solid white;
            cursor: pointer;
          }
          div:hover{
            background: white;
            color: #17B0E7;
          }
          div:active{
            background: white;
            color: #17B0E7;
            transform: translateY(2px);
          }
        }
        .select-date{
          color: white;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          p{
            color: white;
            font-size: 14px;
            margin-right: 15px;
            margin-left: 15px;
          }
          div {
            width: 170px;
            padding: 3px;
            background-color: white;
            border-radius: 5px;
            span{
              color: #17B0E7;
              font-size: 20px;
              margin-right: 10px;
              svg {
                cursor: pointer;
              }
            }
          }
        }
        .select-time{
          margin-top: 10px;
          margin-left: 15px;
          // border-radius: 5px;
          background-color: white;
          width: 245px;
          height: 70px;
          padding: 7px 3px;
          .time {
            // color: #17B0E7;
            border-radius: 5px;
            margin: 1px 2px;
            padding: 1px 1px 1px 3px;
            font-size: 15px;
          }
        }
        .select-team{
          margin-top: 15px;
          color: white;
          font-size: 14px;
          display: flex;
          p{
            margin-left: 15px;
            margin-right: 15px;
          }
          .input-member{
            background-color: white;
            border-radius: 5px;
            padding: 1px 0px;
            position: absolute;
            margin-left: 88px;
            border-left: 1px solid #17B0E7;
            border-right: 1px solid #17B0E7;
            border-bottom: 1px solid #17B0E7;
            ul{
              color: black;
              // background-color: #daf5ff;
              display : block;
              cursor: pointer;
              li{
                height: 23px;
                border-top: 1px solid #17B0E7;
                list-style: none;
                text-align: left;
                padding-left:7px;
                padding-top: 2px;
              }
              li:hover{
                background-color: #daf5ff;
              }
              li:active{
                background-color: #daf5ff;
              }
            }
            input{
              width: 152px;
              padding: 0px 8px;
            }
            span {
              color: #17B0E7;
              svg{
                margin-top: 2px;
                margin-right: 5px;
                padding: 1px;
                cursor: pointer;
              }
            }
          }
        }
        .member-list{
          background: white;
          width: 245px;
          height: 40px;
          margin-left: 15px;
          margin-top: 10px;
          // border-radius: 5px;
          overflow-x: auto;
          white-space:nowrap;
          padding: 0px 2px;
          display: flex;
          div {
            color: #17B0E7;
            margin:5px 4px 0px 4px;
            border: 2px solid #17B0E7;
            padding: 2px;
            height: 30px;
            border-radius: 8px;
            font-size: 14px;
            svg {
              margin:0px 2px;
              cursor: pointer;
            }
          }
          div:hover{
            background-color: rgba(#17B0E7,0.1);
          }
        }
        .input-password{
          display: flex;
          margin-top: 10px;
          p {
            color: white;
            margin-left: 18px;
            margin-right: 15px;
            font-size: 14px; 
          }
          input {
            background-color: white;
            border-radius: 5px;
            width: 172px;
            padding: 4px 8px 0px 8px;
          }
        }
        button {
          margin-top: 13px;
          background-color: #39c3f5;
          border-radius: 5px;
          font-weight: bold;
          padding: 5px;
          border: 2px solid white;
          color: white;
          cursor: pointer;
        }
        button:hover{
          background-color: white;
          color: #17B0E7;
        }
        button:active{
          background: white;
          color: #17B0E7;
          transform: translateY(2px);
        }
      }
    }
  }
}
</style>