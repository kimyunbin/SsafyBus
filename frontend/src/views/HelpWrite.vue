<template>
  <div class="container">
    <div class="box">
      <h3>제목</h3>
      <input type="text" name="" id="" v-model="title">
    </div>
     <!-- <div class="box">
      <h3>내용</h3>
      <textarea name="" id="" cols="30" rows="10"  ></textarea>
    </div> -->
    <!-- <div class="box">
      <h3>코드내용</h3>
      <textarea name="" id="" cols="30" rows="10"  v-model="code"></textarea>
    </div> -->
    <div class="box editer">
      <editor
          :initialValue="content"
          ref="toastuiEditor"
          height="500px"
          initialEditType="markdown"
          previewStyle="vertical"
      />
    </div>      
    <div class="box">
      <h3>링크</h3>
      <input type="text" name="" id=""  v-model="link">
    </div>
    <button v-if="this.$route.params.help_pk > 0" @click="editClick()">수정완료</button>
    <button v-else @click="submitClick()">등록</button>
  </div>
</template>

<script>
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/vue-editor';

import { mapActions, mapGetters} from 'vuex'
const boardStore = 'boardStore'
export default {
  components: {
    editor: Editor,
  },
  name: 'HelpWrite',
  data() {
    return {
      title:'',
      content: '',
      code: '',
      link:'',
    }
  },
  computed:{},
  methods:{
    ...mapActions(boardStore, ['writeHelpList','editHelpItem']),
    ...mapGetters(boardStore, ['help_info','search_user', 'edit_info']),

    submitClick() {
      const content = this.$refs.toastuiEditor.invoke('getHTML');
      console.log(content)
      const value = {
        "title" : this.title,
        "content" : content,
        "code" : this.code,
        "link" : this.link,
        }
      this.writeHelpList(value)
      .then(()=>{
        alert('등록이 완료되었습니다')
        this.$router.push({name:'Help'})
      })
    },
    editClick() {
      const help_pk = this.$route.params.help_pk
      const data = {
        "title" : this.title,
        "content" : this.content,
        "code" : this.code,
        "link" : this.link,
      }
      const value = {
        'help_pk' : help_pk,
        'data' : JSON.stringify(data)
      }
      this.editHelpItem(value)
      .then(()=>{
        this.$router.push({name:"Help"})
      })
    }
  },
  created() {
    // console.log(this.$route.params.help_pk,'pp')
    if (this.$route.params.help_pk > 0) {
      console.log(this.edit_info())
      this.title = this.edit_info().title
      this.content = this.edit_info().content
      this.code = this.edit_info().code
      this.link = this.edit_info().link
    }
  },
  
}
</script>

<style scoped>
.container {
  height: 850px;
  width: 80%;
  max-width: 1000px;
  border: 5px solid #17B0E7;
  border-radius: 20px;
  margin: auto;
  margin-top: 40px;
}
.box {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  max-width: 1000px;
}
.box h3 {
  /* text-align: left; */
  /* margin-left: 100px; */
}
.box input {
  margin-left: 50px;
  width: 360px;
  height: 35px;
}
.box textarea {
  width: 360px;
  /* height: 35px; */
}

.box input, textarea {
  background-color: #eee;
  padding-bottom: 5px;
  font-size: 1.1rem;
  border: none;
  border-radius: 5px;
  outline-style: none;  /* 포커스시 발생하는 효과 제거를 원한다면 */
  -webkit-appearance: none;  /* 브라우저별 기본 스타일링 제거 */
  -moz-appearance: none;
  appearance: none;
}
button {
  margin-top: 40px;
  margin-bottom: 20px;
  background: #17B0E7;
  color: #fff;
  border: none;
  padding: 8px;
  }
.editer{
  text-align: left;
}
</style>>

