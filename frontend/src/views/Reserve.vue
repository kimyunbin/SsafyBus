<template>
  <div class="reserve-page">
    <div class="reserve-box">
      <div class="status">
        <div class="box1">
          예약현황
        </div>
      </div>
      <div class="table">
        <div>예약하기</div>
        <div>
          <div>1번방</div>
          <div>2번방</div>
          <div>3번방</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { createInstance } from "@/api/index.js";

export default {
  data() {
    return {
      statusRoom: [],
      emptyRoom: [],
      date: '',
    }
  },
  created() {
    this.statusStudyRoom()
  },
  methods: {
    async statusStudyRoom() {
      const data = {
        date: this.date
      }
      console.log("스터디방 예약 현황")
      const instance = createInstance()
      const response = await instance.get(`/studyroom/date=${data['date']}`)
      console.log(response.data)
    },
    async emptyStudyRoom() {
      const data = {
        room: this.room,
        date: this.date,
      }
      console.log("빈방 조회")
      const instance = createInstance()
      const response = await instance.get(`/studyroom/check?room=${data['room']}&date=${data['date']}`)
      console.log(response.data)
    },
    customDate(date) {
      this.paramsdate =
        date.substring(0, 4) +
        date.substring(5, 7) +
        date.substring(8, 10)
    },
  },
}
</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
.reserve-page{
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  .reserve-box{
    display: flex;
    width: 700px;
    height: 500px;
    .status{
      width: 50%;
      height: 100%;
    }
    .table{
      width: 50%;
      height: 60%;
    }
  }
}
</style>