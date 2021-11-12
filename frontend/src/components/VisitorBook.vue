<template>
<div class="main">
  <!-- {{this.date}}
  {{(dateFormat(new Date()))}} -->
  <div class="container">
    <!-- {{visitor.guestbook.length}} -->
    <article class="date-carousel">
      <input type="button" class="date-carousel-prev" @click="prev()" value="&lt;">
      <input type="date" class="date-carousel-input" v-model="date">
      <input type="button" class="date-carousel-next" @click="next()" value="&gt;">
    </article>
    <hr>
    <div v-if="visitor.guestbook"  class ="write">
      <div v-if="this.date == dateFormat(new Date())">
        <WriteModal :list="visitor.guestbook"/>
      </div>
      <div v-else>
        <h1>오늘로 돌아가서 방명록을 남겨주세요!✍</h1>
      </div>
      
    </div>
    <br>
    <hr>
    <div v-if="visitor.guestbook">
      <div v-if="visitor.guestbook.length > 0" class = "list" >
        <VisitorBookDetail
          v-for="(visitorDetail,key) in visitor.guestbook"
          :key = key
          :visitorDetail = visitorDetail
        />
      </div>
      <div v-else>
        <br>
        <h3>방명록이 없습니다</h3>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import VisitorBookDetail from './VisitorBookDetail.vue';
const boardStore = 'boardStore'

import WriteModal from './WriteModal.vue';
export default {
  components: { WriteModal, VisitorBookDetail},
  name: 'VisitorBook',
  data() {
    return {
      date: '', // yyyy-mm-dd형식
      visitor: [],
      today: new Date(), // DateTime형식
    }
  },
  watch: {
    date() {
      // 문자열을 읽어서 다시 date()바꿔서 today넣어야된다.
      this.today = this.to_date2(this.date)
      // 당일 글 조회 용
      this.date = this.dateFormat(this.today)
      this.getVisitorBook(this.date)
      .then(()=>{
        // console.log(this.visitor_info(),'dddddd')
        this.visitor = this.visitor_info()
      }) 
    }
  },
  computed: {
    // ...mapGetters(boardStore, ['visitor_info']),

  },

  created() {
    // 당일 글 조회 용
    this.date = this.dateFormat(this.today)
    this.getVisitorBook(this.date)
    .then(()=>{
      // console.log(this.visitor_info(),'dddddd')
      this.visitor = this.visitor_info()
    }) 
  },

  methods: {
    ...mapActions(boardStore, ['getVisitorBook']),
    ...mapGetters(boardStore, ['visitor_info']),

    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();
      // let day = 26;

      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;
      return date.getFullYear() + '-' + month + '-' + day;
    },

    to_date2(date_str)
    {
        var yyyyMMdd = String(date_str);
        var sYear = yyyyMMdd.substring(0,4);
        var sMonth = yyyyMMdd.substring(5,7);
        var sDate = yyyyMMdd.substring(8,10);

        //alert("sYear :"+sYear +"   sMonth :"+sMonth + "   sDate :"+sDate);
        return new Date(Number(sYear), Number(sMonth)-1, Number(sDate));
    },

    prev() {
      // console.log(this.today,'today')
      this.today = new Date(this.today.setDate(this.today.getDate() - 1))
      console.log(this.today)
      this.date = this.dateFormat(this.today)

      this.getVisitorBook(this.date)
      .then(()=>{
        // console.log(this.visitor_info(),'dddddd')
        this.visitor = this.visitor_info()
      }) 

    },
    next() {
      // console.log(this.today,'today')
      this.today = new Date(this.today.setDate(this.today.getDate() + 1))
      // console.log(this.today)
      this.date = this.dateFormat(this.today)

      this.getVisitorBook(this.date)
      .then(()=>{
        // console.log(this.visitor_info(),'dddddd')
        this.visitor = this.visitor_info()
      }) 

    },

    
  },
  
  mounted() {
  },
  
}
</script>

<style scoped>
h1 {
  font-size: 1.875rem;
  font-weight: 300;
  margin: 60px 0 30px 0;
  color: black;
}
.main{
  width: 100vw;
  height: 100vh;
  background-image: url("../assets/gate.png");
  background-size: cover;
  background-position: center;
  padding-top: 60px;
}

.container {
  width: 1000px;
  max-width: 1000px;
  margin-left:  auto;
  margin-right: auto;

}

.list {
  margin-top: 30px;
}

.date-carousel {
  background-color: white;
  box-sizing: border-box;
  display: flex;
  height: 0.5in;
  margin: auto;
  position: relative;
  width:3in;
}

.date-carousel-next,
.date-carousel-prev,
.date-carousel-input {
  box-sizing: border-box;
  position:relative;
  border: none;
}

.date-carousel-next,
.date-carousel-prev {
  background-color: transparent;
  color: black;
  cursor: pointer;
  font-size: larger;
  font-weight: bold;
  width: 0.5in;
}

.date-carousel-input {
  color: black;
  background-color: transparent;
  text-align: center;
  /* width: 2in; */
  font-size: 1.5rem;
}

/* Disable Native UI */

.date-carousel-input::-webkit-inner-spin-button,
.date-carousel-input::-webkit-input-placeholder{
    display: none;
    -webkit-appearance: none;
}
</style>