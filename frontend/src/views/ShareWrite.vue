<template>
  <div class="container">
    <div class="box">
      <h3>제목</h3>
      <input type="text" name="" id="" v-model="title">
    </div>
     <div class="box search-box">
      <!-- <textarea name="" id="" cols="30" rows="10"  ></textarea> -->
      <h3>공유할 사람</h3>
    <div class="select-team">
      <div class="input-member">
        <input type="text" v-model="member" @keypress="searchMember" placeholder="이름 입력">
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
    </div>
    <div class="member-list">
      <div v-for="(member, idx) in members" :key="idx">
        {{ member.nickname }}
        <span @click="deleteMember(idx)"><i class="fas fa-times"></i></span>
      </div>
    </div>
    <div class="box">
      <h3>공유파일</h3>
      <input type="file" accept=".gif, .jpg, .png" @change="upload">
    </div>
    <button  @click="submitClick()">등록</button>
  </div>
</template>

<script>
import { mapActions, mapGetters} from 'vuex'
const boardStore = 'boardStore'
const userStore = 'userStore'

export default {
  name: "ShareWrite",
  data() {
    return {
      title: '',
      member: '',
      members: [],
      users: [],
      userid: [],
      showList: false,
      files: '',
    }
  },
  methods: {
    ...mapActions(boardStore, ['searchUser','writeShareList']),
    ...mapGetters(boardStore, ['search_user']),
    ...mapGetters(userStore, ['user_info']),

    searchMember() {
      console.log(this.member)
      this.searchUser(this.member)
      .then(()=>{
        this.users = this.search_user()
        this.showList=true
      })
    },
    addMember(user){
      console.log(user)
      console.log(this.user_info().userId)
      if (user.userId == this.user_info().userId) {
        alert('자기자신은 빼주세요!')
      }
      else {
        this.members.push(user)
        this.userid.push(user.userId)
        this.showList=false
        this.member = ''
      }
    },
    deleteMember(idx){
      this.members.splice(idx,1)
      this.userid.splice(idx,1)
    },
    upload(e) {
      this.files = e.target.files
      console.log(this.files)

    },
    submitClick() {
      // console.log(this.userid)
      let data = new FormData();
      data.append('title', this.title);
      // for (let i = 0; i < this.userid.length; i++) {
      //   const me = this.userid[i];
      //   if (me == this.user_info().userId) {
      //     alert('자기자신은 빼주세요!')
      //   }
      // }
      
      data.append('userid',  this.userid);
      data.append('file', this.files[0]);
      // const data = {
      //   'title': this.title,
      //   'userId': this.userid,
      // }
      // const value = {
      //   'data': data,
      //   'file': file,
      // }
      console.log(data.get('userid'))
      console.log(data.get('file'))
      this.writeShareList(data)
      .then(()=>{
        this.$router.push({name:"Share"})
      })
    }
   
  }
  
}
</script>

<style lang="scss" scoped>


.container {
  margin-top: 60px;
  height: 450px;
  width: 100%;
  max-width: 600px;
  border: 5px solid #17B0E7;
  border-radius: 20px;
}
.search-box {

  display: flex;
}
.box {
  margin-top: 30px;
}
.box h3 {
  text-align: left;
  margin-left: 100px;
  margin-bottom: 10px;
}
.box input {
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
.select-team{
  // margin-left: 100px;
  // margin-top: 15px;
  color: black;
  font-size: 14px;
  display: flex;
    p {
      margin-left: 15px;
      margin-right: 15px;
    }
  .input-member{
    background-color: white;
    padding: 1px 0px;
    // position: absolute;
    margin-left: 90px;
    border: 1px solid #17B0E7;
    border-radius: 5px;

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

  background: #eee;
  width: 360px;
  height: 40px;
  margin-left: 100px;
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
</style>