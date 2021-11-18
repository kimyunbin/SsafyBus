<template>
<div :class="{'hidden': showGame}">
  <unity
    src = "./unity/PJT3/unity.json"
    unityLoader = "./unity/PJT3/UnityLoader.js"
    height = "600"
    width = "1000"
    ref= "instance"
    >
  </unity>
  <div id="unity-object"></div>
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
         this.interval = setInterval(()=>{
          if(this.$refs.instance !== undefined){
            //  this.$refs.instance.message('Canvas','UserName',this.user_info().nickname)
          }
          let item
          if(document.getElementById('unity-object').innerHTML !== undefined){
            item = document.getElementById('unity-object').innerHTML
          }
          if(item != undefined){
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
                break
            }
            document.getElementById('unity-object').innerHTML = ''
          }
        },1000)
       },
      goUnity(){

      },
      quitUnity(){
        clearInterval(this.interval);
      },
    },
    created(){
      this.getUnityItem()
    },
    watch: {
      object: function(object){
        console.log(object)
        this.object = ''
      }
    },
    computed: {
      showGame: function() {
        if(this.$route.name === 'UnityGame'){
          return false
        }
        else{
          return true
        }
      }
    }
}
</script>

<style scoped>
.hidden{
  visibility: hidden;
}
</style>