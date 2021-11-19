<template>
<div :class="{'hidden': showGame}">
  <unity
    src = "./unity/PJT3/unity.json"
    unityLoader = "./unity/PJT3/UnityLoader.js"
    height = "80%"
    width = "90%"
    ref= "instance"
    >
  </unity>
  <div id="unity-object" hidden></div>
  <!-- <input type="text"> -->
</div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import { mapGetters } from 'vuex'
const userStore = 'userStore'

export default {
    components : {
      Unity,
    },
    data() {
        return {
          object: '',
          interval:'',
        }
    },
    methods: {
       ...mapGetters(userStore, ['user_info']),
       getUnityItem(){
           if(this.$refs.instance !== undefined){
              // this.$refs.instance.message('Canvas','UserName',this.user_info().nickname)
           }
         this.interval = setInterval(()=>{
           let item
          if(document.getElementById('unity-object').innerHTML !== undefined){
            item = document.getElementById('unity-object').innerHTML
          }
          if(item){
            switch(item){
              case 'GuestBook':
                this.$router.push({name: 'Gate'})
                break
              case 'Help':
                this.$router.push({name: 'Help'})
                break
              case 'Share':
                this.$router.push({name: 'Share'})
                break
              case 'kiosk':
                console.log("찍힘")
                this.$router.push({name: 'Reserve'})
                break
              case 'Locker':
                this.$router.push({name: 'Locker'})
                break
              case 'study1':
                this.$router.push({name: 'StudyRoom', params: {roomId: 1}})
                break
              case 'study2':
                this.$router.push({name: 'StudyRoom', params: {roomId: 2}})
                break
              case 'study3':
                this.$router.push({name: 'StudyRoom', params: {roomId: 3}})
                break
              default:
                this.$router.push({name: 'Live', params: {location: item}})
                break
            }
            document.getElementById('unity-object').innerHTML = ''
            // clearInterval(this.interval)
          }
        },1000)
       },
      goUnity(){

      },
      quitUnity(){
        clearInterval(this.interval)
      },
    },
    created(){
      // console.log('c')

      if(this.$route.name === 'UnityGame'){
        this.getUnityItem()
      }
      else{
        clearInterval(this.interval);
      }
    },
    watch: {
    },
    computed: {
      showGame: function() {
        if(this.$route.name === 'UnityGame'){
          if(this.$refs.instance !== undefined){
              this.$refs.instance.message('Game Manager','focusing',"true");
          }  
          return false
        }
        else{
          if(this.$refs.instance !== undefined) {
            this.$refs.instance.message('Game Manager','focusing',"false");
          }
          return true
        }
      }
    }
}
</script>

<style scoped>
.hidden{
  display: none;
}
</style>