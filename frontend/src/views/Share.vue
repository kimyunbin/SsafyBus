<template>
  <div>
    {{share_list}}
    <div class="my-board">
      <h2>공유 리스트</h2>
    <div class="head">
      <p><i class="fas fa-times-circle exit-icon"></i></p>
    </div>


    <table class="list-box">
      <thead class="th-list">
        <tr>
          <th class="th-no">no</th>
          <th class="th-title">제목</th>
          <th class="th-id">작성자</th>
          <th class="th-date">날짜</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, idx) in share_list" :key="idx">
          <td>{{idx+1}}</td>
          <td class="txt_left">
            <a class="a-title" @click="detailClick(row.id)">{{row.share.title}}</a>
          </td>
          <td>{{row.share.user.nickname}}</td>
          <td>{{row.createdAt}}</td>
        </tr>
      </tbody>
    </table>
    <br>

    <button class="btn" @click="writeClick()">질문하기</button>

		<!-- <div class="pagination" v-if="paging.totalCount > 0">
			<a href="javascript:;" @click="fnPage(1)" class="first">&lt;&lt;</a>
			<a href="javascript:;" v-if="paging.start_page > 10" @click="fnPage(`${paging.start_page-1}`)"  class="prev">&lt;</a>
			<template v-for=" (n,index) in paginavigation()">
				<template v-if="paging.page==n">
					<strong :key="index">{{n}}</strong>
				</template>
				<template v-else>
					<a href="javascript:;" @click="fnPage(`${n}`)" :key="index">{{n}}</a>
				</template>
			</template>
			<a href="javascript:;" v-if="paging.total_page > paging.end_page" @click="fnPage(`${paging.end_page+1}`)"  class="next">&gt;</a>
			<a href="javascript:;" @click="fnPage(`${paging.total_page}`)" class="last">&gt;&gt;</a>
		</div> -->

      <!-- <div class="btnRightWrap">
        <a  class="btn">등록</a>
      </div> -->
  </div>
  </div>
</template>

<script>
import { mapActions, mapGetters} from 'vuex'
const boardStore = 'boardStore'
export default {
  name: "Share",
  data() {
    return {
      share_list: {},
    }
  },
  created() {
  this.getShareList()
  .then(()=>{
    this.share_list = this.share_info()
    for (let i = 0; i < this.share_list.length; i++) {
      const createdAt = this.share_list[i].createdAt
      this.share_list[i].createdAt = this.dateFormat(new Date(createdAt))
      }
     })
  },
  methods: {
    ...mapActions(boardStore, ['getShareList']),
    ...mapGetters(boardStore, ['share_info']),
    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();
      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;
      return date.getFullYear() + '-' + month + '-' + day;
    },
     
  },

}
</script>

<style lang="scss" scoped>
$button-bg: #17B0E7;

.my-board {
  margin-top: 60px;

}
.head {
  display: flex;
  justify-content: right;
  width: 80%;  
  max-width: 1000px;
  margin: auto;
}
.th-list {
  // background-color:
}
.th-no {
  width: 10%;
}
.th-title {
  width: 60%;
}

.list-box {
  width: 80%;
  max-width: 1000px;
  margin: auto;
  // border: 1px solid black;
  border: 2px solid #17B0E7;

  border-radius: 10px;
  border-collapse: separate !important;
}
.list-box th, .list-box td{
  border-bottom:1px solid #eee;
  padding:8px 0
}
.list-box td.txt_left {
  padding-left: 8px;
  text-align:left;
}
.a-title {
  text-decoration: none;
  color: rgb(2, 0, 145);
  cursor: pointer;
}
.exit-icon {
  font-size: 20px;
}
.btn {
  background-color: $button-bg;
  color: #fff;
  margin-bottom: 20px;
  border: none;
  // padding: 8px;
  box-shadow: 0 10px 20px rgba(0,0,0,.1);
  &:hover {
    background: darken($button-bg, 3%);
  }
}

</style>