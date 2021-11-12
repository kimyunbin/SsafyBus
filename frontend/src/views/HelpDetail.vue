<template>
  <div class="container">
    <!-- {{help_item}} -->
    <div class="title">
      <!-- <h2>제목</h2> -->
      <h2>{{help_item.title}}</h2>
      <div class="change" v-if="this.nickname == help_item.user">
        <button class="btn" @click="editClick(help_item.helpId)">수정</button>
        <button class="btn" @click="deleteClick(help_item.helpId)" style="margin-left:8px">삭제</button>
      </div>
    </div>

    <div class="code">
      <!-- <CodeEditor

      >
      </CodeEditor> -->
      <!-- {{help_item.code}} -->
    </div>

    <div class="content center">
      <viewer :initialValue="viewerText" height="500px" />
      <!-- <p class="txt_left">{{help_item.content}}</p> -->
    </div>

     <div v-if="help_item.link" class="link">
      링크: <a v-bind:href="help_item.link"> {{help_item.link}}</a>
    </div>


    <div class="comment-wirte">
      <!-- <label>댓글:</label> -->
      <input type="text" name="" id="" class="comment-input"  v-model="comment" @keyup.enter="submitClick(help_item.helpId)">
      <button size="sm" class="btn" @click="submitClick(help_item.helpId)">댓글 등록하기</button>
    </div>
    <div class="comment-list">
      <CommentDetail 
        v-for="(commentList,key) in help_item.comments"
        :key = key
        :commentList = commentList
      />
    </div>




  </div>
</template>

<script>
import { mapActions, mapGetters} from 'vuex'
const boardStore = 'boardStore'
const userStore = 'userStore'
import '@toast-ui/editor/dist/toastui-editor-viewer.css';

import { Viewer } from '@toast-ui/vue-editor';

// import CodeEditor from 'simple-code-editor';
import CommentDetail from '../components/CommentDetail.vue'

export default {
  components: {
    // CodeEditor,
    CommentDetail,
     viewer: Viewer
  },
  name: 'HelpDetail',
  data() {
    return  {
      help_item: {},
      help_pk:'',
      comment: '',
      is_active : false,
      nickname: this.user_info().nickname,
      viewerText: this.help_one_info().content,
      editorOptions: {
        hideModeSwitch: true
      }
    }
  },
  created() {
    // console.log( this.help_one_info())
    
    this.getHelpItem(this.$route.params.help_pk)
    .then(()=>{
      this.help_item = this.help_one_info()
    })
  },
  computed: {

    },
  methods: {
    ...mapActions(boardStore, ['getHelpList', 'getHelpItem','writeComment','editHelpItem','editHelp','deleteHelpItem' ]),
    ...mapGetters(boardStore, ['help_info','help_one_info']),
    ...mapGetters(userStore, ['user_info']),
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
        this.help_item.comments.push({
          content:this.comment,
          user:this.user_info(),
          createdAt:''
        })
        this.comment = ""
      })
    },
    editClick() {
      const value = {
        'title': this.help_item.title,
        'content' : this.help_item.content,
        'code' : this.help_item.code,
        'link': this.help_item.link,
      }
      this.editHelp(value)
      .then(()=>{
        const help_pk = this.help_item.helpId
        console.log(help_pk)
        this.$router.push({name:"HelpWrite" ,params: { help_pk: help_pk}})
      })
    },
    deleteClick(e) {
      const help_pk = e
      this.deleteHelpItem(help_pk)
      .then(()=>{
        alert('삭제되었습니다')
         this.$router.push({name:"Help"})
      })

    },
  }
  
}
</script>

<style lang="scss" scoped>
$button-bg: #17B0E7;
.container {
  margin:auto;
}

.title {
  margin-top: 60px;
  margin-bottom: 20px;
}
.change {
  margin: auto;
  width: 80%;
  max-width: 1000px;
  display: flex;
  justify-content: end;
}
.code {
  width: 80%;
  max-width: 1000px;
  margin: auto;
  margin-bottom: 50px;
}
.content {
  margin: auto;
  width: 80%;
  max-width: 1000px;
  height: 600px;
  border: 5px solid #17B0E7;
  border-radius: 20px;
  margin-bottom: 50px;
  padding: 20px;
}
.content .txt_left {
  padding-top: 15px;
  padding-left: 20px;
  text-align:left;
}
.link {
  margin-top:50px;
  margin-bottom: 50px;
  width: 80%;
  max-width: 1000px;
  margin: auto;
  display: flex;
  justify-content: end;
}
.link a {
  text-decoration: none;
  color: rgb(2, 0, 145);
  cursor: pointer;
}
.comment-list {
  width: 80%;
  max-width: 1000px;
  margin: auto;
  margin-top:50px;
  margin-bottom: 50px;
}
.comment-wirte {
  margin-top:50px;
  margin-bottom: 50px;
}
.comment-wirte input {
  width: 60%;
  height: 40px;
  margin: auto;
  margin-right: 20px;
  background-color: #eee;
  padding: 8px;
  padding-left: 15px;
}
.btn  {
  background-color: $button-bg;
  color: #fff;
  border: none;
  padding: 8px;
  box-shadow: 0 10px 20px rgba(0,0,0,.1);
  &:hover {
    background: darken($button-bg, 3%);
  }
}

.center{
  text-align: left;
}
</style>