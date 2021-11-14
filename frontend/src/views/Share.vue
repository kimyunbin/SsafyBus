<template>
  <div>
    <!-- {{share_list}} -->
    <div class="my-board">
      <h2>공유 리스트</h2>
    <div class="head">
    </div>
      <br>


    <table class="list-box">
      <thead class="th-list">
        <tr>
          <th class="th-no">no</th>
          <th class="th-title">제목</th>
          <th class="th-file">파일명</th>
          <th class="th-id">작성자</th>
          <th class="th-date">날짜</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, idx) in share_list" :key="idx">
          <td>{{idx+1}}</td>
          <td class="txt_left">
            <a @click="download(row.share.path)" class="a-title">{{row.share.title}}</a>
          </td>
          <td class="txt_right">
            <a @click="download(row.share.path)" class="a-title">{{row.share.original}}</a>
          </td>
          <td>{{row.share.user.nickname}}</td>
          <td>{{row.createdAt}}</td>
        </tr>
      </tbody>
    </table>
    <br>

    <button class="btn" @click="writeClick()">공유하기</button>
    <br>
    <div class="content-bottom">
      <div class="hide"></div>
      <Pagnation
        :pagnationInfo = pagnation_info
        @update ="shareList"
      />
      <div class="out">나가기</div>
    </div>
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
import Pagnation from '../components/Pagnation.vue'

export default {
  name: "Share",
  components: {
    Pagnation
  },
  data() {
    return {
      share_list: {},
      page: 0,
      total: '',
    }
  },
  created() {
    this.total = this.share_info().totalPages
    this.pagnation_info = {
      'page': this.page,
      'total': this.total
    }
  },
  methods: {
    ...mapActions(boardStore, ['getShareList','shareDownload']),
    ...mapGetters(boardStore, ['share_info']),
    shareList(value) {
      console.log(value)
      this.page = value-1
      const page = this.page
      this.getShareList(page)
      .then(()=>{
        this.share_list = this.share_info().content
        for (let i = 0; i < this.share_list.length; i++) {
          const createdAt = this.share_list[i].createdAt
          this.share_list[i].createdAt = this.dateFormat(new Date(createdAt))
          }
        })      
    },
    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();
      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;
      return date.getFullYear() + '-' + month + '-' + day;
    },
    
    writeClick() {
      this.$router.push({name:"ShareWrite"})
    },
    download(e) {
      console.log(e)
      this.shareDownload(e)
      .then((res)=>{
        const url = window.URL.createObjectURL(new Blob([res.data]),{ type: res.headers['content-type'] });
        // { type: res.headers['content-type'] }));
        console.log(url,'url')
        const link = document.createElement('a');
        const contentDisposition = res.headers['content-disposition']; // 파일 이름
        console.log(contentDisposition)
        const fileName = decodeURI(contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/)[1].replace(/['"]/g, ''))

        // let fileName = 'unknown';
        // if (contentDisposition) {
        //   const [ fileNameMatch ] = contentDisposition.split(';').filter(str => str.includes('filename'));
        //   if (fileNameMatch)
        //     [ , fileName ] = fileNameMatch.split('=');
        // }          
        link.href = url;
        link.setAttribute('download',`${fileName}`);
        // link.setAttribute('download','회의록.md');

        document.body.appendChild(link);
        link.click();   
        window.URL.revokeObjectURL(url);
      })

    },
  
  },
  mounted() {
    this.shareList()
  }

}
</script>

<style lang="scss" scoped>
$button-bg: #17B0E7;
body, html{
  width: 100%;
  height: 100vh;
  margin: 0;
  padding: 0;
}
.my-board {
  width: 100vw;
  height: 100vh;
  background-image: url("../assets/board.png");
  background-size: cover;
  background-position: center;
  padding-top: 60px;

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
  width: 50%;
}
.th-file {
  width: 15%;
}
.list-box {
  width: 80%;
  max-width: 1000px;
  margin: auto;
  // border: 1px solid black;
  background-color: #fff;
  padding: 10px;
  // border: 2px solid #17B0E7;

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
  padding: 8px;
  box-shadow: 0 10px 20px rgba(0,0,0,.1);
  border-radius: 10px;
  &:hover {
    background: darken($button-bg, 3%);
  }
}

.out {
  background: #ffd52d;
  padding: 8px 20px;
  border-radius: 5px;
  text-decoration: none;
  color: white;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
  transition: 0.5s;
  font-size: 15px;
  font-weight: bold;
  border: 0px;
  cursor: pointer;
}
.out:hover {
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.6);
  background: #fff;
  color: #000;
}

.content-bottom{
  margin: auto;
  width: 80%;
  max-width: 1000px;
  display: flex;
  justify-content: space-around;
}
.hide{
  width: 80px;
}

</style>