<template>
  <div>
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
    
      <i class="fas fa-times-circle exit-icon"></i>

    <div class="content-box">
      <div class="card-box" >
        <ProfileDetail
          v-for="(profileDetail,key) in profile"
          :key = key
          :profileDetail = profileDetail
        />
      </div>
      <button class="out">나가기</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters} from 'vuex'
const boardStore = 'boardStore'
import ProfileDetail from './ProfileDetail.vue'
export default {
  components: { ProfileDetail },
  name: 'Profile',
  data() {
    return  {
      profile: {},
      search_name: '',
    }
  },
  computed: {

  },
  created() {
    this.getProfile()
    .then(()=>{
      this.profile = this.profile_info()
    })
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
    }
  },
  mounted() {
    // this.searchUser()
  }
}
</script>

<style scoped>
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
.box:hover i{
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

.profile-list{
  display: flex;
  justify-content: center;
}
.card-box {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  max-width: 1200px;
  margin: auto;
  /* border: 1px solid black; */
}
.content-box {
  margin-top: 20px ;
  /* border: 1px solid black; */
}
.out {
  background: #ffd52d;
  padding: 8px 20px;
  border-radius: 5px;
  text-decoration: none;
  color: white;
  margin-top: 20px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
  transition: 0.5s;
  font-size: 15px;
  font-weight: bold;
  border: 0px;
}
.out:hover {
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.6);
  background: #fff;
  color: #000;
}
</style>>

