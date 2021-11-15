<template>
  <div>
    <div class="my-board">
      <h2>헬프 게시판</h2>


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
          <tr v-for="(row, idx) in help_list.slice()" :key="idx">
            <td>{{row.id}}</td>
            <td class="txt_left">
              <a class="a-title" @click="detailClick(row.id)">{{row.title}}</a>
            </td>
            <td>{{row.user.nickname}}</td>
            <td>{{row.createdAt}}</td>
          </tr>
        </tbody>
      </table>
      <br>
      <button class="btn" @click="writeClick()">질문하기</button>
      <br>
      <div class="content-bottom">
        <div class="hide"></div>
        <Pagnation
          :pagnationInfo = pagnation_info
          @update ="helpList"
        />
        <div class="out" @click="goUnity">나가기</div>
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
import Pagnation from '../components/Pagnation.vue'
// import CodeEditor from 'simple-code-editor';
const boardStore = 'boardStore'
  export default {
    components: {
        Pagnation
      // CodeEditor,
    },
    data() {
      return {
        fields: [
          { key: 'title', label: '제목', thClass: 'w30'},
          { key: 'content', label: '내용', thClass: 'w30'},
          { key: 'user.nickname', label: '작성자', thClass: 'w15'},
          { key: 'createdAt', label: '작성일', sortable: true, thClass: 'w10'},
          { key: 'show_details', label: '자세히보기', thClass: 'w5'},

          ],

        help_list: [],
        help_item:{},
        help_pk:'',
        comment: '',
        is_active : false,
        total: '',
        page: 0, // 현재 페이지
      }
    },
    computed: {},
    methods: {
    ...mapActions(boardStore, ['getHelpList', 'getHelpItem','writeComment','deleteHelpItem']),
    ...mapGetters(boardStore, ['help_info','help_one_info']),
   
    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();
      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;
      return date.getFullYear() + '-' + month + '-' + day;
    },

    writeClick() {
      this.$router.push({name:"HelpWrite"})
    },
    detailClick(pk) {
      console.log(pk)
      // row.toggleDetails()
      const help_pk = pk
        this.getHelpItem(help_pk)
        .then(()=>{
          this.help_item = this.help_one_info()
          this.$router.push({name:'HelpDetail', params: { help_pk: help_pk}})
        })
    },
    deleteClick(e) {
      const help_pk = e
      this.deleteHelpItem(help_pk)
      .then(()=>{
        alert('삭제되었습니다')
        this.$router.go()
      })

    },
    bclick() {
    this.is_active = true
    },
    xclick() {
      this.is_active = false
    },
    submitClick(e) {
      const help_pk = e
      var comment = {
      'content' : this.comment
      }
      var value = {
        'help_pk' : help_pk,
        'content' : JSON.stringify(comment)
      }
      console.log(value)
      this.writeComment(value)
      .then(()=>{
        this.$router.go()
      })
    },
    helpList(value) {
      console.log(value)
      this.page = value-1
      const page = this.page
      this.getHelpList(page)
      .then(()=>{
        this.help_list = this.help_info().content
        for (let i = 0; i < this.help_list.length; i++) {
          const createdAt = this.help_list[i].createdAt
          this.help_list[i].createdAt = this.dateFormat(new Date(createdAt))
          }
        })
    },
    goUnity(){
      this.$router.push('UnityGame')
    }
  },
  mounted() {
    this.helpList()
  },
  created() {
    
    this.total = this.help_info().totalPages
    this.pagnation_info = {
      'page': this.page,
      'total': this.total
    }
    },
  }
</script>

<style lang="scss" scoped>


$button-bg: #17B0E7;
$speed: 0.6s;
$delay: ($speed * .5);
$easing: cubic-bezier(.55,0,.1,1);

/**
 * Base styles
 */


h1,  h3, h4, h5, h6 {
  font-weight: normal;
}

h1 {
  font-size: 1.875rem;
  font-weight: 300;
  margin: 60px 0 30px 0;
  color: black;
}

h3 {
  font-size: 16px;
  font-weight: 500;
  margin: 10px 0 30px 0;
  color: black;
}

h2{
  margin-bottom: 20px;
}
// button {
//   background-color: $button-bg;
//   position: relative;
//   color: #fff;
//   border: none;
//   padding: 1.25em 2em;
//   font-size: 0.75em;
//   letter-spacing: 1px;
//   text-transform: uppercase;
//   cursor: pointer;
//   box-shadow: 0 10px 20px rgba(0,0,0,.1);
//   transition: background 0.25s $easing;
  
//   &:hover {
//     background: darken($button-bg, 3%);
//   }
  
//   &:focus {
//     outline: none;
//   }
// }

  .tbList{margin-right: 0px}
	// .tbList th{border-top:1px solid #888;}
	.tbList th, .tbList td{border-bottom:1px solid #eee; padding:8px 0;}
	.tbList td.txt_left{padding-left: 5px; text-align:left;}
	// .btnRightWrap{text-align:right; margin:10px 0 0 0;}
  //  tbody, td, tfoot, th, thead, tr{width: 200px;}

/// 여기부터
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
  // border: 2px solid #17B0E7;
  background-color: #fff;
  border-radius: 10px;
  padding: 10px;
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