<template>
  <div class="imgurl">
    <div class="head-box">
      
      <div class="box">
        <div name="search">
          <input type="text" class="input" 
            v-model="search_name"
            @keyup.enter="search()"  
          >
        </div>
          <i class="fas fa-search search-icon"></i>
      </div>
    </div>

    <div class="content-box">
      <div class="card-box" >
        <ProfileDetail
          v-for="(profileDetail,key) in profile"
          :key = key
          :profileDetail = profileDetail
        />
      </div>
      <div class="content-bottom">
        <div class="hide"></div>
        <Pagnation
          :pagnationInfo = pagnation_info
          @update ="profileList"
        />
        <div class="out">나가기</div>
      </div>
    </div>
      
  </div>
</template>

<script>
import { mapActions, mapGetters} from 'vuex'
const boardStore = 'boardStore'

import Pagnation from './Pagnation.vue'
import ProfileDetail from './ProfileDetail.vue'
export default {
  components:{ProfileDetail, Pagnation},
  name: 'Profile',
  data() {
    return  {
      profile: {},
      search_name: '',
      total: '',
      page: 0, // 현재 페이지
      
    }
  },
  computed: {

  },
  created() {
    // console.log(this.page,'ddd')
    this.total = this.profile_info().totalPages
    this.pagnation_info = {
      'page': this.page,
      'total': this.total
    }
  },
  methods: {
    ...mapActions(boardStore, ['getProfile', 'searchUser']),
    ...mapGetters(boardStore, ['profile_info','search_user']),
    search() {
      console.log(this.search_name)
      this.searchUser(this.search_name)
      .then(()=>{
        this.profile = this.search_user()
        this.search_name = ''
      })
    },
    profileList(value) {
      console.log(value)
      this.page = value-1
      const page = this.page
      this.getProfile(page)
      .then(()=>{
        this.profile = this.profile_info().content
        // this.$router.push({name:"Profile", params: this.page})
      })
    },
    

  },
  mounted() {
    this.profileList()

  }
}
</script>

<style scoped>
body, html{
  width: 100%;
  height: 100vh;
  margin: 0;
  padding: 0;
}
.imgurl {
  width: 100vw;
  height: 100vh;
  background-image: url("../assets/locker.png");
  background-size: cover;
  background-position: center;
  padding-top: 30px;
}

.head-box {
  display: flex;
  /* justify-content: end; */
  justify-content: center;
}

.box{
    position: relative;
    /* top: 50%;
    left: 50%; */
    /* transform: translate(-50%,-50%); */

}
.input {
    padding: 10px;
    width: 50px;
    height: 50px;
    background: none;
    border: 4px solid #ffd52d;
    border-radius: 50px;
    box-sizing: border-box;
    font-family: Open Sans;
    font-size: 26px;
    color: #ffd52d;
    outline: none;
    transition: .5s;
}
.box:hover input{
    width: 350px;
    background: #272133;
    border-radius: 10px;
}
.search-icon{
    position: absolute;
    top: 50%;
    /* right: 15px; */
    transform: translate(-50%,-50%);
    font-size: 26px;
    color: #ffd52d;
    transition: .2s;

}
.box:hover .search-icon{
    opacity: 0;
    z-index: -1;
}
.exit-icon {
  margin-left: 50px;
  font-size:26px;
  position: absolute;
  top: 12%;
  right: 15% ;
}


.card-box {
  display: flex;
  justify-content: center;
  /* align-items: center; */
  flex-wrap: wrap;
  /* width: 220px; */
  max-width: 1200px;
  margin: auto;
  /* border: 1px solid black; */
}
.content-box {
  margin-top: 20px ;
  max-width: 1200px;
  margin: auto;
  /* display: flex; */
  /* justify-content: center; */
  /* flex-wrap: wrap; */
  /* border: 1px solid black; */
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
</style>>

